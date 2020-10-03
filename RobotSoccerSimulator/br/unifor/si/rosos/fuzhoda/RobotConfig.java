package br.unifor.si.rosos.fuzhoda;

import br.unifor.si.rosos.TeamSide;
import net.sourceforge.jFuzzyLogic.FIS;

public class RobotConfig {
	private TeamSide teamSide;
	private FIS fis;
	
	public RobotConfig(TeamSide teamSide, boolean keeper) {
		this.teamSide = teamSide;
		String fileName = null;
		
		if(!keeper) {
			fileName = "br/unifor/si/rosos/fuzhoda/daniel_arthur_fuzzy_attack.fcl";			
		}else {
			fileName = "br/unifor/si/rosos/fuzhoda/daniel_arthur_fuzzy_defender.fcl";
		}
		
		fis = FIS.load(fileName);
		
		if(fis  == null) {
			try {
				throw new Exception(".fcl não encontrado");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public FIS getFis() {
		return fis;
	}

	public TeamSide getTeamSide() {
		return teamSide;
	}	
}
