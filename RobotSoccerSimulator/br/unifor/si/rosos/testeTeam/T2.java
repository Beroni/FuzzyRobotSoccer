package br.unifor.si.rosos.testeTeam;

import br.unifor.si.rosos.*;
import br.unifor.si.rosos.fuzzyleiros.robot.RoboFuzzy;
import processing.core.*;
import java.util.*;



public class T2 implements Team{

	TeamSide currentSide;

	public String getTeamName(){
		return "Time 2";
	}

	public void setTeamSide(TeamSide side){
		currentSide = side;
	}

	public Robot buildRobot(GameSimulator s, int index){
		if (index == 0  ) {
			RoboFuzzy robot = new Defensor	(s);
			robot.setCurrentSide(currentSide);
			return robot;
		}else {
			RoboFuzzy robot = new Goleiro(s);
			robot.setCurrentSide(currentSide);
			return robot;
		}
	}



}