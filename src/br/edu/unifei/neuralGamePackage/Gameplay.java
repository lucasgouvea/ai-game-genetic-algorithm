package br.edu.unifei.neuralGamePackage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Timer timer;
	private int delay = 10;
	

	
	public int getGeneration() {
		return generation;
	}


	public void setGeneration(int generation) {
		this.generation = generation;
	}

	Random rdm;
	public final int INITIAL_BALL_SPEED = 5; //para treino melhor aumentar
	public final int PADDLE_WIDTH = 30; 
	public final int PADDLE_HEIGTH = 10; 
	private int ballNumber = 0; //o jogo vai somente até 20 bolas
	private int ballsLost = 0;
	private int ballSpeed = 10;
	private int ballPosX;
	private int ballPosY = 50;
	private int paddlePosX = 290;

	private int fitness = 0; //for text
	private int genome = 0; //for text
	private int generation = 0; //for text 
	
	public int getScore() {
		return score;  //score é baseado na distancia que o paddle chega da ball agora
	}


	public void setScore(int score) {
		this.score = score;
	}

	private int score = 0;
	
	
	public Gameplay()
	{
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		setRdm(new Random());
		timer = new Timer(delay,this);
	}

	
	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public void paint(Graphics g)
	{
		
		//background
		g.setColor(Color.black);
		g.fillRect(0, 0, 600, 600);
		
		
		//x-distance
		g.setColor(Color.white);
		g.drawLine(paddlePosX, 555, ballPosX, 555);

		//score
		g.setColor(Color.white);
		g.setFont(new Font("TimesRoman", Font.BOLD, 20));
		g.drawString("Score  "+score, 480, 30);
		g.drawString("Balls Lost  "+ballsLost, 20, 30);
		//g.drawString("Fitness "+fitness, 240, 30);
		g.drawString("Genome "+genome, 340, 30);
		g.drawString("Gen " + generation, 150,30);
		
		//paddle
		g.setColor(Color.white);
		g.fillRect(paddlePosX, 550, PADDLE_WIDTH , 10);
		
		//ball
		g.setColor(Color.blue);
		g.fillOval(ballPosX, ballPosY, 18, 18);
	
		g.dispose();
		
	}
	
	public int getFitness() {
		return fitness;
	}


	public void setFitness(int fitness) {
		this.fitness = fitness;
	}


	public int getGenome() {
		return genome;
	}


	public void setGenome(int genome) {
		this.genome = genome;
	}


	public void moveRight()
	{

		paddlePosX += 7;
		paddlePosX += 7;
		
	}
	
	public void moveLeft()
	{

		paddlePosX -= 7;
		paddlePosX -= 7;
	}
	
	public boolean endBall()
	{
		if (ballPosY > 580)
		{
			ballsLost++;
			return true;
		}
		else {return false;}			
	}
	public void newBall()
	{
		ballNumber++;
		ballPosY = 50;
		ballPosX = 	rdm.nextInt(500);
	}
	
	//SET INITIAL
	public void newGame()
	{
		
		score = 0;
		ballSpeed = INITIAL_BALL_SPEED;
		ballNumber = 0;
		ballPosY = 50;
		ballsLost = 0;		
		paddlePosX = 290;
		timer.start();

	
	}

	public void endGame()
	{
		//PAUSAR
		timer.stop();
	}
	
	public boolean ballHitsPaddle()
	{
		Rectangle r1 = new Rectangle(ballPosX, ballPosY, 18, 12);
		Rectangle r2 = new Rectangle(paddlePosX, 500, PADDLE_WIDTH , PADDLE_HEIGTH);
		if (r1.intersects(r2))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		if (ballNumber >= 20)
		{
			endGame();
		}
		
		ballPosY += ballSpeed; //runs the ball
		
		if (endBall())
		{
			score += scoreCalc();
			newBall();
		}
		
		if(ballHitsPaddle())
		{
			score += scoreCalc();
			newBall();
		}
		
		repaint();
	}

	public int scoreCalc()
	{
		int distance = Math.abs(ballPosX-paddlePosX ); //distance fromb ball to paddle
		
		
		if(distance > 570) { return 0; }
		if(distance > 550) { return 2; }
		if(distance > 530) { return 4; }
		if(distance > 510) { return 6; }
		if(distance > 490) { return 8; }
		if(distance > 470) { return 10; }
		if(distance > 450) { return 12; }
		if(distance > 430) { return 14; }
		if(distance > 410) { return 16; }
		if(distance > 390) { return 18; }
		if(distance > 370) { return 20; }
		if(distance > 350) { return 22; }
		if(distance > 330) { return 24; }
		if(distance > 310) { return 26; }
		if(distance > 290) { return 28; }
		if(distance > 270) { return 30; }
		if(distance > 250) { return 32; }
		if(distance > 230) { return 34; }
		if(distance > 210) { return 36; }
		if(distance > 190) { return 38; }
		if(distance > 180) { return 40; }
		if(distance > 170) { return 42; }
		if(distance > 160) { return 44; }
		if(distance > 150) { return 46; }
		if(distance > 140) { return 48; }
		if(distance > 130) { return 50; }
		if(distance > 120) { return 52; }
		if(distance > 110) { return 54; }
		if(distance > 100) { return 56; }
		if(distance > 90) { return 58; }
		if(distance > 80) { return 60; }
		if(distance > 70) { return 62; }
		if(distance > 60) { return 64; }
		if(distance > 50) { return 66; }
		if(distance > 40) { return 68; }
		if(distance > 30) { return 70; }
		if(distance > 20) { return 75; }
		if(distance > 10) { return 80; }
		if(distance > 9) { return 85; }
		if(distance > 8) { return 90; }
		if(distance > 7) { return 95; }
		if(distance > 6) { return 100; }
		if(distance > 5) { return 105; }
		if(distance > 4) { return 110; }
		if(distance > 3) { return 115; }
		if(distance > 2) { return 120; }
		if(distance > 1) { return 125; }
		if(distance > 0) { return 130; }
		
		return 0;
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT && (paddlePosX+PADDLE_WIDTH ) < 600)
		{
			moveRight();
		}
		else 
		{
			if(e.getKeyCode() == KeyEvent.VK_LEFT && (paddlePosX) > 0)
			{
				moveLeft();
			}
		}

		
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	//GETS AND SETS
	
	public int getBallsLost() {
		return ballsLost;
	}
	public void setBallsLost(int ballsLost) {
		this.ballsLost = ballsLost;
	}

	public int getBallPosX()
	{
		return ballPosX;
	}
	public int getPaddlePosX()
	{
		return paddlePosX;
	}
	public void setBallPosX(int ballPosX)
	{
		this.ballPosX = ballPosX;
	}
	
	public void setRdm(Random rdm) {
		this.rdm = rdm;
	}

	
}
