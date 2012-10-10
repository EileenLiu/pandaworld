package student;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Console {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(args[0].equals("--mutate"))
		{
			
		
		}
		else
		{
			try {
				Scanner infile = new Scanner(new File(args[0]));
				
			} catch (FileNotFoundException e) {

				System.out.println("The given file was not found.");
			}
		}

	}
}
