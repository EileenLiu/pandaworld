/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.util;

import student.Action;

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
            throw new RuntimeException("Functions.randEnum", roe);
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
}
