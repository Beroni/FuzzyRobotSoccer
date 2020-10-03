package br.unifor.si.rosos.fuzzyleiros.sensor;

import br.unifor.si.rosos.GameController;
import br.unifor.si.rosos.GameSimulator;
import br.unifor.si.rosos.Robot;
import br.unifor.si.rosos.Sensor;
import br.unifor.si.rosos.TeamSide;

public class SensorGame extends Sensor {

	public SensorGame(GameSimulator g, Robot r) {
		super(g, r);
		// TODO Auto-generated constructor stub
	}

	
	public float getTime() {
		return getGameSimulator().getTime();
	}
	
	public int getTeamScore(TeamSide side) {
		return GameController.getPointsFor(side);
	}
	
	public int getAdversaryScore(TeamSide side) {
		if(side == TeamSide.LEFT)
			return GameController.getPointsFor(TeamSide.RIGHT);
		else
			return GameController.getPointsFor(TeamSide.LEFT);
	}
}
