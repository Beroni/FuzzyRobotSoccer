package br.unifor.si.rosos.fuzhoda;

import br.unifor.si.rosos.GameSimulator;
import br.unifor.si.rosos.Robot;
import br.unifor.si.rosos.Team;
import br.unifor.si.rosos.TeamSide;

public class FuzRoDaTeam implements Team{
	private TeamSide teamSide;

	@Override
	public String getTeamName() {
		return "Daniel e Arthur";
	}	
	
	@Override
	public void setTeamSide(TeamSide side){
		teamSide = side;
	}

	@Override
	public Robot buildRobot(GameSimulator simulator, int index) {
		RobotConfig robotconfig = new RobotConfig(this.teamSide, false);
		
		return new RobotBoyAttacker(simulator, robotconfig);
//		if(index == 0) {
//			RobotConfig robotconfig = new RobotConfig(this.teamSide, false);
//			
//			return new RobotBoyAttacker(simulator, robotconfig);			
//		}else {
//			RobotConfig robotconfig = new RobotConfig(this.teamSide, true);
//			
//			return new RobotBoyKeeper(simulator, robotconfig);
//		}
	}

}
