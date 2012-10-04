package student;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

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
        sct.put('!',"!");
        singleCharacterTokens = Collections.unmodifiableMap(sct);
    }

    protected BufferedReader br;
    protected int lineNo;
    protected StringBuffer buf;
    protected boolean ready;
    protected String curTok;

    /**
     * Create a tokenizer for a program to be read by the specified reader.
     *
     * @param r
     */
    public Tokenizer(Reader r) {
        this.br = new BufferedReader(r);
        this.buf = new StringBuffer();
        this.lineNo = 1;
    }

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

    /**
     * Close the reader opened by this tokenizer.
     */
    public void close() {
        try {
            br.close();
        } catch (IOException e) {
            System.out.println("IOException:");
            System.out.println(e.getMessage());
            e.printStackTrace(); //TODO: remove after debugging
        }
    }
    
    private void lexNext() throws IOException, InvalidTokenException {
        int c;
        //eat whitespace
        while(Character.isWhitespace(c = br.read()) && c != '\n') //turns out newline can be significant, so it's tokenized.
            if(c < 0) //end of stream
                return;
        //is this a single character token?
        if(singleCharacterTokens.containsKey((char)c)) {
            curTok = singleCharacterTokens.get((char)c);
        } else if(c == '-') {
            curTok = lexWorm();
        } else if(c == '\n') {
            curTok = "\n";
        } else if(c == ':') {
            curTok = lexTwoSpot();
        } else if(Character.isJavaIdentifierStart(c)) {             
            curTok = lexName(c);
        } else if(Character.isDigit(c)) {
            curTok = lexNumber(c);
        } else if(c == '>' || c == '<') {
            curTok = lexAngle(c);
        } else { 
            curTok = unexpected();
        }
        ready = true;
    }

    private String lexWorm() throws IOException {
        br.mark(3); //prepare for peek
        int d;
        if((d = br.read()) < 0 || d != '-' || 
           (d = br.read()) < 0 || d != '>') {
            br.reset();
            return "-";
        }
        return "-->";
    }

    private String lexTwoSpot() throws IOException, InvalidTokenException {
        int d = br.read();
        if(d < 0 || d != '=') {
            return unexpected();
        }
        return ":=";
    }

    private String unexpected() throws InvalidTokenException {
        throw new InvalidTokenException();
    }

    private String lexName(int c) throws IOException {
        //alpha
        StringBuilder sb = new StringBuilder();
        sb.append((char) c);
        br.mark(2);
        while (Character.isJavaIdentifierPart(c = br.read())) {
            sb.append((char) c);
            br.mark(2);
        }
        br.reset();
        return sb.toString();
    }

    private String lexNumber(int c) throws IOException {
        int val = 0;
        val *= 10;
        val += c - '0';
        br.mark(2);
        while(Character.isDigit(c = br.read())) {
            val *= 10;
            val += c - '0';
            br.mark(2);
        }
        br.reset();
        return Integer.toString(val);
    }

    private String lexAngle(int c) throws IOException {
        br.mark(1);
        if(br.read() == '=')
            return c == '>' ? ">=" : "<=";
        else
            return c == '>' ? ">" : "<";
    }
    
    public static class InvalidTokenException extends Exception {

    }
}
