package br.edu.unifei.neuralGamePackage;

import java.util.*;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class GeneticAlgorithm {

	int generation = 0;
	Robot robot;
	Random random;
	Gameplay gameplay;
	float mutatingFactor;
	List<Genome> genomesList = new ArrayList<Genome>();
	
	public GeneticAlgorithm(Gameplay gameplay) throws AWTException
	{

		setGameplay(gameplay);
		random = new Random();
		robot = new Robot();
		
	}
	
	//params : gameplay and the genome to be mutated
	
	public void start()
	{
		System.out.print("Starting GeneticAlgorithm \n");
		initialMutate();
	}
	
	public void initialMutate ()
	{
		setGeneration(generation);
		System.out.print("Mutating \n");
		for(int i = 0 ; i<8 ;i++)
		{
			genomesList.add(new Genome(this.gameplay));
			System.out.print("Mutated Genome "+ i + " : " + 
					" a = " + genomesList.get(i).getA() + " b = " + genomesList.get(i).getB()+"\n");
		}
		evaluation();
	}
	
	public void mutate (Genome son1,Genome son2)
	{
		generation++;
		setGeneration(generation);
		System.out.print("Mutating \n");
		System.out.print("Mutated Genome 0 : " + 
				" a = " + genomesList.get(0).getA() + " b = " + genomesList.get(0).getB()+"\n");
		System.out.print("Mutated Genome 1 : " + 
				" a = " + genomesList.get(1).getA() + " b = " + genomesList.get(1).getB()+"\n");
		for(int i = 2 ; i<5 ;i++)
		{
			
				mutatingFactor = 2 * random.nextFloat();			
			
			genomesList.add(new Genome(this.gameplay,mutatingFactor,son1));
			System.out.print("Mutated Genome "+ i + " : " + 
					" a = " + genomesList.get(i).getA() + " b = " + genomesList.get(i).getB()+"\n");
		}
		for(int i = 5 ; i<8 ;i++)
		{
			
				mutatingFactor = 2 * random.nextFloat();			
			
			genomesList.add(new Genome(this.gameplay,mutatingFactor,son2));
			System.out.print("Mutated Genome "+ i + " : " + 
					" a = " + genomesList.get(i).getA() + " b = " + genomesList.get(i).getB()+"\n");
		}
		evaluation();
	}
	
	
	public int getGeneration() {
		return generation;
	}

	public void setGeneration(int generation) {
		this.generation = generation;
		gameplay.setGeneration(generation);
	}

	public void evaluation()
	{
		System.out.print("Starting a new game \n");
		
		
		for(int i = 0 ; i<8 ; i++)
		{
			gameplay.newGame(); //start a new game
			gameplay.setGenome(i);
			gameplay.setFitness(genomesList.get(i).getFitness());
			System.out.print("Evaluating genome "+i+" ! \n");
			while(gameplay.getTimer().isRunning()) //while there is game -> play
			{		
				
				int output = genomesList.get(i).CalculateOutput(); 
				if(output == KeyEvent.VK_RIGHT)
				{
					robot.delay(50);
					robot.keyPress(KeyEvent.VK_RIGHT);
				}
				if(output == KeyEvent.VK_LEFT)
				{
					robot.delay(50);
					robot.keyPress(KeyEvent.VK_LEFT);
				}				
			}
			System.out.print("Endgame ! \n");
			//after the game results
			System.out.print("Score/fitness : " + gameplay.getScore() + "\n");
			genomesList.get(i).setFitness(gameplay.getScore()); //setting fitness for the genome
					
		}
		select();
	}
	
	//destructive select process of the list
	public void select()
	{
		System.out.print("Selecting mom and dad \n");
		Genome dad;
		Genome mom;
		

		

		
		//find the highest fitness for dad
		dad = genomesList.get(0);
		genomesList.remove(0); 

		for(int i = 0; i < genomesList.size() ; i++)
		{
			if(genomesList.get(i).getFitness() > dad.getFitness())
			{
				dad = genomesList.get(i);
				genomesList.remove(i);	
			}
			
		}
		
		//find the highest fitness for mom
		mom = genomesList.get(0);
		genomesList.remove(0); 
		for(int i = 0; i < genomesList.size() ; i++)
		{
			if(genomesList.get(i).getFitness() > mom.getFitness())
			{
				mom = genomesList.get(i);
				genomesList.remove(i);	
			}
			
			
		}
		
		genomesList.clear();
		crossover(mom,dad);
	}
		
	
	public void crossover(Genome mom, Genome dad)
	{
		System.out.print("Crossover Process \n");
		System.out.print("Dad's fitness:"+dad.getFitness()+ "\n");
		System.out.print("mom's fitness:" +mom.getFitness()+ "\n");
		Genome son1 = new Genome(this.gameplay,mom,dad);
		Genome son2 = new Genome(this.gameplay,dad,mom);
		son1.setFitness(dad.getFitness());
		son2.setFitness(dad.getFitness());
		genomesList.add(son1);
		genomesList.add(son2);
		
		writeToText(son1);
		
	
		mutate(son1,son2);
	}

	
	public void writeToText(Genome son)
	{
		try{
			PrintWriter writer = new PrintWriter("trained-genome","UTF-8");
			writer.println(son.getA());
			writer.println(son.getB());
			writer.close();
			
		} catch (IOException e)
		{
			System.out.print("Cant write");
		}
	}
	
	//NAO IMPLEMENTADO PARA 2 SONS
	public void startBySavedGenom()
	{
		BufferedReader br = null;
		FileReader fr = null;
		float[] param = new float[2];
		try {

			fr = new FileReader("trained-genome");
			br = new BufferedReader(fr);

			String sCurrentLine;

			br = new BufferedReader(new FileReader("trained-genome"));
			
			int i =0;
			while ((sCurrentLine = br.readLine()) != null) {
				param[i] = Float.valueOf(sCurrentLine);
				i++;
			}
			
			Genome initial = new Genome(this.gameplay);
			initial.setA(param[0]); //setA
			initial.setB(param[1]); //setB
			
			genomesList.add(initial);
			genomesList.add(initial);
			mutate(initial,initial);
			

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

	
	public Random getRandom() {
		return random;
	}

	public void setRandom(Random random) {
		this.random = random;
	}

	public Gameplay getGameplay() {
		return gameplay;
	}

	public void setGameplay(Gameplay gameplay) {
		this.gameplay = gameplay;
	}


	public float getMutatingFactor() {
		return mutatingFactor;
	}

	public void setMutatingFactor(float mutatingFactor) {
		this.mutatingFactor = mutatingFactor;
	}

	public List<Genome> getGenomesList() {
		return genomesList;
	}

	public void setGenomesList(List<Genome> genomesList) {
		this.genomesList = genomesList;
	}

	

}
