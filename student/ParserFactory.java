package student;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * A factory that gives access to instances of parser.
 */
public class ParserFactory {

    public static void main(String[] args) {
        if (args[0].equals("--mutate")) {
            try {
                Reader inStreamReader = new InputStreamReader(new FileInputStream(new File(args[2])));
                Program program = ParserFactory.getParser().parse(inStreamReader);
                int numMutations = Integer.parseInt(args[1]);
                for(int i = 0; i<numMutations; i++)
                {
                Node selected = FaultInjector.randomNode(program, null);
                selected.mutate();
                System.out.println(selected.mutationDescription());    
                }
                StringBuffer sb = new StringBuffer();
                program.prettyPrint(sb);
                System.out.println(sb);
            } catch (FileNotFoundException e) {
                System.out.println("The given file was not found.");
            } catch (NumberFormatException e) {
                System.out.println("--mutate requires an integer for number of mutations");
            }
        } else if (args[0].equals("--extend")) {
            ////////
        } else {
            try {
                Reader inStreamReader = new InputStreamReader(new FileInputStream(new File(args[0])));
                Program program = ParserFactory.getParser().parse(inStreamReader);
                StringBuffer sb = new StringBuffer();
                program.prettyPrint(sb);
                System.out.println(sb);
            } catch (FileNotFoundException e) {

                System.out.println("The given file was not found.");
            }
        }
    }
    /**
     * Return a parser object for parsing a critter program.
     *
     * @return
     */
    public static Parser getParser() {
        return (Parser) new ParserImpl();
    }
}
