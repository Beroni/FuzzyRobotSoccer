package br.unifor.si.rosos.fuzzyleiros.robot;

import java.awt.Color;

import br.unifor.si.rosos.GameSimulator;
import br.unifor.si.rosos.RobotBasic;
import br.unifor.si.rosos.TeamSide;
import processing.core.PApplet;
import br.unifor.si.rosos.fuzzyleiros.sensor.*;

public class TesteRobo extends RobotBasic {

	private float velocidade1, velocidade2, anguloBall, distancia, bussola,goalAdversary;
	private TeamSide currentSide;
	
	
	
	public TeamSide getCurrentSide() {
		return currentSide;
	}

	public void setCurrentSide(TeamSide currentSide) {
		this.currentSide = currentSide;
	}

	protected void initializeSensors(GameSimulator game){
		super.initializeSensors(game);
		SensorGoal sensor_goal = new SensorGoal(game, this);
		registerSensor(sensor_goal, "GOAL");

	}

	public TesteRobo(GameSimulator g) {
		super(g);
		
		
		// TODO Auto-generated constructor stub
	}
	

	
	public void setup(){
		/*velocidade = 0.5f;
		setRotation(45);
		setSpeed(0.5f);
		delay(2000);
		stopMotors();*/


	}

	public void loop(){
		anguloBall = getSensor("BALL").readValue(0);
		distancia = getSensor("BALL").readValue(1);
		bussola = getSensor("COMPASS").readValue(0);
		goalAdversary = ((SensorGoal)getSensor("GOAL")).readAdversaryAngle(currentSide);
		
		

		
		
//		rotacao();
//		velocidade();
		
//		setRotation(90);
//		
//		delay(1000);
		
		System.out.println("Adversario : " + goalAdversary);
		

	}

	void velocidade() {
		if(distancia >= .4) {
			velocidade1=distancia-distancia*Math.abs(anguloBall)/45;
			velocidade2=0.5f*anguloBall/90;
		}
		else if((bussola > 170 && bussola < 190)) {
			velocidade1 = 0.5f-0.5f*Math.abs(anguloBall)/45;
			
		}else if((bussola >20 && bussola <90) || (bussola > 270 && bussola <340) ){
			velocidade1=0.5f;
		}else {
			velocidade1 = 0.4f-0.4f*Math.abs(anguloBall)/45;
		}
		setSpeed(velocidade1+(float)Math.random()*0.2f- 0.1f, velocidade2);
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
	
	public void draw(PApplet canvas, float scale){
		super.draw(canvas, scale);
		float x = (float) position.x * scale;
		float y = (float) position.y * scale;
		canvas.textSize(20);
		canvas.fill(255);
		canvas.textAlign(PApplet.CENTER);
		canvas.text("1", x, y-scale * getRadius());
	}
	
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
	}

}
