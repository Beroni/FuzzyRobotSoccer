package br.unifor.si.rosos.testeTeam;

import br.unifor.si.rosos.*;
import br.unifor.si.rosos.fuzzyleiros.robot.RoboFuzzy;




public class T10 implements Team{

	TeamSide currentSide;

	public String getTeamName(){
		return "T10";
	}

	public void setTeamSide(TeamSide side){
		currentSide = side;
	}

	public Robot buildRobot(GameSimulator s, int index){
		RoboFuzzy robot = new Atacante(s);
		robot.setCurrentSide(currentSide);
		return robot;
	}



}