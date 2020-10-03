package br.unifor.si.rosos.fuzzyleiros.robot;

import br.unifor.si.rosos.GameSimulator;
import br.unifor.si.rosos.RobotBasic;
import br.unifor.si.rosos.TeamSide;
import br.unifor.si.rosos.fuzzyleiros.fuzzy.RegrasFuzzy;
import br.unifor.si.rosos.fuzzyleiros.sensor.SensorGoal;
import processing.core.PApplet;

public class TesteRobo2 extends RobotBasic {

	private float velocidade1, velocidade2, anguloBall, distancia, bussola;
	private RegrasFuzzy regras;

	public TesteRobo2(GameSimulator g) {
		super(g);
		// TODO Auto-generated constructor stub
	}
	
	protected void initializeSensors(GameSimulator game){
		super.initializeSensors(game);
		SensorGoal sensor_goal = new SensorGoal(game, this);
		registerSensor(sensor_goal, "GOAL");

	}

	
	public void setup(){
		/*velocidade = 0.5f;
		setRotation(45);
		setSpeed(0.5f);
		delay(2000);
		stopMotors();*/
		regras = new RegrasFuzzy();
		
	}

	public void loop(){
		anguloBall = getSensor("BALL").readValue(0);
		distancia = getSensor("BALL").readValue(1);
		bussola = getSensor("COMPASS").readValue(0);
		//rotacao();
		velocidade();
		

	}

	void velocidade() {
		velocidade2 = 0f;
		velocidade1 = 0f;
		
		velocidade1 = regras.getVelocidadeX(anguloBall);
//		if(Math.abs(anguloBall) <30) {
//			velocidade1 = 0.5f;
//		}else {
//			velocidade1 = -0.5f;
//		}
		
		if(anguloBall > 10 && anguloBall < 110 )
			velocidade2 = 0.5f;
		if(anguloBall < -10 && anguloBall > -110)
			velocidade2 = -0.5f;
		
		if(anguloBall > 155 )
			velocidade2 = -0.5f;
		if(anguloBall < -155 )
			velocidade2 = 0.5f;
		
		
		setSpeed(velocidade1,velocidade2);
	}
	
	void rotacao() {
		float angulo = bussola + anguloBall;
		float fator =0;
		angulo= angulo<0?360-angulo:angulo;
		if(angulo < 170) {
				fator = -20;
		}
		if(angulo > 190) {
			fator = 20;
		}
		
		
		//System.out.println("angulo"+angulo+";bussola="+bussola+";bola="+anguloBall+"fator"+fator);
		setRotation(anguloBall+fator);
		//delay(1000);
	}

	/*
		If you want to code the thread method yourself, instead of
		using the already made `setup` and `loop` methods, you can
		override the method `run`. Uncomment those lines to use.
	 */
	// public void run(){

	// }

	/*
		You can use this method to decorate your robot.
		use Processing methods from the `canvas` object.

		The center of the robot is at [0,0], and the limits
		are 100px x 100px.
	 */
	public void decorateRobot(PApplet canvas){

	}

	/*
		Called whenever a robot is:
			PAUSED
			REMOVED from field
			PLACED in field
			STARTED
	 */
	public void onStateChanged(String state){
		//System.out.println(state);
	}

}
