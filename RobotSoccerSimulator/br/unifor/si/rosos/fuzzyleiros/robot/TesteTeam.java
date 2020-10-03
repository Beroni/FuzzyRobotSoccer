package br.unifor.si.rosos.fuzzyleiros.robot;

import br.unifor.si.rosos.*;
import processing.core.*;
import java.util.*;



public class TesteTeam implements Team{

	TeamSide currentSide;

	public String getTeamName(){
		return "Alvo";
	}

	public void setTeamSide(TeamSide side){
		currentSide = side;
	}

	public Robot buildRobot(GameSimulator s, int index){
		return new TesteRobo2(s);
	}



}