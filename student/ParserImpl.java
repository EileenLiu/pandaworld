package student;

import java.io.Reader;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParserImpl /*implements Parser*/ {

    public ParserImpl() {
        
    }

    public void parse(Reader r) throws SyntaxError {
        try {
            Deque<String> stack = new LinkedList<String>();
            Tokenizer t = new Tokenizer(r);
            stack.push("Program");
            while (stack.size() > 0) {
                System.out.print(t.peek() + " ");
                System.out.println(Arrays.toString(stack.toArray()));
                String top = stack.pop();
                int p = top.charAt(0) - 'A';
                if (p >= 0 && p < 26) {
                    parseClass(top, t, stack);
                } else {
                    expect(t, top);
                }
            }
        } catch (SyntaxError e) {
            throw e;
        }
    }
    
    private void parseClass(String top, Tokenizer t, Deque<String> stack) throws SyntaxError {
        String fst = t.peek();
        if("Program".equals(top)) {
            if(fst == null)
                return;
            stack.push("Program");
            stack.push("Rule");
        } else if("Rule".equals(top)) {
            stack.push(";");
            stack.push("Command");
            stack.push("-->");
            stack.push("Condition");
        } else if("Command".equals(top)) {
            if("mem".equals(fst)) { //Upd Cmd1
                stack.push("Command1");
                stack.push("Update");
            } else if(fiAction.contains(fst))
                stack.push("Action");
            else throw new SyntaxError(t.line(), "Command parse failure");
        } else if("Command1".equals(top)) {
            if("mem".equals(fst)) { //Upd Cmd1
                stack.push("Command1");
                stack.push("Update");
            } else if(fiAction.contains(fst)) //Act
                stack.push("Action");
            //ep
        } else if("Update".equals(top)) {
            stack.push("Expression");
            stack.push(":=");
            stack.push("]");
            stack.push("Expression");
            stack.push("[");
            stack.push("mem");
        } else if("Action".equals(top)) {
            if("tag".equals(fst)) {
                stack.push("]");
                stack.push("Expression");
                stack.push("[");
                stack.push("tag");
            } else if(fiAction.contains(fst)) {
                stack.push(fst);
            } else throw new SyntaxError(t.line(),"Action parse failure");
        } else if("Condition".equals(top)) {
            stack.push("Condition1");
            stack.push("Conjunction");
        } else if("Condition1".equals(top)) {
            if("or".equals(fst)) { //"or" Cnj Con'
                stack.push("Condition1");
                stack.push("Conjunction");
                stack.push("or");
            } 
            //ep
        } else if("Conjunction".equals(top)) {
            stack.push("Conjunction1");
            stack.push("Relation");
        } else if("Conjunction1".equals(top)) {
            if("and".equals(fst)) { //"and" Rel Cnj'
                stack.push("Conjunction1");
                stack.push("Relation");
                stack.push("and");
            }
            //ep
        } else if("Relation".equals(top)) {
            if("{".equals(fst)) { //"{" Con "}"
                stack.push("}");
                stack.push("Condition");
                stack.push("{");
            } else if(fiExpr.contains(fst)) { //Exp Rsy Exp
                stack.push("Expression");
                stack.push("Rel");
                stack.push("Expression");
            } else
                throw new SyntaxError(t.line(),"Relation parse failure");
        } else if("Rel".equals(top)) {
            if(fiRel.contains(fst))  //Rsy
                stack.push(fst);
            else
                throw new SyntaxError(t.line(),"Rel parse failure");
        } else if("Expression".equals(top)) {
            stack.push("Expression1");
            stack.push("Factor");
        } else if("Expression1".equals(top)) {
            if(fiAop.contains(fst)) {
                stack.push("Expression1");
                stack.push("Factor");
                stack.push(fst);
            } 
            //ep
        } else if("Factor".equals(top)) {
            stack.push("Factor1");
            stack.push("Atom");
        } else if("Factor1".equals(top)) {
            if(fiMop.contains(fst)) {
                stack.push("Factor1");
                stack.push("Atom");
                stack.push(fst);
            } 
            //ep
        } else if("Atom".equals(top)) {
            if(nump(fst)) {
                stack.push(fst);
            } else if("mem".equals(fst)) {
                stack.push("]");
                stack.push("Expression");
                stack.push("[");
                stack.push("mem");
            } else if("(".equals(fst)) {
                stack.push(")");
                stack.push("Expression");
                stack.push("(");
            } else if(fiSensor.contains(fst)) {
                stack.push("]");
                stack.push("Expression");
                stack.push("[");
                stack.push(fst);
            }
        } else
            throw new SyntaxError(t.line(),"Unknown token");
    }
    
    private static boolean nump(String s) {
        try {
            int i = Integer.parseInt(s);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
    
    private static final Set<String> fiAction, fiExpr, fiRel, fiAop, fiSensor, fiMop;
    
    static {
        Set<String> act = new HashSet<String>(11, 1);
        act.add("wait");
        act.add("forward");
        act.add("backward");
        act.add("left");
        act.add("right");
        act.add("eat");
        act.add("attack");
        act.add("grow");
        act.add("bud");
        act.add("mate");
        act.add("tag");
        fiAction = Collections.unmodifiableSet(act);
        
        Set<String> sen = new HashSet<String>(3,1);
        sen.add("nearby");
        sen.add("ahead");
        sen.add("random");
        fiSensor = Collections.unmodifiableSet(sen);
        
        Set<String> exp = new HashSet<String>(5,1);
        exp.add("mem");
        exp.add("(");
        exp.addAll(fiSensor);
        fiExpr = Collections.unmodifiableSet(exp);
        
        Set<String> rel = new HashSet<String>(6,1);
        rel.add("=");
        rel.add("!=");
        rel.add("<");
        rel.add(">");
        rel.add(">=");
        rel.add("<=");
        fiRel = Collections.unmodifiableSet(rel);
        
        Set<String> aop = new HashSet<String>(2,1);
        aop.add("+");
        aop.add("-");
        fiAop = Collections.unmodifiableSet(aop);
        
        Set<String> mop = new HashSet<String>(3,1);
        mop.add("*");
        mop.add("/");
        mop.add("mod");
        fiMop = Collections.unmodifiableSet(mop);
    }

    
    /** Consumes a token of the expected type. Throws a SyntaxError if the wrong kind of token is encountered. */
    public static void expect(Tokenizer t, String... ss) throws SyntaxError {
        String tok = t.next();
        for(String s : ss) 
            if(s.equalsIgnoreCase(tok)) 
                return;
        throw new SyntaxError.UnexpectedToken(t.line(), tok, ss);
    }
}
