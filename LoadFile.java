package Classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoadFile {

	private static LoadFile este = null; 
	private File sourceFile;
	private Genetic genetic = Genetic.getHistance();
	private boolean loaded;

	private void loadData() throws FileNotFoundException 
	{
		if (sourceFile == null) return;
		
		Pattern pattern = Pattern.compile("(\\[(.*?)\\])");
		Pattern patloc = Pattern.compile("(\\[location=(\\d+)..(\\d+)\\])");
		Pattern patLocus = Pattern.compile("(\\[locus_tag=(.*?)\\])");

		Scanner data = new Scanner(sourceFile);

		String locus = "";
		long begin = -1;
		long end = -1;
		String sequenc = "";

		// Percorre o arquivo do genoma
		while (data.hasNextLine())
		{
			String line = data.nextLine();
			
			// Se é uma linha de cabeçalho ...
			if (line.length() > 0 && line.charAt(0) == '>') 
			{
				if (begin != -1) 
				{ 	
					genetic.addGene(new Gene(locus, sequenc, begin, end));
					
					locus = "";
					begin = -1;
					end = -1;
					sequenc = "";
				}
				
				Matcher matcher = pattern.matcher(line);
				
				// Procura pelos dados
				while (matcher.find())
				{
					String token = matcher.group(1);
					Matcher matchLoc = patloc.matcher(token);
					
					if (matchLoc.matches()) 
					{
						begin = Long.parseLong(matchLoc.group(2));
						end = Long.parseLong(matchLoc.group(3));
					} 
					else 
					{
						Matcher matchLocus = patLocus.matcher(token);
						
						if (matchLocus.matches()) 
							locus = matchLocus.group(2);
					}
				}
			}
			else
			{
				for(char c : line.toCharArray()){sequenc += c;}
			}
		}
		
		data.close();
		loaded = true;
	}
	
	public void print()
	{
		genetic.getGenes().forEach(g -> 
		{
			System.out.println(g.getSequence());
			g.printCodons();
			
			System.out.println("\n");
			
		//	try {Thread.sleep(500);}
		//	catch (InterruptedException e) {e.printStackTrace();}
		});
	}
	
	private LoadFile()
	{
		loaded = false;
		sourceFile = null;
	}
	
	public static LoadFile getInstance()
	{
		if (este == null)
		{
			este = new LoadFile();
		}
		
		return este;
	}
	
	public void saveFile(String fileName) throws IOException
	{
		PrintWriter pr = new PrintWriter(new FileWriter(fileName, true ));
		genetic.getGenes().forEach(p -> pr.println(p + "\n"));
		
	}
	
	public void inicializa(File sourceFile) throws FileNotFoundException 
	{
		this.sourceFile = sourceFile;
		this.loadData();
		this.loaded = true;
	}
}