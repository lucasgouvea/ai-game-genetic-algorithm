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

	private static final long serialVersionUID = 1L;
	private Timer timer;
	private int delay = 10;
		
	public final int INITIAL_BALL_SPEEDX = 1;
	public final int INITIAL_BALL_SPEEDY = 2;
	public final int PADDLE_WIDTH = 60; 
	public final int PADDLE_HEIGTH = 20;	
	public final int ARENA_BOUNDS_INF = 35;
    public final int ARENA_BOUNDS_SUP =	580;
	public final int PADDLE1_POSY = ARENA_BOUNDS_SUP - 40;
	public final int PADDLE2_POSY =	ARENA_BOUNDS_INF + 40;
	private int ballNumber = 0; //o jogo vai somente ate 20 bolas
	private int ballsLost = 0;
	private int ballSpeedX = 2;
	private int ballSpeedY = 1;
	private int ballPosX;
	private int ballPosY = 50;
	private int paddle1PosX = 290;
	private int paddle2PosX = 290;

	private int fitness = 0; //for text
	private int genome = 0; //for text
	private int generation = 0; //for text 
	private int score = 0;
	
	
	public Gameplay()
	{
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay,this);
	}
	public void paint(Graphics g)
	{	
		//background
		g.setColor(Color.black);
		g.fillRect(0, 0, 600, 600);	
		//arena
		g.setColor(Color.yellow);
		g.drawLine(ARENA_BOUNDS_INF, ARENA_BOUNDS_INF, ARENA_BOUNDS_INF, ARENA_BOUNDS_SUP);
		g.drawLine(ARENA_BOUNDS_INF, ARENA_BOUNDS_INF, ARENA_BOUNDS_SUP, ARENA_BOUNDS_INF);
		g.drawLine(580, ARENA_BOUNDS_INF, ARENA_BOUNDS_SUP, ARENA_BOUNDS_SUP);
		g.drawLine(ARENA_BOUNDS_INF, ARENA_BOUNDS_SUP, ARENA_BOUNDS_SUP, ARENA_BOUNDS_SUP);
		//x-distance
		g.setColor(Color.blue);
		g.drawLine(paddle1PosX, 555, ballPosX, 555);
		g.drawLine(paddle1PosX, 555, ballPosX, 555);
		//score
		g.setColor(Color.white);
		g.setFont(new Font("TimesRoman", Font.BOLD, 20));
		g.drawString("Score  "+score, 480, 30);
		g.drawString("Balls Lost  "+ballsLost, 20, 30);
		//g.drawString("Fitness "+fitness, 240, 30);
		g.drawString("Genome "+genome, 340, 30);
		g.drawString("Gen " + generation, 175,30);
		//paddle1
		g.setColor(Color.blue);
		g.fillRect(paddle1PosX, PADDLE1_POSY, PADDLE_WIDTH , 10);
		//paddle2
		g.setColor(Color.red);
		g.fillRect(paddle2PosX, PADDLE2_POSY, PADDLE_WIDTH , 10);
		//ball
		g.setColor(Color.yellow);
		g.fillOval(ballPosX, ballPosY, 18, 18);
		g.dispose();
	}
	
	public void moveRight()
	{
		paddle1PosX += 13;
		paddle2PosX += 13;	
	}
	
	public void moveLeft()
	{
		paddle1PosX -= 13;
		paddle2PosX -= 13;
	}
	
	public boolean endBall()
	{
		if (ballPosY > ARENA_BOUNDS_SUP || ballPosY < ARENA_BOUNDS_INF)
		{
			ballsLost++;
			return true;
		}
		else {return false;}			
	}
	public void newBall()
	{
		ballNumber++;
		ballPosY = 250;
		ballPosX = new Random().nextInt(500) + ARENA_BOUNDS_INF;
	}
	
	//SET INITIAL
	public void newGame()
	{
		score = 0;
		ballSpeedX = INITIAL_BALL_SPEEDX;
		ballSpeedY = INITIAL_BALL_SPEEDY;
		ballNumber = 0;
		ballPosY = 250;
		ballPosX = new Random().nextInt(500) + ARENA_BOUNDS_INF;
		ballsLost = 0;		
		paddle1PosX = 290;
		timer.start();
	}

	public void endGame()
	{
		timer.stop();
	}
	
	public boolean ballHitspaddle1()
	{
		Rectangle r1 = new Rectangle(ballPosX, ballPosY, 12, 7);
		Rectangle r2 = new Rectangle(paddle1PosX, PADDLE1_POSY, PADDLE_WIDTH , PADDLE_HEIGTH);
		if (r1.intersects(r2))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean ballHitspaddle2()
	{
		Rectangle r1 = new Rectangle(ballPosX, ballPosY, 12, 7);
		Rectangle r2 = new Rectangle(paddle2PosX, PADDLE2_POSY, PADDLE_WIDTH , PADDLE_HEIGTH);
		if (r1.intersects(r2))
		{
			return true;
		}
		else
		{
			return false;
		}
	}	
	
	private boolean ballHitsArenaBorder() {
		if(ballPosX < ARENA_BOUNDS_INF || ballPosX > ARENA_BOUNDS_SUP - 10) {
			return true;
		}
		return false;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (ballNumber >= 20)
		{
			endGame();
		
		}
		ballPosX += ballSpeedX; //runs the ball
		ballPosY += ballSpeedY; //runs the ball
		if (endBall())
		{
			score += scoreCalc();
			newBall();
		}
		if(ballHitspaddle1() || ballHitspaddle2())
		{
			score += scoreCalc();
			this.ballSpeedY = this.ballSpeedY * -1;
		}
		if(ballHitsArenaBorder()) {
			this.ballSpeedX = this.ballSpeedX * -1;
		}
		repaint();
	}

	public int scoreCalc()
	{
		int distance = Math.abs(ballPosX-paddle1PosX ); //distance fromb ball to paddle1
		
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
		if(e.getKeyCode() == KeyEvent.VK_RIGHT && (paddle1PosX+PADDLE_WIDTH ) < ARENA_BOUNDS_SUP - 2)
		{
			moveRight();
		}
		else 
		{
			if(e.getKeyCode() == KeyEvent.VK_LEFT && (paddle1PosX) > ARENA_BOUNDS_INF + 10)
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
	public int getPaddle1PosX()
	{
		return paddle1PosX;
	}
	public void setBallPosX(int ballPosX)
	{
		this.ballPosX = ballPosX;
	}
	public int getGeneration() {
		return generation;
	}
	public void setGeneration(int generation) {
		this.generation = generation;
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
	public Timer getTimer() {
		return timer;
	}
	public void setTimer(Timer timer) {
		this.timer = timer;
	}
	public int getScore() {
		return score;  //score � baseado na distancia que o paddle1 chega da ball agora
	}
	public void setScore(int score) {
		this.score = score;
	}





	
}
