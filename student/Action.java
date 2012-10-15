/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import java.util.LinkedList;
import student.ParserImpl.HistObj;
import static student.util.Functions.forName;

/**
 *
 * @author haro
 */
public class Action extends Node {
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

    private Action(String type) {
        super();
        this.act = forName(Act.class,type);
    }
    
    public Action() {
        super();
    }
    
    public void execute() {
        act.exec();
    }

    @Override
    public Node mutate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void prettyPrint(StringBuffer sb) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private static enum Act {
        WAIT {
            @Override public void exec() {
                throw new Error("Can't do that yet!");
            }
        }, FORWARD {
            @Override public void exec() {
                throw new Error("Can't do that yet!");
            }
        }, BACKWARD {
            @Override public void exec() {
                throw new Error("Can't do that yet!");
            }
        }, LEFT {
            @Override public void exec() {
                throw new Error("Can't do that yet!");
            }
        }, RIGHT {
            @Override public void exec() {
                throw new Error("Can't do that yet!");
            }
        }, EAT {
            @Override public void exec() {
                throw new Error("Can't do that yet!");
            }
        }, ATTACK {
            @Override public void exec() {
                throw new Error("Can't do that yet!");
            }
        }, GROW {
            @Override public void exec() {
                throw new Error("Can't do that yet!");
            }
        }, BUD {
            @Override public void exec() {
                throw new Error("Can't do that yet!");
            }
        }, MATE {
            @Override public void exec() {
                throw new Error("Can't do that yet!");
            }
        };
        
        public abstract void exec();
    }
}
