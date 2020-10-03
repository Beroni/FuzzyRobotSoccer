package br.unifor.si.rosos.testeTeam;

import br.unifor.si.rosos.GameSimulator;
import br.unifor.si.rosos.fuzzyleiros.robot.Perfil;
import br.unifor.si.rosos.fuzzyleiros.robot.RoboFuzzy;

public class MeioCampo extends RoboFuzzy {
	
	public MeioCampo(GameSimulator g) {
		super(g);
		// TODO Auto-generated constructor stub
	}
	
		
	@Override
	public void setup() {
		super.setup();
		setEstilo(Perfil.M);
	}

	@Override
	public void principal() {
		acao();
	}

	public String getNome() {
		// TODO Auto-generated method stub
		return "M";
	}
	

}
