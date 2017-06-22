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
	

	
	Random rdm;

	int ballSpeed = 1;
	int ballPosX;
	int ballPosY = 50;
	int paddlePosX = 290;
	int paddleWidth = 90;
	int score = 0;
	
	
	public int getPaddleWidth()
	{
		return paddleWidth;
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
	

	
	public Gameplay()
	{
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		setRdm(new Random());
		timer = new Timer(delay,this);
		timer.start();
	}

	public void paint(Graphics g)
	{
		
		//background
		g.setColor(Color.black);
		g.fillRect(0, 0, 600, 600);
		
		//borders
		g.setColor(Color.red);
		g.fillRect(0, 0, 4, 600);
		g.fillRect(596, 0, 5, 600);
		g.fillRect(0, 0, 600, 3);

		//score
		g.setColor(Color.white);
		g.setFont(new Font("TimesRoman", Font.BOLD, 25));
		g.drawString(""+score, 550, 30);
		
		//paddle
		g.setColor(Color.white);
		g.fillRect(paddlePosX, 550, paddleWidth, 10);
		
		//ball
		g.setColor(Color.blue);
		g.fillOval(ballPosX, ballPosY, 18, 18);
	
		g.dispose();
		
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
		if (ballPosY > 580) {return true;}
		else {return false;}			
	}
	public void newBall()
	{
		ballPosY = 50;
		ballPosX = 	rdm.nextInt(500);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		timer.start();
		
		
		if (endBall())
		{
			newBall();
		}

		
		
		Rectangle r1 = new Rectangle(ballPosX, ballPosY, 18, 18);
		Rectangle r2 = new Rectangle(paddlePosX, 500, 100, 20);

		if (r1.intersects(r2))
		{
			score ++;
			if((score & 1) == 0) {ballSpeed++;} // if score is even , ballspeed++
			newBall();
			System.out.print("FLAG" + score + " " + ballSpeed + " ");
		}

			
		ballPosY += ballSpeed;
		
		
		repaint();
	
		

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			moveRight();
		}
		else 
		{
			if(e.getKeyCode() == KeyEvent.VK_LEFT)
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



	
}
