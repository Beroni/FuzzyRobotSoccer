package br.unifor.si.rosos.testeTeam;

import br.unifor.si.rosos.GameSimulator;
import br.unifor.si.rosos.fuzzyleiros.robot.RoboFuzzy;

public class Goleiro extends RoboFuzzy {
	
	public Goleiro(GameSimulator g) {
		super(g);
		// TODO Auto-generated constructor stub
	}
	
	public String getNome() {
		
		return "GK";
	}

	@Override
	public void principal() {
		//System.out.println(this.getPosition().x + " - "+ this.getPosition().y);
		runDefesa();
		
	}

	
	

}
