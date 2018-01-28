package br.edu.unifei.neuralGamePackage;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;


public class App {

	public static void main(String[] args) throws AWTException, InterruptedException
	{
		//fitness : baseado na distancia que o paddle chega da ball antes dela sumir
		
		
		Robot robot = new Robot();
		
		Gameplay gameplay = new Gameplay();
		JFrame frame = new JFrame();
		Machine machine = new Machine(gameplay);
		machine.getGameplay().newGame();

		
		//GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(gameplay);
		frame.setBounds(200,200,600,600);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(gameplay);
		frame.setVisible(true);
		robot.delay(1000);
		
		//geneticAlgorithm.startBySavedGenom();

		

		 
		
		



		
		
	}
	

	
	
}
