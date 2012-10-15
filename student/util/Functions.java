/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.util;

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
    public static <E extends Enum<E>> Enum<E> randEnum(Class<? extends Enum<E>> type) {
        try {
            Enum<E> vals[] = (E[]) type.getMethod("values").invoke(null);
            int i = (int)(Math.random() * vals.length);
            return (E)vals[i];
        } catch (ReflectiveOperationException roe) {
            throw new RuntimeException("Functions.randEnum", roe);
        }
    }
}
