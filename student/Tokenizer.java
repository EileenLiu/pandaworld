package student;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * A Tokenizer turns a Reader into a stream of tokens that can be iterated over
 * using a {@code for} loop.
 */
public class Tokenizer implements Iterator<String> {
    
    public static final Map<Character,String> singleCharacterTokens; //'+', '*', '/', '[', ']', '(', ')', '{', '}' }));
    static {
        Map<Character,String> sct = new HashMap<Character,String>();
        sct.put('+',"+");  //'-' is a prefix to '-->' so is not dealt with here.
        sct.put('*',"*");
        sct.put('/',"/");
        sct.put('[',"[");
        sct.put(']',"]");
        sct.put('(',"(");
        sct.put(')',")");
        sct.put('{',"{");
        sct.put('}',"}");
        sct.put(';',";");
        sct.put('=',"=");
        sct.put('%',"%");
        singleCharacterTokens = Collections.unmodifiableMap(sct);
    }

    protected PushbackReader pr;
    protected int lineNo;
    protected boolean ready;
    protected String curTok;

    /**
     * Create a tokenizer for a program to be read by the specified reader.
     *
     * @param r
     */
    public Tokenizer(Reader r) {
        super();
        this.pr = new PushbackReader(r);
        this.lineNo = 1;
    }
    
    private Tokenizer() {}

    @Override
    public boolean hasNext() {
        if (!ready) {
            try {
                lexNext();
            } catch (NoSuchElementException e) {
                return false;
            } catch (InvalidTokenException e) { //TODO: should we throw this up?
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    @Override
    public String next() {
        String tok = peek();
        ready = false;
        curTok = null;
        return tok;
    }

    /**
     * Return the next token in the program without consuming the token.
     *
     * @return
     */
    public String peek() {
        if(hasNext())
            return curTok;
        return null;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
    
    public int line() {
        return lineNo;
    }

    /**
     * Close the reader opened by this tokenizer.
     */
    public void close() {
        try {
            pr.close();
        } catch (IOException e) {
            System.out.println("IOException:");
            System.out.println(e.getMessage());
            e.printStackTrace(); //TODO: remove after debugging
        }
    }
    
    private void lexNext() throws IOException, InvalidTokenException {
        int c;
        //eat whitespace
        while(Character.isWhitespace(c = pr.read()))
            if(c == '\n')
                lineNo++;
        if(c < 0) //end of stream
            return; 
        //is this a single character token?
        if(singleCharacterTokens.containsKey((char)c)) {
            curTok = singleCharacterTokens.get((char)c);
        } else if(c == '-') {
            curTok = lexWorm();
        } else if(c == ':') {
            curTok = lexTwoSpot();
        } else if(Character.isJavaIdentifierStart(c)) {             
            curTok = lexName(c);
        } else if(Character.isDigit(c)) {
            curTok = lexNumber(c);
        } else if(c == '>' || c == '<') {
            curTok = lexAngle(c);
        } else if(c == '!') {
            curTok = lexWow();
        } else { 
            curTok = unexpected();
        }
        ready = true;
    }

    private String lexWorm() throws IOException, InvalidTokenException {
        int d;
        if((d = pr.read()) < 0 || d != '-') {
            pr.unread(d);
            return "-";
        } else if ((d = pr.read()) < 0 || d != '>') {
            pr.unread(d);
            return unexpected();
        }
        return "-->";
    }

    private String lexTwoSpot() throws IOException, InvalidTokenException {
        int d = pr.read();
        if(d < 0 || d != '=') {
            return unexpected();
        }
        return ":=";
    }

    private String unexpected() throws InvalidTokenException {
        throw new InvalidTokenException();
    }

    private String lexName(int c) throws IOException {
        int d;
        StringBuilder sb = new StringBuilder();
        sb.append((char) c);
        while (Character.isJavaIdentifierPart(d = pr.read())) {
            sb.append((char) d);
        }
        pr.unread(d);
        return sb.toString();
    }

    private String lexNumber(int c) throws IOException {
        int val = 0, d;
        val *= 10;
        val += c - '0';
        while(Character.isDigit(d = pr.read())) {
            val *= 10;
            val += d - '0';
        }
        pr.unread(d);
        return Integer.toString(val);
    }

    private String lexAngle(int c) throws IOException {
        int d;
        if((d = pr.read()) == '=')
            return c == '>' ? ">=" : "<=";
        pr.unread(d);
        return c == '>' ? ">" : "<";
    }

    private String lexWow() throws IOException {
        int d;
        if((d = pr.read()) == '=')
            return "!=";
        pr.unread(d);
        return "!";
    }
    
    public static class InvalidTokenException extends Exception {

    }
}
