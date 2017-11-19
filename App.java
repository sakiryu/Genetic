package Classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class App {

	public static void main(String args[]) throws InterruptedException, IOException
	{
		LoadFile lf = LoadFile.getInstance();
		
		try 
		{
			lf.inicializa(new File("H37RvSequence.txt"));
		}
		catch (FileNotFoundException e)
		{
			System.out.println("Arquivo não encontrad!!");
		}
	
		System.out.println("Carregado.");
		
		
		//lf.print();
		List<Gene> a = Genetic.getHistance().getGenes();
		
		a.forEach(ge -> 
				{
					ge.printCodons();
					System.out.println("\n");
				});
		
		//a.forEach(ge -> System.out.println(ge.getCodons().size()));
		
	}
	
}
