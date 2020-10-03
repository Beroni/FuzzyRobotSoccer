package br.unifor.si.rosos.fuzzyloucos;
import processing.core.*;

import net.sourceforge.jFuzzyLogic.*;
import net.sourceforge.jFuzzyLogic.plot.*;

import java.lang.Math;
import java.util.*;
import br.unifor.si.rosos.*;

public class FuzzyBall implements Team{
	private static class UsSensors {
		public static final int FRONT = 0;
		public static final int LEFT = 1;
		public static final int BACK = 2;
		public static final int RIGHT = 3;
	}

	public String getTeamName(){
		return "Lokuzilados";
	}
	
	TeamSide s;
	public void setTeamSide(TeamSide side){
		s = side;
	}
	
	public Robot buildRobot(GameSimulator s, int index){
		return new Zidane(s);
	}
	
	class Zidane extends RobotBasic{
		Zidane(GameSimulator s){
			super(s);			
		}
		
		SensorAll all;
		float goalDir;
		
		
		Vector<Robot> adversarios;
		Vector<Robot> parceiros;
		PVector outroGol;
		PVector meuGol;
		Ball ball;
		float posX;
		float posY;
		
		float speedX;
		float speedY;
		float realSpeedX;
		float realSpeedY;
		
		int loops = 0;
		
		LinkedList<Float> distsBola = new LinkedList<Float>();
		
		
		

		public void setup(){
			System.out.println("Running!");
			
			all = (SensorAll)getSensor("ALL");
			
			
			goalDir = 0f;
			// Find Goal Direction
			if(s == TeamSide.RIGHT)
				goalDir = 180f;
		}
		
		
		public float normalize(float x) {
		    return ((x - -1f) 
		            / (1f - -1f)*(0.5f - -0.5f))-0.5f;
		            
		}
		
		public void atualizaInfos(Vector<Simulatable> itens) {
			
			Vector<Robot> adversarios = new Vector<Robot>();
			Vector<Robot> parceiros = new Vector<Robot>();
			for(Simulatable sim:itens) {
				
				if(sim instanceof Ball) {
					ball = (Ball)sim;
				}else if(sim instanceof Robot) {
					
					if(sim instanceof Zidane) {
						
						if(!sim.equals(this))parceiros.add((Robot)sim);
					}else {
						adversarios.add((Robot)sim);
					}
				}else if(sim instanceof Goal){
					if(s == TeamSide.LEFT) {
						if(sim.position.x<1f) {
							
							meuGol= new PVector();
							meuGol.x=sim.position.x;
							meuGol.y = sim.position.y-0.3f;
						}else {
							outroGol= new PVector();
							outroGol.x=sim.position.x;
							outroGol.y = sim.position.y-0.3f;
						}
						
					}else {
						if(sim.position.x>2f) {
							
							meuGol= new PVector();
							meuGol.x=sim.position.x;
							meuGol.y = sim.position.y-0.3f;
						}else {
							outroGol= new PVector();
							outroGol.x=sim.position.x;
							outroGol.y = sim.position.y-0.3f;
						}
					}
				}
			}
			posX = this.getRealPosition().x;
			posY = this.getRealPosition().y;
			this.parceiros=parceiros;
			this.adversarios=adversarios;
			
			realSpeedX = this.getRealSpeed().x;
			realSpeedY = this.getRealSpeed().y;
			
		}
		
		public void imprimePosicoes() {
			
			
			System.out.println("Bola - X:"+ ball.position.x+" Y:"+ ball.position.y);
			System.out.println("MeuGol - X:"+ meuGol.x+" Y:"+ meuGol.y);
			System.out.println("OutroGol - X:"+ outroGol.x+" Y:"+ outroGol.y);
			int i=1;
			for(Robot adv: adversarios) {
				System.out.println("Adversario "+i+" - X:"+ adv.position.x+" Y:"+ adv.position.y);
				i++;
			}
			i =1;
			for(Robot par: parceiros) {
				System.out.println("Parceiro "+i+" - X:"+ par.position.x+" Y:"+ par.position.y);
				i++;
			}
			
			System.out.println("EU "+" - X:"+ posX+" Y:"+ posY);
			System.out.println("Speed"+" - X:"+ speedX+" Y:"+ speedY);
			
		}
		
		public void logicaFuzzy() {
			
			if (this.getOrientation()!=0) {
				this.setState(this.position, 0f, false);
			}
			
			String filename = "br/unifor/si/rosos/fuzzyloucos/logicaGoleiroEsquerda.fcl";
			FIS fis = FIS.load(filename, true);

			if (fis == null) {
				System.err.println("Can't load file: '" + filename + "'");
				System.exit(1);
			}

			// Get default function block
			FunctionBlock fb = fis.getFunctionBlock(null);

			// Set inputs
			float ballX = ball.position.x;
			float varBallX = ball.position.x - posX;
			float ballY = ball.position.y - posY;
			float myGolSide = meuGol.x;
			float varAdvBallX = 0;
			
			try {
			 varAdvBallX = ball.position.x - adversarios.get(0).position.x;
			} catch(Exception e)
			{
				if(myGolSide < 1.02)
					varAdvBallX = 2;
				else
					varAdvBallX = -2;
			}
			

			
			fb.setVariable("varAdvBallX", varAdvBallX);
			fb.setVariable("myGolSide", myGolSide);
			fb.setVariable("posX", posX);
			fb.setVariable("varBallX", varBallX);
			fb.setVariable("posY", posY);
			fb.setVariable("ballX", ballX);
			fb.setVariable("ballY", ballY);

			// Evaluate
			fb.evaluate();
			

			// Show output variable's chart
			speedX = (float)fb.getVariable("outX").defuzzify();
			speedY = (float)fb.getVariable("outY").defuzzify();
			
			

		}

		
//CODIGO COMENTADO E REFERENTE A UMA LOGICA PARA IMPLEMENTAR O "DOMINIO DA BOLA", MAS NAO TERMINEI

//		public float calculaDists(PVector pos1,PVector pos2) {
//			PVector dist = PVector.sub(pos1, pos2);
//			dist.rotate(-this.getOrientation());
//			float distRet = dist.heading();
//			
//			
//			double distX = Math.abs(pos1.x-pos2.x);
//			double distY = Math.abs(pos1.y-pos2.y);
//			distRet = (float)Math.sqrt(distX*distX+distY*distY);
//			System.out.println("dist:"+distRet);
//			
//			return Math.abs(distRet);
//		}
//		
//		public void atualizaDistsBola(float dist) {
//			
//			distsBola.add(dist);
//			if(distsBola.size()>10)distsBola.removeFirst();
//			
//		}
//		
//		float getMean() {
//	        float sum = 0.0f;
//	        for(float a : distsBola)
//	            sum += a;
//	        return sum/distsBola.size();
//	    }
//
//	    float getVariance() {
//	        float mean = getMean();
//	        float temp = 0;
//	        for(float a :distsBola)
//	            temp += (a-mean)*(a-mean);
//	        return temp/(distsBola.size()-1);
//	    }
//
//	    float getStdDev() {
//	        return (float)Math.sqrt(getVariance());
//	    }

		public void loop(){
			loops++;
			
			Vector<Simulatable> itens = all.readValues(0);
			atualizaInfos(itens);
			imprimePosicoes();
			logicaFuzzy();
			
			setSpeed(speedX,speedY);
			
						
		}
	}
	
}
