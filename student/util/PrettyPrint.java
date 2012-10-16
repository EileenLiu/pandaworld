/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.util;

/**
 *
 * @author haro
 */
public final class PrettyPrint {
    private PrettyPrint(){}
    
    public static final int CONDITION_BREAK = 38; //magic number alert
    public static int tabWidth(StringBuffer sb) {
        return lastTo(sb, "\n");
    }
    
    public static int lastTo(StringBuffer sb, String c) {
        return sb.length() - sb.lastIndexOf(c);
    }
    
    public static void tab(StringBuffer sb, int ts) {
        sb.append("\n");
        while(ts --> 1)
            sb.append(" ");
    }
    
    public static boolean shouldBreak(StringBuffer sb, int dist) {
        return tabWidth(sb) > dist;
    }
    
    public static void test(String n, StringBuffer sb) {
        sb.append(n);
        for(int i = (int)(Math.random()*16); i > 0; i--)
            sb.append('.');
    }
}
