/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.util;

import student.Node;

/**
 *
 * @author haro
 */
public final class Functions {
    private Functions() { }
    
    /**
     * Returns a random member of the specified enumeration type.
     * @param type The enumeration type to be randomly selected from.
     * @return A random enumeration constant from that {@code type}
     */
    public static <E extends Enum<E>> E randEnum(Class<? extends Enum<E>> type) {
        try {
            Enum<E> vals[] = (E[]) type.getMethod("values").invoke(null);
            int i = (int)(Math.random() * vals.length);
            return (E)vals[i];
        } catch (ReflectiveOperationException roe) {
            throw new RuntimeException("Functions.randEnum", roe);
        }
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
