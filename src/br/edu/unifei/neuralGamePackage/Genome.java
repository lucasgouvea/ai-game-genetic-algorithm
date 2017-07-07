package br.edu.unifei.neuralGamePackage;

import java.awt.event.KeyEvent;
import java.util.Random;

public class Genome {

	private float a;
	private float b;
	private float distanceFromBall; // in
	private float output; 
	private Gameplay gameplay;
	private Random random;
	private int fitness;

	
	public int getFitness() {
		return fitness;
	}
	public void setFitness(int fitness) {
		this.fitness = fitness;
	}
	//for initial genome
	public Genome(Gameplay gameplay)
	{
		this.gameplay = gameplay;
		random = new Random();
		a = -1*random.nextFloat() + random.nextFloat(); // -1 < a < 1
		b = -1*random.nextFloat() + random.nextFloat(); // -1 < b < 1		
	}
	//for crossover genome
	public Genome(Gameplay gameplay, Genome mom, Genome dad)
		{
			this.gameplay = gameplay;

			a = dad.getA(); 
			b = mom.getB(); 		
		}
	//for mutating genome
	public Genome(Gameplay gameplay, float mutatingFactor, Genome originalGenome)
	{
		this.gameplay = gameplay;
		random = new Random();
		a = (mutatingFactor)*originalGenome.getA(); // -1*mf < a*mf < 1*mf
		b = (mutatingFactor)*originalGenome.getB(); // -1*mf < b*mf < 1*mf	
	}
	
	public int CalculateOutput()
	{
		int ballPosX = gameplay.getBallPosX();
		int paddleCenter = gameplay.getPaddlePosX() + gameplay.PADDLE_WIDTH/2;
		distanceFromBall = ballPosX - paddleCenter;
		distanceFromBall = distanceFromBall / 300; //adjustment -> -1 < distance/300 < 1
		output = distanceFromBall * a + b;
		//System.out.printf("a : %.2f ", a);
		//System.out.printf("b : %.2f ", b);
		//System.out.printf("out : %.2f \n", output);
		

		if(output >= 0.1)
		{
			return KeyEvent.VK_LEFT;
		}
		if(output <= -0.1)
		{
			return KeyEvent.VK_RIGHT;
		}

			return 0;
		
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
	public float getA() {
		return a;
	}
	public void setA(float a) {
		this.a = a;
	}
	public float getB() {
		return b;
	}
	public void setB(float b) {
		this.b = b;
	}

	
	
	
	
	
}
