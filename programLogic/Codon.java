package programLogic;

/**
 * @author Luna
 */

public class Codon {
	
	private String codons;
	private int distance;
	
	public Codon(String codons, int distance)
	{
		this.codons = codons;
		this.distance = distance;
	}

	public int getDistance()
	{
		return distance;
	}
	
	public String getCodon()
	{
		return codons;
	}
	
	public String toString()
	{
		return "Distância: " + distance + "\n" + codons + "\n";
	}

}

