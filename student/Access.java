package student;

import java.util.LinkedList;
import student.ParserImpl.HistObj;
import student.util.Functions;
import static student.util.Functions.forName;

/**
 * Represents an access of some external quantity, such as memory or a sensor.
 */
public class Access extends Expression {
    Sen sen;
    Expression ind;
    
    public static Access parse(LinkedList<HistObj> hist) throws SyntaxError {
        HistObj self = hist.pop();
        Sen type = forName(Sen.class,self.token);
        hist.pop().expect("[");
        Expression ind = Expression.parse(hist);
        hist.pop().expect("]");
        return new Access(type, ind);
    }

    private Access(Sen sen, Expression ind) {
        this.sen = sen;
        this.ind = ind;
    }
    
    /**
     * Randomly changes the type of access being made.
     * @return true
     */
    @Override
    public boolean randomize()
    {
    	sen = Functions.randEnum(Sen.class);
    	return true;
    }

    /**
     * Returns the value of the specified memory/sensor.
     */
    @Override
    public int eval() {
        return sen.val(ind.eval());
    }

    @Override
    public StringBuffer toString(StringBuffer sb) {
        sb.append(Functions.en2s(sen));
        sb.append('[');
        ind.toString(sb);
        sb.append(']');
        return sb;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Access a = (Access) super.clone();
        a.ind = (Expression) ind.clone();
        return a;
    }
    
    
    
    public static enum Sen {
        MEM {
            @Override public int val(int par) {
                throw new Error("Can't do that yet...");
            }
        },
        RANDOM {
            @Override public int val(int par) {
                return (int)(par*Math.random());
            }            
        },
        AHEAD {
            @Override public int val(int par) {
                throw new Error("Can't do that yet...");
            }            
        },
        NEARBY {
            @Override public int val(int par) {
                throw new Error("Can't do that yet...");
            }            
        };
        
        public abstract int val(int par);
    }
}
