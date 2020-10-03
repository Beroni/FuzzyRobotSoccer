package br.unifor.si.rosos.testeTeam;

import br.unifor.si.rosos.*;
import br.unifor.si.rosos.fuzzyleiros.robot.Goleiro;
import br.unifor.si.rosos.fuzzyleiros.robot.RoboFuzzy;
import processing.core.*;
import java.util.*;



public class T1 implements Team{

	TeamSide currentSide;

	public String getTeamName(){
		return "Time 1";
	}

	public void setTeamSide(TeamSide side){
		currentSide = side;
	}

	public Robot buildRobot(GameSimulator s, int index){
		RoboFuzzy robot = new Goleiro(s);
		robot.setCurrentSide(currentSide);
		return robot;
	}



}