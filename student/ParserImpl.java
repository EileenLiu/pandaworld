package student;

import java.io.Reader;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import student.SyntaxError.ExpectationFailure;

public class ParserImpl implements Parser {

    public ParserImpl() {
        
    }

    @Override
    public Program parse(Reader r) {
        try {
            Deque<String> stack = new LinkedList<String>();
            LinkedList<HistObj> hist = new LinkedList<HistObj>();
            Tokenizer t = new Tokenizer(r);
            stack.push("Program");
            while (stack.size() > 0) {
                System.out.print(t.peek() + " ");
                System.out.println(Arrays.toString(stack.toArray()));
                String top = stack.pop();
                int p = top.charAt(0) - 'A';
                if (p >= 0 && p < 26) {
                    hist.add(parseClass(top, t, stack));
                } else {
                    String n = t.next();
                    if(!n.equals(top))
                        throw new SyntaxError.UnexpectedToken(t.line(), n, top);
                    hist.add(HistObj.tok(top));
                }
            }
            System.out.println(hist);
            return Program.parse(hist);
        } catch (SyntaxError e) {
            return null;
        }
    }
    
    private HistObj parseClass(String top, Tokenizer t, Deque<String> stack) throws SyntaxError {
        String fst = t.peek();
        if("Program".equals(top)) {
            if(fst == null)
                return null;
            stack.push("Program");
            stack.push("Rule");
            return new HistObj("Program","Program","Rule");
        } else if("Rule".equals(top)) {
            stack.push(";");
            stack.push("Command");
            stack.push("-->");
            stack.push("Condition");
            return new HistObj("Rule","Condition","-->","Command",";");
        } else if("Command".equals(top)) {
            if("mem".equals(fst)) { //Upd Cmd1
                stack.push("Command1");
                stack.push("Update");
                return new HistObj("Command","Update","Command1");
            } else if(fiAction.contains(fst)) {
                stack.push("Action");
                return new HistObj("Command","Action");
            }
            else throw new SyntaxError(t.line(), "Command parse failure");
        } else if("Command1".equals(top)) {
            if("mem".equals(fst)) { //Upd Cmd1
                stack.push("Command1");
                stack.push("Update");
                return new HistObj("Command1","Update","Command1");
            } else if(fiAction.contains(fst)) { //Act
                stack.push("Action");
                return new HistObj("Command1","Action");
            }
            return new HistObj("Command1");
        } else if("Update".equals(top)) {
            stack.push("Expression");
            stack.push(":=");
            stack.push("]");
            stack.push("Expression");
            stack.push("[");
            stack.push("mem");
            return new HistObj("Update","mem","[","Expression","]",":=","Expression");
        } else if("Action".equals(top)) {
            if("tag".equals(fst)) {
                stack.push("]");
                stack.push("Expression");
                stack.push("[");
                stack.push("tag");
                return new HistObj("Action","tag","[","Expression","]");
            } else if(fiAction.contains(fst)) {
                stack.push(fst);
                return new HistObj("Action",fst);
            } else throw new SyntaxError(t.line(),"Action parse failure");
        } else if("Condition".equals(top)) {
            stack.push("Condition1");
            stack.push("Conjunction");
            return new HistObj("Condition","Conjunction","Condition1");
        } else if("Condition1".equals(top)) {
            if("or".equals(fst)) { //"or" Cnj Con'
                stack.push("Condition1");
                stack.push("Conjunction");
                stack.push("or");
                return new HistObj("Condition1","or","Conjunction","Condition1");
            } 
            return new HistObj("Condition1");
        } else if("Conjunction".equals(top)) {
            stack.push("Conjunction1");
            stack.push("Relation");
            return new HistObj("Conjunction","Relation","Conjunction1");
        } else if("Conjunction1".equals(top)) {
            if("and".equals(fst)) { //"and" Rel Cnj'
                stack.push("Conjunction1");
                stack.push("Relation");
                stack.push("and");
                return new HistObj("Conjunction1","and","Relation","Conjunction1");
            }
            return new HistObj("Conjunction1");
        } else if("Relation".equals(top)) {
            if("{".equals(fst)) { //"{" Con "}"
                stack.push("}");
                stack.push("Condition");
                stack.push("{");
                return new HistObj("Relation","{","Condition","}");
            } else if(fiExpr.contains(fst)) { //Exp Rsy Exp
                stack.push("Expression");
                stack.push("Rel");
                stack.push("Expression");
                return new HistObj("Relation","Expression","Rel","Expression");
            } else
                throw new SyntaxError(t.line(),"Relation parse failure");
        } else if("Rel".equals(top)) {
            if(fiRel.contains(fst)) { //Rsy
                stack.push(fst);
                return new HistObj("Rel",fst);
            }
            else
                throw new SyntaxError(t.line(),"Rel parse failure");
        } else if("Expression".equals(top)) {
            stack.push("Expression1");
            stack.push("Factor");
            return new HistObj("Expression","Factor","Expression1");
        } else if("Expression1".equals(top)) {
            if(fiAop.contains(fst)) {
                stack.push("Expression1");
                stack.push("Factor");
                stack.push(fst);
                return new HistObj("Expression1",fst,"Factor","Expression1");
            } 
            return new HistObj("Expression1");
        } else if("Factor".equals(top)) {
            stack.push("Factor1");
            stack.push("Atom");
            return new HistObj("Factor","Atom","Factor1");
        } else if("Factor1".equals(top)) {
            if(fiMop.contains(fst)) {
                stack.push("Factor1");
                stack.push("Atom");
                stack.push(fst);
                return new HistObj("Factor1",fst,"Atom","Factor1");
            } 
            return new HistObj("Factor1");
        } else if("Atom".equals(top)) {
            if(nump(fst)) {
                stack.push(fst);
                return new HistObj("Atom",fst);
            } else if("(".equals(fst)) {
                stack.push(")");
                stack.push("Expression");
                stack.push("(");
                return new HistObj("Atom","(","Expression",")");
            } else if(fiSensor.contains(fst)) {
                stack.push("]");
                stack.push("Expression");
                stack.push("[");
                stack.push(fst);
                return new HistObj("Atom",fst,"[","Expression","]");
            }
        } else
            throw new SyntaxError(t.line(),"Unknown token");
        throw new Error("unreachable");
    }
    
    public static boolean nump(String s) {
        if(nums.containsKey(s))
            return true;
        try {
            int i = Integer.parseInt(s);
            nums.put(s, i);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
    
    public static int num(String s) throws SyntaxError {
        if(nump(s))
            return nums.get(s);
        throw new SyntaxError(-1,"NotANumber");
    }
    
    private static final Map<String, Integer> nums = new TreeMap<String,Integer>();
    
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
        sen.add("mem");
        fiSensor = Collections.unmodifiableSet(sen);
        
        Set<String> exp = new HashSet<String>(5,1);
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
        
        Set<String> mop = new HashSet<String>(4,1);
        mop.add("*");
        mop.add("/");
        mop.add("mod");
        mop.add("%");
        fiMop = Collections.unmodifiableSet(mop);
    }
    
    public static class HistObj {
        public final String token;
        
        public final String rule;
        public final String[] production;
        
        public HistObj(String t, String...p) {
            rule = t;
            production = p;
            token = null;
        }
        
        private HistObj(String t, Void v) {
            token = t;
            rule = null; production = null;
        }
        
        public static HistObj tok(String t) {
            return new HistObj(t,(Void)null);
        }
        
        @Override public String toString() {
            if(token != null)
                return "(T "+token+")";
            else
                return "(P "+rule+" "+Arrays.toString(production)+")";
        }

        public void expect(String string) throws SyntaxError {
            if(token == null || !token.equals(string))
                throw new SyntaxError.ExpectationFailure(string,token);
        }

        @Override
        public int hashCode() {
            if(token != null)
                return 3*token.hashCode();
            else
                return 5*Arrays.hashCode(production) + 7*rule.hashCode();
        }
        
        @Override
        public boolean equals(Object o) {
            if(o instanceof HistObj) {
                HistObj ho = (HistObj)o;
                if(ho.token != null) {
                    return ho.token.equals(this.token);
                } else {
                    return Arrays.equals(ho.production, this.production)
                        && ho.rule.equals(this.rule);
                }
            } else
                return false;
        }
    }
}
