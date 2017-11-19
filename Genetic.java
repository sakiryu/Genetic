package Classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Genetic {
	
	private List<Gene> genes = new ArrayList<>();
	private static Genetic este = null;
    private AminoacidTable table = AminoacidTable.getInstance();
	
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
			int endIndex = codons.lastIndexOf(FIRST);//getIndex(codons, end);
		
			//Medir a distância
			int distance = (endIndex - firstIndex);
		
			//Se for negativo = inválido(?)
			return distance;
		}
		
		return 0;
	}
	
	/*public static List<String> createCodons(List<Character> list)
	{	
		List<String> codons = new ArrayList<>();
		
		//Seis combinações...
		for(int state = 0; state < 6; state++)
		{
			String str = concatenateChar(list);
			 str = AminoacidTable.getInstance().generateAminoacid(concatenateChar(list));
			
			list.remove(0);
			codons.add(str);
			
			if(state == 2)
				Collections.reverse(list);
		}	
		
		return codons;
	}*/
	
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
	
	/*public static String concatenateChar(List<Character> list)
	{
		
		String strCodon = list.stream()
				            .map(c -> c.toString())                
				            .collect(Collectors.joining())
				            .replaceAll("(.{3})(?!$)", "$1,");
		                                 //oh...
		
		return strCodon;
	}*/

}
