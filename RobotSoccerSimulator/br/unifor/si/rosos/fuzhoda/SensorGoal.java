package br.unifor.si.rosos.fuzhoda;
import br.unifor.si.rosos.Ball;
import br.unifor.si.rosos.GameSimulator;
import br.unifor.si.rosos.Goal;
import br.unifor.si.rosos.Robot;
import br.unifor.si.rosos.Sensor;
import br.unifor.si.rosos.TeamSide;
import processing.core.PVector;

class SensorGoal extends Sensor{

	float[] values = new float[2];
	float sensorLimit = 1f;
	
	private TeamSide teamSide;

	SensorGoal(GameSimulator g, Robot r, TeamSide teamSide){
		super(g, r);
		this.teamSide = teamSide;
	}

	float lastRead = 0;
	public float[] readValues(){
		// Avoid multiple readings within 100ms
		if(game.getTime() >= lastRead + 0.1f)
			doReading();

		return values;
	}

	private void doReading(){
		Robot thisRobot = getRobot();
		
		Goal goal = null;
		
		if(teamSide.equals(TeamSide.RIGHT)) {
			goal = getGameSimulator().goalLeft;
		}else {
			goal = getGameSimulator().goalRight;
		}

		// Find relative distance from Ball to Robot
		PVector dist = PVector.sub(goal.getPosition(), thisRobot.getPosition());
		dist.rotate(-thisRobot.getOrientation());

		// index 0 contains the Angle of the ball
		values[0] = (float)Math.toDegrees(dist.heading());
		// index 1 contains the distance to the ball
		values[1] = (float)Math.min(dist.mag(), sensorLimit);
	}

}