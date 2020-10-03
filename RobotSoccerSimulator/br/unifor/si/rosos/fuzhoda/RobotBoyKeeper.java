package br.unifor.si.rosos.fuzhoda;

import br.unifor.si.rosos.Ball;
import br.unifor.si.rosos.GameSimulator;
import br.unifor.si.rosos.RobotBasic;
import br.unifor.si.rosos.Sensor;
import br.unifor.si.rosos.TeamSide;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;

public class RobotBoyKeeper extends RobotBasic {
	private float velocidade;
	private RobotConfig robotConfig;
	
	private Sensor locator;
	private Sensor compass;
	private Sensor front, right, back, left;
	private Sensor goal;
	
	private float goalDir;
	
	private Ball ball;

	public RobotBoyKeeper(GameSimulator g, RobotConfig robotConfig) { 
		super(g);
		ball = g.ball;
		this.robotConfig = robotConfig;
		
		SensorGoal goal = new SensorGoal(g, this, this.robotConfig.getTeamSide());
		registerSensor(goal, "GOAL");
	}
	
	public void setup(){
		velocidade = 1f;
		
		System.out.println("Running!");

		locator = getSensor("BALL");
		compass = getSensor("COMPASS");

		front = getSensor("ULTRASONIC_FRONT");
		left = getSensor("ULTRASONIC_LEFT");
		back = getSensor("ULTRASONIC_BACK");
		right = getSensor("ULTRASONIC_RIGHT");
		
		goal = getSensor("GOAL");

		goalDir = 0f;
		// Find Goal Direction
		if(this.robotConfig.getTeamSide() == TeamSide.RIGHT)
			goalDir = 180f;
	}

	public void loop(){
		FIS fis = this.robotConfig.getFis();
		
		readGoal(fis);
		readBall(fis);
		ultraSonic(fis);
		
//		float goalAngle = goal.readValue(0);
//		float goalDist = goal.readValue(1);
//		
//		float sonicFront = front.readValue(0);
//		float sonicRight = right.readValue(0);
//		float sonicBack = back.readValue(0);
//		float sonicLeft = left.readValue(0);
//		
//		System.out.print(this.getRealPosition().x + " ");
//		System.out.print(this.getRealPosition().y + " ");
//		System.out.print(locator.readValue(1) + " ");
//		System.out.println(this.getOrientation());
		
		fis.evaluate();
		
		FunctionBlock functionBlock = fis.getFunctionBlock("fuzhoda");
		
		float vX = (float) functionBlock.getVariable("vX").getValue();
		float vY = (float) functionBlock.getVariable("vY").getValue();
//		lookToTheBall();
		
		System.out.print(vX + " | ");
		System.out.println(vY);
		
		setSpeed(vX , vY);
	}
	
	public void ultraSonic(FIS fis) {
		float sonicFront = front.readValue(0);
		float sonicRight = right.readValue(0);
		float sonicBack = back.readValue(0);
		float sonicLeft = left.readValue(0);
		
//		fis.setVariable("sonicFront", Math.abs(sonicFront));
		fis.setVariable("sonicRight", Math.abs(sonicRight));
//		fis.setVariable("sonicBack", Math.abs(sonicBack));
		fis.setVariable("sonicLeft", Math.abs(sonicLeft));
		
		System.out.print("Sonic: ");
		System.out.print(sonicLeft + " | ");
		System.out.print(sonicRight + " | ");
	}
	
	public void readGoal(FIS fis) {
		float goalAngle = goal.readValue(0) * -1;
		float goalDist = goal.readValue(1);

//		float rads = (float)Math.toRadians(ballAngle);
//		float ballX = (float)Math.sin(rads);
//		float ballY = (float)Math.cos(rads);
		
		if(goalAngle < 0) {
			goalAngle = 360 + goalAngle;
		}
		System.out.print("Goal: ");
		System.out.print(goalDist + " | ");
		System.out.print(goalAngle + " | ");
		
		fis.setVariable("goalAngle", goalAngle);
		fis.setVariable("goalDist", Math.abs(goalDist));
//		fis.setVariable("goalX", ballX);
//		fis.setVariable("goalY", ballY);
	}
	
	public void readBall(FIS fis) {
		float ballAngle = locator.readValue(0) * -1;
		float ballDist = locator.readValue(1);

//		float rads = (float)Math.toRadians(ballAngle);
//		float ballX = (float)Math.sin(rads);
//		float ballY = (float)Math.cos(rads);
		float ballX = this.getPosition().x - this.ball.getPosition().x;
		float ballY = this.getPosition().y - this.ball.getPosition().y;
		
		
		if(ballAngle < 0) {
			ballAngle = 360 + ballAngle;
		}

		System.out.print("Ball: ");
		System.out.print(ballDist + " | ");
		System.out.print(ballAngle + " | ");
		
		fis.setVariable("ballAngle", ballAngle);
		fis.setVariable("ballDist", Math.abs(ballDist));
		fis.setVariable("ballX", ballX);
		fis.setVariable("ballY", ballY);
	}
	
//	private void lookToTheBall() {
//		float comp = compass.readValue(0);
//
//		float ballAngle = locator.readValue(0);
//		float ballDist = locator.readValue(1);
//		
//		System.out.println("Angle:" + ballAngle);
//		System.out.println("Dists:" + ballDist);
//		
//		setRotation(ballAngle * 10);
//	}
}
