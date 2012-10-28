/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.grid;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

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
    
    private final Set<Action> actions; {
        Class<? extends Entity> self = getClass();
        Method meths[] = self.getMethods();
        Set<Action> acts = new HashSet<Action>();
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
        }
        actions = acts;
    }
    
    public Set<Action> actions() {
        return actions;
    }
    
    public abstract int read();
    public abstract void timeStep();
}
