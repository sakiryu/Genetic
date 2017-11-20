package Classes;

import java.util.List;
public class Gene {
	
	private long begin, end;
	private String locus;
	private String sequence;
	private List<Codon> codons;
	
	public Gene(String locus, String sequence, long begin, long end)
	{
		if(locus == null)
			throw new NullPointerException("[Exception!] Expected: <String> locus.\nInput: " + locus +".");
		
		if(sequence == null)
			throw new NullPointerException("[Exception!] Expected: List<Character> sequence.\nInput: " + sequence +".");
		
		this.locus = locus;
		this.sequence = sequence;
		this.begin = begin;
		this.end = end;
		this.codons = Genetic.createCodons(sequence);
		
	}
	
	public String getLocus(){return locus;}
	
	public String getSequence()
	{
		return sequence;
	}
	
	public List<Codon> getCodons()
	{
		return codons;
	}
	
	public void printCodons()
	{
		codons.forEach(v -> System.out.println("["+v.getDistance() + "] - " + v.getCodon()));
	}
	
	public void setCodons(List<Codon> codons)
	{
		if(codons == null)
			throw new NullPointerException("[Exception!] Expected: List<String> codons.\nInput: " + codons +".");
		
		this.codons = codons;
	}
	
	public String toString()
	{
		return  "\n[Locus: " + locus + "][Início: " + begin + "][Fim: " + end + "]\n" + sequence + "\n" + codons;
	}

}
