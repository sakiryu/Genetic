package programLogic;

/**
 * @author Luna
 */

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
        
        public String getFormatedCodons()
        {
            String codon = "";
            
            for(Codon c : codons)
            codon += c + "\n";
            
            return codon;
        }
	
	public void printCodons()
	{
		codons.forEach(v -> System.out.println("["+v.getDistance() + "] - " + v.getCodon() + "\n" /*+ highDistance()*/));
		System.out.println("\n");
	}
	
	public int highDistance()
	{
            int i = codons.get(0).getDistance();
            
            for(Codon c : codons)
                if(c.getDistance() > i)
                    i = c.getDistance();
                    
            return i;
	}
        
        public long getBegin()
        {
            return begin;
        }
        
        public long getEnd()
        {
            return end;
        }
	
	public void setCodons(List<Codon> codons)
	{
		if(codons == null)
			throw new NullPointerException("[Exception!] Expected: List<String> codons.\nInput: " + codons +".");
		
		this.codons = codons;
	}
	
        @Override
	public String toString()
	{
		return  "Locus: " + locus + " | Início: " + begin + " | Fim: " + end + "\n" + sequence + "\n" + codons;
	}

}

