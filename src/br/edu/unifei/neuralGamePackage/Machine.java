package br.edu.unifei.neuralGamePackage;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class Machine extends Player{

	private Gameplay gameplay;
	
	
	int ballPosX;
	int paddleCenter;
	Robot robot;
	
	public Machine(Gameplay gameplay) throws AWTException
	{
		this.setGameplay(gameplay);
		this.setRobot(new Robot());
	}

	public void setRobot(Robot robot)
	{
		this.robot = robot;
	}

	public Gameplay getGameplay() {
		return gameplay;
	}

	public void setGameplay(Gameplay gameplay) {
		this.gameplay = gameplay;
	}
	
	public void Play()
	{
		robot.delay(1500);
	
		while(gameplay.getTimer().isRunning())
		{
			ballPosX = gameplay.getBallPosX();
			paddleCenter = gameplay.getPaddlePosX() + gameplay.PADDLE_WIDTH/2;
			
			if(ballPosX - paddleCenter < 0)
			{
				robot.keyPress(KeyEvent.VK_LEFT);
			}
			if(ballPosX - paddleCenter > 0)
			{
				robot.keyPress(KeyEvent.VK_RIGHT);
			}

		}
			
	}
	
}
