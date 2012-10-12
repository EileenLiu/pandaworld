package student;

import java.util.Arrays;

public class SyntaxError extends Exception {
    public SyntaxError(int line) {
        this(line,"error");
    }
    
    public SyntaxError(int line, String msg) {
        super(""+line+": "+msg+"\n");
    }
    
    public static class UnexpectedToken extends SyntaxError {
        public UnexpectedToken(int line, String obs, String exp[]) {
            super(line, "unexpected token: expected one of: " + Arrays.toString(exp) + " but found: " + obs);
        }
    }

    public static class EmptyCommand extends SyntaxError {
        public EmptyCommand(int line) {
            super(line,"empty command; expected update or action after -->");
        }
    }
}
