package student.util;

import java.util.Deque;
import java.util.LinkedList;

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
    
    public static StringBuffer test(String n, StringBuffer sb) {
        sb.append(n);
//        for(int i = (int)(Math.random()*16); i > 0; i--)
//            sb.append('.');
        return sb;
    }
    
    public static String pp(String s) {
        @SuppressWarnings("StringBufferMayBeStringBuilder")
        StringBuffer res = new StringBuffer(s.length()); //a guess, better than def.16
        Deque<Integer> inds = new LinkedList<Integer>();
        
        int i; char c = s.charAt(0);
        for(i = 0; i<s.length(); c = s.charAt(i++)) {
            switch (c) {
                case '{':
                    
            }
        }
        
        return res.toString();
    }
}
