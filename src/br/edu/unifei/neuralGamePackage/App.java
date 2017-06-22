package br.edu.unifei.neuralGamePackage;

import java.awt.AWTException;

import javax.swing.JFrame;

public class App {

	public static void main(String[] args) throws AWTException
	{
		JFrame frame = new JFrame();
		Gameplay gameplay = new Gameplay();
		Machine machine = new Machine(gameplay);
		frame.setBounds(200,200,600,600);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(gameplay);
		frame.setVisible(true);
		machine.Play();
	}
	
}
