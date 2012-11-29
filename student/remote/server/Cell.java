package student.remote.server;

import java.io.Serializable;

public class Cell implements Serializable {

	private int row;
	private int col;

	private boolean hasCritter;
	private boolean hasPlant;
	private boolean hasRock;
	private boolean hasDead; // in case some groups have visualizable dead
	// critters/food

	private RemoteCritter critter;
    private RemoteSpecies species;
    
	private int critSize;
	private int critDirection;
	private int critAppearance;

	/*Consider writing utility methods for cleaner critter/plant/rock/dead
	 * cell construction*/
	public Cell(int row, int col, 
			boolean hasCritter, 
			boolean hasPlant, 
			boolean hasRock, 
			boolean hasDead,
			RemoteCritter critter,
			RemoteSpecies species,
			int critSize,
			int critDirection,
			int critAppearance)
	{
		this.row = row;
		this.col = col;
		this.hasCritter = hasCritter;
		this.hasPlant = hasPlant;
		this.hasRock = hasRock;
		this.hasDead = hasDead;
		this.critter = critter;
		this.species = species;
		this.critSize = critSize;
		this.critDirection = critDirection;
		this.critAppearance = critAppearance;
	}

	public int row() { return row; }
	public int col() { return col; }
	public boolean hasCritter() { return hasCritter; }
	public boolean hasPlant() { return hasPlant; }
	public boolean hasRock() { return hasRock; }
	public boolean hasDead() { return hasDead; }
	public RemoteCritter critter() { return critter; }
	public RemoteSpecies species() { return species; }
	public int critSize() { return critSize; }
	public int critDirection() { return critDirection; }
	public int critAppearance() { return critAppearance; }

	private static final long serialVersionUID = 6238531172449664193L;
}
