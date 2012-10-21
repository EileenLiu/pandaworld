/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.parse.util;

/**
 *
 * @author haro
 */
public final class Functions {
    private Functions() { }
    
    /**
     * Returns a random member of the specified enumeration type.
     * @param e The enumeration type to be randomly selected from.
     * @return A random enumeration constant from that {@code type}
     */
    public static <E extends Enum<E>> E randEnum(Class<? extends Enum<E>> e) {
        Enum<E> vals[] = enval(e);
        int i = (int)(Math.random() * vals.length);
        return (E)vals[i];
    }
    
    private static <E extends Enum<E>> E []enval(Class<? extends Enum<E>> e) {
        try {
            return (E[]) e.getMethod("values").invoke(null);
        } catch (ReflectiveOperationException roe) {
            throw new RuntimeException("Functions.enval: " + roe.getMessage(), roe);
        }
    }
    
    /**
     * Returns the enumeration constant with the given name.
     * @param e The enumeration type to be queried
     * @param s The name to be found
     * @return The enumeration constant with that name, or null if no such constant can be found.
     */
    public static <E extends Enum<E>> E forName(Class<? extends Enum<E>> e, String s) {
        for(E i : enval(e))
            if(i.name().toLowerCase().equals(s))
                return i;
        return null;
    }
    
    public static <E extends Enum<E>> String en2s(E e) {
        return e.name().toLowerCase();
    }
    
    /**  
     * Creates an instance of the passed Node class. Generally called with the other method of the same name.
     * @param nc (some node type).class
     * @return a new such node.
     */
    public static <N> N tcln(Class<? extends N> nc) {
    	try {
    		return (N)nc.getConstructor().newInstance();
    	} catch (NullPointerException npe) {
    		throw new RuntimeException("Functions.tcln",npe);
    	} catch (ReflectiveOperationException roe) {
    		throw new RuntimeException("Functions.tcln",roe);
    	}
    }
    
    /**
     * Klones the type of n.
     * @param n A node, whose type has a default constructor.
     * @return A node superficially similar to {@code n}
     */
    @SuppressWarnings("unchecked")
    public static <N> N tcln(N n) {
    	return (N) tcln(n.getClass());
    }
}
