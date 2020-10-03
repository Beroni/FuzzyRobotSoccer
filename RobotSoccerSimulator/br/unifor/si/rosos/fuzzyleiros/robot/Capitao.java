package br.unifor.si.rosos.fuzzyleiros.robot;

import br.unifor.si.rosos.GameSimulator;

public class Capitao extends RoboFuzzy {
	
	public Capitao(GameSimulator g) {
		super(g);
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public void principal() {
		// TODO Auto-generated method stub
		runBall();
		//float a = (float)Math.random()*90-45;
		//setRotation(a);
	}

	@Override
	public String getNome() {
		// TODO Auto-generated method stub
		return "CP";
	}

}
