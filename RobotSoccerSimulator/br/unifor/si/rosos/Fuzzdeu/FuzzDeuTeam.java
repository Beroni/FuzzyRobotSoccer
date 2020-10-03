package br.unifor.si.rosos.Fuzzdeu;

import br.unifor.si.rosos.*;
import processing.core.*;
import java.util.*;



public class FuzzDeuTeam implements Team{
	
	public String getTeamName(){
		return "Victor e Daniel";
	}

	public void setTeamSide(TeamSide side){

	}

	public Robot buildRobot(GameSimulator s, int index){
		return new Fuzzdeu(s);
	}
 

 
}