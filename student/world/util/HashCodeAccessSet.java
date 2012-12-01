/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.world.util;

import java.util.Comparator;
import java.util.TreeSet;

/**
 *
 * @author haro
 */
public class HashCodeAccessSet<E> extends TreeSet<E> {
    public HashCodeAccessSet() {
        super(new Comparator<E>() {
            @Override
            public int compare(E o1, E o2) {
                return o1.hashCode() - o2.hashCode();
            }
        });
    }
    
    //I'm a C programmer.
    public E forHashCode(final int hash) {
        E dummy =(E) new Object() {
            @Override public int hashCode() {
                return hash;
            }
            @Override public boolean equals(Object o) {
                return false;
            }
        };
        E poss = floor(dummy);
        return poss == null ? poss : poss.equals(ceiling(dummy)) ? poss : null;
    }
    
    public static void main(String[] argv) {
        HashCodeAccessSet<Integer> hcas = new HashCodeAccessSet<Integer>();
        hcas.add(Integer.valueOf(6));
        hcas.add(Integer.valueOf(7));
        hcas.add(Integer.valueOf(9));
        System.out.println(hcas.forHashCode(7).intValue()==7 ? "good" : "bad");
        System.out.println(hcas.forHashCode(9).intValue()==9 ? "good" : "bad");
        System.out.println(hcas.forHashCode(6).intValue()==6 ? "good" : "bad");
        System.out.println(hcas.forHashCode(4423) == null ? "good" : "bad");
        System.out.println(hcas.forHashCode(4) == null ? "good" : "bad");
        System.out.println(hcas.forHashCode(8) == null ? "good" : "bad");
    }
}
