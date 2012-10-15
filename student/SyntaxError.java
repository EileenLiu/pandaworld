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
    
    public static class Empty extends SyntaxError {
        public Empty(int line, String x) {
            super(line,"empty "+x+"; expected update or action after -->");
        }
        
        public static class Command extends Empty {
            public Command(int line) {
                super(line,"command");
            }
        }
        
        public static class Condition extends Empty {
            public Condition(int line) {
                super(line,"condition");
            }
        }
    }

    static class ExpectationFailure extends SyntaxError {
        public ExpectationFailure(String token, String string) {
            super(-1,"expected: "+token+", got: "+string);
        }
    }
}
