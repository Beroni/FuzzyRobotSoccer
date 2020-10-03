package br.unifor.si.rosos.fuzzyleiros.fuzzy;

import br.unifor.si.rosos.fuzzyleiros.robot.Perfil;
import net.sourceforge.jFuzzyLogic.FIS;

public class RegrasFuzzy {
	
	final private FIS xVelocidade = FIS.load("br/unifor/si/rosos/fuzzyleiros/fcl/xVelocidade.fcl",true);
	final private FIS yVelocidade = FIS.load("br/unifor/si/rosos/fuzzyleiros/fcl/yVelocidade.fcl",true);
	final private FIS xVelocidadeGK = FIS.load("br/unifor/si/rosos/fuzzyleiros/fcl/xVelocidadeGK.fcl",true);
	final private FIS mentalidade = FIS.load("br/unifor/si/rosos/fuzzyleiros/fcl/mentalidade.fcl",true);
	
	float yVelocidadeMax = 0.1f;
	float yVelocidadeMin = -0.1f;

	
	public RegrasFuzzy() {
		
	}
	
	// normalizing data in [a,b] = a + ((X-X.min)(b-a) / X.max - X.Min)

	
	public float getVelocidadeX(float anguloBola) {
		
		xVelocidade.setVariable("ballAngle", anguloBola);
		xVelocidade.evaluate();
		return  (float)xVelocidade.getVariable("velocity").getValue();
		
	}
	
	
	public float getVelocidadeY(float anguloBola, float y) {
		
		yVelocidade.setVariable("ballAngle", anguloBola);
		yVelocidade.setVariable("yPosition", y);
		yVelocidade.evaluate();
		float vlm = (float) yVelocidade.getVariable("velocity").getValue();
		yVelocidadeMax = yVelocidadeMax<vlm?vlm:yVelocidadeMax;
		yVelocidadeMin = yVelocidadeMin>vlm?vlm:yVelocidadeMin;
		//return (float)((vlm-yVelocidadeMin)/(yVelocidadeMax-yVelocidadeMin) -0.5f);
		return (float) yVelocidade.getVariable("velocity").getValue();
	}
	
	public float getVelocidadeXGK(float distBola) {
		
		xVelocidadeGK.setVariable("distance", distBola);
		xVelocidadeGK.evaluate();
		return  (float)xVelocidadeGK.getVariable("velocity").getValue();
		
	}
	
	public int mentalidade(float tempo, float placar, float gols) {
		
		mentalidade.setVariable("tempo", tempo);
		mentalidade.setVariable("resultado", placar);
		mentalidade.setVariable("golsSofridos", gols);
		mentalidade.evaluate();
		return  (int) Math.round(mentalidade.getVariable("perfil").getValue());
		
	}
	
	


}
