package student;

import java.io.Reader;

public class ParserImpl implements Parser {


    /** The tokenizer from which input is read. */
    Tokenizer tokenizer;

    public ParserImpl(Tokenizer tk) {
        throw new UnsupportedOperationException();
    }

    public Program parse(Reader r) {
        throw new UnsupportedOperationException();
    }

    /** Parses a program from the stream of tokens provided by the Tokenizer,
     *  consuming tokens representing the program. All following methods with
     *  a name "parseX" have the same spec except that they parse syntactic form
     *  X.
     *  @return the created AST
     *  @throws SyntaxError if there the input tokens have invalid syntax
     */
    public Program parseProgram() throws SyntaxError {
        throw new UnsupportedOperationException();
    }
    public Rule parseRule() throws SyntaxError {
        throw new UnsupportedOperationException();
    }
    public Condition parseCondition() throws SyntaxError {
        throw new UnsupportedOperationException();
    }
    public Expression parseExpression() throws SyntaxError {
        return parseTerm();
    }
    public Expression parseTerm() throws SyntaxError {
        throw new UnsupportedOperationException();
    }
    public Expression parseFactor() throws SyntaxError {
        throw new UnsupportedOperationException();
    }
    public Expression parseAtom() throws SyntaxError {
        throw new UnsupportedOperationException();
    }
    // add more as necessary...

    /** Consumes a token of the expected type. Throws a SyntaxError if the wrong kind of token is encountered. */
    public void consume(int tokenType) throws SyntaxError {
        throw new UnsupportedOperationException();
    }
}
