package br.unifor.si.rosos.fuzzyleiros.robot;

import br.unifor.si.rosos.*;
import processing.core.*;
import java.util.*;



public class FuzzyleirosTeam implements Team{

	TeamSide currentSide;

	public String getTeamName(){
		return "Beroni e Diego";
	}

	public void setTeamSide(TeamSide side){
		currentSide = side;
	}

	public Robot buildRobot(GameSimulator s, int index){
		RoboFuzzy robot = new RoboFuzzy(s, index);
		robot.setCurrentSide(currentSide);
		return robot;
		
	}



}