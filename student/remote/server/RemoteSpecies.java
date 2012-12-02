package student.remote.server;

import java.rmi.Remote;
import java.util.ArrayList;
import java.util.HashSet;
import student.parse.Program;

public interface RemoteSpecies extends Remote {
	
	/**
	 * @return The unique attribute array for this critters of this species
	 */
	public int[] getSpeciesAttributes();
	
	/**
	 * @return The set of Species that preceded to the current species
	 */
	public HashSet<Integer> getLineage();
	
	/**
	 * @return The program of rules that critters of this species execute
	 */
	public Program getSpeciesProgram();

}
