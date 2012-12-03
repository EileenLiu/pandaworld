package student.parse;

import java.util.Collections;
import java.util.LinkedList;
import student.grid.Critter;
import student.parse.ParserImpl.HistObj;
import student.parse.util.Functions;
import static student.parse.util.Functions.forName;

/**
 * Represents an action a critter can take.
 * @author haro
 */
public class Action extends Node<Expression<?>> /*implements Serializable*/ {
    Act act;
    
    public static Action parse(LinkedList<HistObj> hist) throws SyntaxError {
        HistObj self = hist.pop();
        String type = hist.pop().token;
        if("tag".equals(type)) {
            hist.pop().expect("[");
            Expression ind = Expression.parse(hist);
            hist.pop().expect("]");
            return new Tag(ind);
        } else
            return new Action(type);
    }

    public Action(String type) {
        super(Collections.EMPTY_LIST);
        this.act = forName(Act.class,type);
    }
    
    public Action(Act a) {
        act = a;
    }
    
    protected Action() {
        super(Collections.EMPTY_LIST);
    }
    
    protected Action(Expression...e) {
        super(e);
    }
    
    /**
     * Executes the action.
     */
    public void execute(Critter c) {
        act.exec(c);
    }

    @Override
    public StringBuffer toString(StringBuffer sb) {
        sb.append(Functions.en2s(act));
        return sb;
    }
    
    @Override
    public boolean randomize() {
        act = Functions.randEnum(Act.class);
        return true;
    }

    public Act action() {
        return act;
    }

    public static enum Act {
        WAIT {
            @Override public void exec(Critter c) {
                c._wait();
            }
        }, FORWARD {
            @Override public void exec(Critter c) {
                c.forward();
            }
        }, BACKWARD {
            @Override public void exec(Critter c) {
                c.backward();
            }
        }, LEFT {
            @Override public void exec(Critter c) {
                c.left();
            }
        }, RIGHT {
            @Override public void exec(Critter c) {
                c.right();
            }
        }, EAT {
            @Override public void exec(Critter c) {
                c.eat();
            }
        }, ATTACK {
            @Override public void exec(Critter c) {
                c.attack();
            }
        }, GROW {
            @Override public void exec(Critter c) {
                c.grow();
            }
        }, BUD {
            @Override public void exec(Critter c) {
                c.bud();
            }
        }, MATE {
            @Override public void exec(Critter c) {
                c.mate();
            }
        };
        
        public abstract void exec(Critter c);
    }
}