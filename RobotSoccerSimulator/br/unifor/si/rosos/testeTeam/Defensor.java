package br.unifor.si.rosos.testeTeam;

import br.unifor.si.rosos.GameSimulator;
import br.unifor.si.rosos.fuzzyleiros.robot.Perfil;
import br.unifor.si.rosos.fuzzyleiros.robot.RoboFuzzy;

public class Defensor extends RoboFuzzy {
	
	public Defensor(GameSimulator g) {
		super(g);
		// TODO Auto-generated constructor stub
	}
	
		
	@Override
	public void setup() {
		super.setup();
		setEstilo(Perfil.D);
	}

	@Override
	public void principal() {
		acao();
	}

	public String getNome() {
		// TODO Auto-generated method stub
		return "D";
	}
	

}
