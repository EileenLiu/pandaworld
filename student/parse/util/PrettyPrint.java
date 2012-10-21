package student.parse.util;

import java.util.Deque;
import java.util.LinkedList;

/**
 *
 * @author haro
 */
public final class PrettyPrint {
    private PrettyPrint(){}
    
    public static int lnl(StringBuffer sb) {
        return sb.length() - sb.lastIndexOf("\n");
    }
    
    public static void tab(StringBuffer sb, int ts) {
        while(ts --> 0)
            sb.append(" ");
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
        int tab = 0;
                
        int i;
        for(i = 0; i<s.length(); i++) {
            char c = s.charAt(i);
            int lnl = lnl(res);
            if(c == '\n') {
                res.append('\n');
                tab(res, tab);
                continue;
            }
            if(Character.isWhitespace(c)) {
                res.append(c);
                continue;
            }
            if(c == ';') {
                res.append(";\n\n");
                tab = 0;
                continue;
            }
            if(c == '(' || c == '{')
                tab++;
            if(c == '}' || c == ')')
                tab--;
            if(s.length()-i>4) {
                if(s.substring(i,i+3).equals("-->")) {
                    res.append("\n");
                    tab = 8;
                    tab(res,tab);
                    tab += 4;
                }
            }
            //res.append(tab);
            res.append(c);
        }
        
        return res.toString();
    }
}
