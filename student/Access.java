/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import java.util.LinkedList;
import student.ParserImpl.HistObj;

/**
 *
 * @author haro
 */
public class Access extends Expression {
    Sen sen;
    Expression ind;
    
    public static Access parse(LinkedList<HistObj> hist) throws SyntaxError {
        HistObj self = hist.pop();
        Sen type = Sen.forName(self.token);
        hist.pop().expect("[");
        Expression ind = Expression.parse(hist);
        hist.pop().expect("]");
        return new Access(type, ind);
    }

    private Access(Sen sen, Expression ind) {
        this.sen = sen;
        this.ind = ind;
    }
    @Override
    public boolean randomize()
    {
    	//TODO fill out
    	return true;
    }
    private static enum Sen {
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
        
        
        public static Sen forName(String st) {
            for(Sen s : values())
                if(s.name().toLowerCase().equals(st))
                    return s;
            return null;
        }
        
        public abstract int val(int par);
    }
}
