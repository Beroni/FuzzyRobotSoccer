package br.unifor.si.rosos.fuzzyleiros.sensor;

import br.unifor.si.rosos.GameSimulator;
import br.unifor.si.rosos.Goal;
import br.unifor.si.rosos.Robot;
import br.unifor.si.rosos.Sensor;
import br.unifor.si.rosos.TeamSide;
import processing.core.PVector;

public class SensorGoal extends Sensor {
	

	public SensorGoal(GameSimulator g, Robot r) {
		super(g, r);
		// TODO Auto-generated constructor stub
	}
	private float readAngleLeft(){
		// Avoid multiple readings within 100ms
		Robot thisRobot = getRobot();
		
		PVector robotPos = new PVector(thisRobot.getPosition().x, thisRobot.getPosition().y);
		
		Goal goalLeft = getGameSimulator().goalLeft;
		
		PVector goalPos = new PVector(goalLeft.getPosition().x, goalLeft.getPosition().y );
		
		PVector distLeft = PVector.sub(goalPos, robotPos);
		
		distLeft.rotate(-thisRobot.getOrientation());
		
		return (float)Math.toDegrees(distLeft.heading());
	}
	
	private float readAngleRight(){
		// Avoid multiple readings within 100ms
		Robot thisRobot = getRobot();
		
		PVector robotPos = new PVector(thisRobot.getPosition().x, thisRobot.getPosition().y);
		
		Goal goalRight = getGameSimulator().goalRight;
		
		PVector goalPos = new PVector(goalRight.getPosition().x , goalRight.getPosition().y);
		
		PVector distRight = PVector.sub(goalPos, robotPos);
		
		distRight.rotate(-thisRobot.getOrientation());
		
		return (float)Math.toDegrees(distRight.heading());
		
	}
	
	private float readDistXLeft(){
		Robot thisRobot = getRobot();
		Goal goalLeft = getGameSimulator().goalLeft;
		return (float)Math.abs(goalLeft.getPosition().x -thisRobot.getPosition().x);
		
	}
	
	private float readDistXRight(){
		Robot thisRobot = getRobot();
		Goal goalRight = getGameSimulator().goalRight;
		return (float)Math.abs(goalRight.getPosition().x -thisRobot.getPosition().x);
		
	}
	
	private float readDistYLeft(){
		Robot thisRobot = getRobot();
		Goal goalLeft = getGameSimulator().goalLeft;
		return (float)goalLeft.getPosition().y -thisRobot.getPosition().y;
		
	}
	
	private float readDistYRight(){
		Robot thisRobot = getRobot();
		Goal goalRight = getGameSimulator().goalRight;
		return (float)goalRight.getPosition().y -thisRobot.getPosition().y;
		
	}
	
	
	public float readAdversaryAngle(TeamSide currentSide) {
		if (currentSide == TeamSide.RIGHT)
			return this.readAngleLeft();
		else
			return this.readAngleRight();

	}
	
	public float readTeamAngle(TeamSide currentSide) {
		if (currentSide == TeamSide.RIGHT)
			return this.readAngleRight();
		else
			return this.readAngleLeft();
	}
	
	public float readTeamXdis(TeamSide currentSide) {
		if (currentSide == TeamSide.RIGHT)
			return this.readDistXRight();
		else
			return this.readDistXLeft();
	}
	
	public float readAdversaryXdis(TeamSide currentSide) {
		if (currentSide == TeamSide.RIGHT)
			return this.readDistXLeft();
		else
			return this.readDistXRight();
	}
	
	public float readTeamYdis(TeamSide currentSide) {
		if (currentSide == TeamSide.RIGHT)
			return this.readDistYRight();
		else
			return -this.readDistYLeft();
	}
	
	public float readAdversaryYdis(TeamSide currentSide) {
		if (currentSide == TeamSide.RIGHT)
			return this.readDistYLeft();
		else
			return this.readDistYRight();
	}
	
	
	

}
