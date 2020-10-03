package br.unifor.si.rosos.testeTeam;

import br.unifor.si.rosos.GameSimulator;
import br.unifor.si.rosos.fuzzyleiros.robot.RoboFuzzy;

public class Atacante extends RoboFuzzy {
	
	public Atacante(GameSimulator g) {
		super(g);
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public void principal() {
		runBall();
		limites();
	}

	@Override
	public String getNome() {
		// TODO Auto-generated method stub
		return "ST";
	}

}
