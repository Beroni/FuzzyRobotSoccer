package br.unifor.si.rosos.fuzzyleiros.fuzzy;


import net.sourceforge.jFuzzyLogic.*;
import net.sourceforge.jFuzzyLogic.plot.*;
import net.sourceforge.jFuzzyLogic.rule.*;

public class Fuzzy {
	public static void main(String[] args) throws Exception {
		// Load from 'FCL' file
		String fileName = "br/unifor/si/rosos/fuzzyleiros/fcl/mentalidade.fcl";
		FIS fis = FIS.load(fileName,true);

		// Error while loading?
		if( fis == null ) { 
			System.err.println("Can't load file: '" + fileName + "'");
			return;
		}

		// Show 

		// normalizing data in [a,b] = a + ((X-X.min)(b-a) / X.max - X.Min)

		//        JFuzzyChart.get().chart(fis);
		float max = Float.NEGATIVE_INFINITY;
		float min = Float.POSITIVE_INFINITY;
		float normalizado;

		/*	for (double i = -180; i <= 180 ; i+=1) {
			fis.setVariable("ballAngle", i);
			fis.setVariable("yPosition", -1.9223331);
			fis.evaluate();
			float vlm = (float) fis.getVariable("velocity").getValue();
			max = max<vlm?vlm:max;
			min = min>vlm?vlm:min;

			//        Double vrm = fis.getVariable("velocityRM").getValue();
			System.out.println(vlm);
		}

		for (double i = -180; i <= 180 ; i+=1) {
			fis.setVariable("ballAngle", i);
			fis.evaluate();
			float vlm = (float) fis.getVariable("velocity").getValue();
			normalizado = (vlm-min)/(max-min) - 0.5f;
			//System.out.println(normalizado);

		}*/



		/*
		 * fis.setVariable("tempo", 100);
		 *  fis.setVariable("resultado", 2);
		 * fis.setVariable("golsSofridos", 0);
		 */
		
		fis.setVariable("tempo", 100);
		fis.setVariable("resultado", 2);
		 fis.setVariable("golsSofridos", 0);
		fis.evaluate();
		Variable tip = fis.getVariable("perfil");
		System.out.println(Math.round(tip.getValue()));
		JFuzzyChart.get().chart(tip, tip.getDefuzzifier(), true);
		
		for( Rule r : fis.getFunctionBlock("mentalidade").getFuzzyRuleBlock("DG").getRules() ) {
			System.out.println(r);
		}
		for( Rule r : fis.getFunctionBlock("mentalidade").getFuzzyRuleBlock("D").getRules() ) {
			System.out.println(r);
		}
		for( Rule r : fis.getFunctionBlock("mentalidade").getFuzzyRuleBlock("V").getRules() ) {
			System.out.println(r);
		}
		for( Rule r : fis.getFunctionBlock("mentalidade").getFuzzyRuleBlock("VG").getRules() ) {
			System.out.println(r);
		}
	}
}