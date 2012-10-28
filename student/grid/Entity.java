/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.grid;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The model class for an entity (rock/critter/plant)
 * @author haro
 */
public abstract class Entity {
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface IsAction {
        public String value() default "";
    }
    
    public static abstract class Action {
        public String name;
        public abstract Object exec(Object...p);
        public Class<?> parType[];
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface IsProperty {
        public String value() default "";
    }
    
    public static abstract class Property {
        public String name;
        public Class<?> type;
        public abstract Object value();
    }
    
    private final Set<Action> actions; 
    private final Set<Property> properties; {
        Class<? extends Entity> self = getClass();
        Method meths[] = self.getMethods();
        Set<Action> acts = new HashSet<Action>();
        Set<Property> props = new HashSet<Property>();
        for(Method m : meths) {
            if(m.isAnnotationPresent(IsAction.class)) {
                IsAction ia = m.getAnnotation(IsAction.class);
                String name = ia.value();
                if("".equals(name))
                    name = m.getName();
                Action a = new Action() {
                    Method m; Entity e;
                    public Action set(String _name, Method _m, Entity _e) {
                        name = _name;
                        parType = _m.getParameterTypes();
                        m = _m;
                        e = _e;
                        return this;
                    }
                    @Override
                    public Object exec(Object... p) {
                        try {
                            return m.invoke(e, p);
                        } catch (RuntimeException re) {
                            throw re;
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }.set(name, m, this);
                acts.add(a);
            }
            else if(m.isAnnotationPresent(IsProperty.class)) {
                IsProperty ip = m.getAnnotation(IsProperty.class);
                String name = ip.value();
                if("".equals(name))
                    name = m.getName();
                Property p = new Property() {
                    Entity e; Method m;
                    public Property set(String _name, Method _m, Entity _e) {
                        e = _e; m = _m;
                        name = _name;
                        type = m.getReturnType();
                        return this;
                    }
                    @Override
                    public Object value() {
                        try {
                            return m.invoke(e);
                        } catch (RuntimeException re) {
                            throw re;
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }.set(name, m, this);
                props.add(p);
            }
        }
        actions = acts;
        properties = props;
    }   
    
    public Set<Action> actions() {
        return actions;
    }
    public Set<Property> properties() {
        return properties;
    }
    
    public abstract int read();
    public abstract void timeStep();
}
