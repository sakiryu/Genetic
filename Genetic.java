package Classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Genetic {
	
	private List<Gene> genes = new ArrayList<>();
	private static Genetic este = null;
	
	public void start()
	{
		genes = new ArrayList<>();
	}
	
	public static Genetic getHistance()
	{
		if(este == null)
			este = new Genetic();
		
		return este;
	}
	
	public List<Gene> getGenes()
	{
		return genes;
	}
	
	public void addGene(Gene g)
	{
		if(g == null)
			throw new NullPointerException("[Exception!]: " + g + " não pode ser nulo.");
		
		genes.add(g);
	}
	
	public static int getDistance(String strCodon)
	{
		final String FIRST = "Met";
		final String END = "Stop";
		
		//Quebrar a string em pedaços
		//Colocar dentro de uma lista
		List<String> codons = Arrays.asList(strCodon.split("-"));
		
		if(codons.contains(FIRST) && codons.contains(END))
		{
			//Pegar o índice da primeira ocorrência do 'Met'
			int firstIndex = codons.indexOf(FIRST);//getIndex(codons, first);
		
			//Pegar o índice da primeira ocorrência do 'Stop'
			int endIndex = codons.lastIndexOf(END);//getIndex(codons, end);
		
			//Medir a distância
			int distance = (endIndex - firstIndex);
		
			//Se for negativo = inválido(?)
			return distance;
		}
		
		return 0;
	}
	
	public static List<Codon> createCodons(String codonSequence)
	{	
		List<Codon> codons = new ArrayList<>();
	
		//Seis combinações...
		for(int state = 0; state < 6; state++)
		{
			String str = separator(codonSequence);
			       str = AminoacidTable.getInstance().generateAminoacid(str);
			
			int distance = getDistance(str);   
			
			codonSequence = codonSequence.substring(1);
			codons.add(new Codon(str, distance));
			
			if(state == 2)
				codonSequence = new StringBuilder(codonSequence).reverse().toString();
		}	
		
		return codons;
	}
	
	private static String separator(String codonSequence)
	{
		return codonSequence.replaceAll("(.{3})(?!$)", "$1,");
	}
}
