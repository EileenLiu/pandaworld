package student;

import java.io.Reader;
import java.util.LinkedList;

public class ParserImpl implements Parser {

    public ParserImpl() {
        
    }

    public Program parse(Reader r) {
        try {
            return parseProgram(new Tokenizer(r));
        } catch (SyntaxError se) {
            return null;
        }
    }

    /** Parses a program from the stream of tokens provided by the Tokenizer,
     *  consuming tokens representing the program. All following methods with
     *  a name "parseX" have the same spec except that they parse syntactic form
     *  X.
     *  @return the created AST
     *  @throws SyntaxError if there the input tokens have invalid syntax
     */
    private static Program parseProgram(Tokenizer t) throws SyntaxError {
        Program p = new Program();
        while(t.hasNext())
            p.addRule(parseRule(t));
        return p;
<<<<<<< HEAD
    }
    
    private static Rule parseRule(Tokenizer t) throws SyntaxError {
        Condition cond = parseCondition(t);
        expect(t,"-->");
        LinkedList<Update> upds = new LinkedList<Update>();
        Update upd;
        while((upd = parseUpdate(t)) != null)
            upds.add(upd);
        Action act = parseAction(t);
        if(act != null) 
            return new Rule(cond, upds, act);
        else if(upds.size() > 0)
            return new Rule(cond, upds);
        else 
            throw new SyntaxError.Empty.Command(t.line());
    }
    
    private static Condition parseCondition(Tokenizer t) throws SyntaxError {
        Conjunction conj;
        LinkedList<Conjunction> conjs = new LinkedList<Conjunction>();
        while((conj = parseConjunction(t)) != null)
            conjs.add(conj);
        if(conjs.size() == 0)
            throw new SyntaxError.Empty.Condition(t.line());
        else
            return new Condition(conjs);
    }
    
    private static Conjunction parseConjunction(Tokenizer t) throws SyntaxError {
        Relation rel;
        LinkedList<Relation> rels = new LinkedList<Relation>();
        while((rel = parseRelation(t)) != null)
            rels.add(rel);
        if(rels.size() == 0)
            throw new SyntaxError.Empty.Condition(t.line());
        else
            return new Conjunction(rels);
    }
    
    private static Relation parseRelation(Tokenizer t) throws SyntaxError {
        BinaryCondition bc = parseBinaryCondition(t);
        if(bc == null) { //not -> expr rel expr
            expect(t,"(");
            Condition c = parseCondition(t);
            expect(t,")");
            return new Relation(c);
        } else 
            return new Relation(bc);
    }
    
    private static BinaryCondition parseBinaryCondition(Tokenizer t) throws SyntaxError {
=======
    }
    
    private static Rule parseRule(Tokenizer t) throws SyntaxError {
        Condition cond = parseCondition(t);
        expect(t,"-->");
        LinkedList<Update> upds = new LinkedList<Update>();
        Update upd;
        while((upd = parseUpdate(t)) != null)
            upds.add(upd);
        Action act = parseAction(t);
        if(act != null) 
            return new Rule(cond, upds, act);
        else if(upds.size() > 0)
            return new Rule(cond, upds);
        else 
            throw new SyntaxError.EmptyCommand(t.line());
    }
    
    private static Condition parseCondition(Tokenizer t) throws SyntaxError {
>>>>>>> FaultInjection
        throw new Error();
    }
    
    private static Update parseUpdate(Tokenizer t) throws SyntaxError {
        throw new Error();
    }
    
    private static Action parseAction(Tokenizer t) throws SyntaxError {
        throw new Error();
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
