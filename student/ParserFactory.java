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
		if(args[0].equals("--mutate"))
		{
			try {
				Reader inStreamReader = new InputStreamReader(new FileInputStream(new File(args[2])));
				Program program = ParserFactory.getParser().parse(inStreamReader);
                                try {
                                Integer.parseInt(args[1]);
                                }
                                catch (NumberFormatException e){
                                    System.out.println("Integer ");
                                }                        
                                Node selected = FaultInjector.randomNode(program, null);
                                selected.mutate();
                                System.out.println(selected.mutationDescription);
                                System.out.println(program.prettyPrint());
                                ///mutate???????????
                                //pretty print
			} catch (FileNotFoundException e) {

				System.out.println("The given file was not found.");
			}	
		}
		else if(args[0].equals("--extend"))
		{
			////////
		}
		else
		{
			try {
				Reader inStreamReader = new InputStreamReader(new FileInputStream(new File(args[0])));
				Program program = ParserFactory.getParser().parse(inStreamReader);
                                System.out.println(program.prettyPrint());
				
			} catch (FileNotFoundException e) {

				System.out.println("The given file was not found.");
			}
		}
	}
	/**
	 * Return a parser object for parsing a critter program.
	 * @return
	 */
	public static Parser getParser() {
		return (Parser) new ParserImpl();
	}
}
