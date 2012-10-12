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
