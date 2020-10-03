package br.unifor.si.rosos.fuzzyleiros.robot;

import br.unifor.si.rosos.GameController;
import br.unifor.si.rosos.GameSimulator;
import br.unifor.si.rosos.RobotBasic;
import br.unifor.si.rosos.TeamSide;
import br.unifor.si.rosos.fuzzyleiros.fuzzy.RegrasFuzzy;
import br.unifor.si.rosos.fuzzyleiros.sensor.SensorGame;
import br.unifor.si.rosos.fuzzyleiros.sensor.SensorGoal;
import net.sourceforge.jFuzzyLogic.FIS;
import processing.core.PApplet;

public class RoboFuzzy extends RobotBasic{

	private Perfil estilo= Perfil.M;
	private int jogador;
	private int tatica, nova;

	private float halfFieldHeight, halfFieldWidth,  zona1;
	private final float  halfPenAreaHeight = 0.45f; //0.30f;
	private final float  halfPenAreaWidth = 0.15f;
	private float xVelocidade, yVelocidade, anguloBall, distancia;
	private float adversaryAngle, adversaryXdist;
	private float teamYdis,teamXdis, bussola;
	private float currentTime;
	private int teamScore, adversaryScore;
	private TeamSide currentSide;
	private RegrasFuzzy regras;

	public RoboFuzzy(GameSimulator g) {
		super(g);
		halfFieldHeight = g.getFieldHeight()/2;
		halfFieldWidth = g.getFieldWidth()/3;
		zona1 =  0.5f;
	}

	public RoboFuzzy(GameSimulator g, int x) {
		this(g);
		jogador =x;
	}



	protected void initializeSensors(GameSimulator game){
		super.initializeSensors(game);
		SensorGoal sensor_goal = new SensorGoal(game, this);
		registerSensor(sensor_goal, "GOAL");
		SensorGame sensor_game = new SensorGame(game,this);
		registerSensor(sensor_game, "GAME");

	}

	public void setup(){
		regras = new RegrasFuzzy();
		tatica=nova=0;
	}

	public void loop(){	

		xVelocidade=yVelocidade=0;
		anguloBall = getSensor("BALL").readValue(0);
		distancia = getSensor("BALL").readValue(1);
		bussola = getSensor("COMPASS").readValue(0);
		adversaryAngle = ((SensorGoal)getSensor("GOAL")).readAdversaryAngle(currentSide);
		adversaryXdist = ((SensorGoal)getSensor("GOAL")).readAdversaryXdis(currentSide);
		teamXdis = ((SensorGoal)getSensor("GOAL")).readTeamXdis(currentSide);
		teamYdis = ((SensorGoal)getSensor("GOAL")).readTeamYdis(currentSide);
		teamScore = ((SensorGame)getSensor("GAME")).getTeamScore(currentSide);
		adversaryScore = ((SensorGame)getSensor("GAME")).getAdversaryScore(currentSide);
		currentTime = ((SensorGame)getSensor("GAME")).getTime();
		principal();
		setSpeed(xVelocidade, yVelocidade);
		//System.out.println(anguloBall +" - " +   teamYdis +" - " + (teamYdis/halfPenAreaHeight) + " - " + yVelocidade);
		

	}
	public void principal() {
		mentalidade();
		acao();
	}

	public void mentalidade() {

		nova = regras.mentalidade(currentTime, teamScore-adversaryScore, adversaryScore);
		//nova = regras.mentalidade(87, 10, 0);
		//System.out.println(tatica+" >> " + nova + "- Placar:" + teamScore + " X " + adversaryScore + "-- " +currentTime); 
		if(nova != tatica) {
			if(jogador == 0) {
				System.out.println(tatica+" >> " + nova + "- Placar:" + teamScore + " X " + adversaryScore + "-- " +currentTime); 
			}
			
			tatica = nova;
			if( jogador == 0) {
				switch (tatica) {
				case 0: estilo = Perfil.GK; break;
				case 1: estilo = Perfil.D; break;
				case 2: estilo = Perfil.M; break;
				case 3: estilo = Perfil.ST; break;
				}
				
			}else {
				switch (tatica) {
				case 0: estilo = Perfil.GK; break;
				case 1: estilo = Perfil.GK; break;
				case 2: estilo = Perfil.D; break;
				case 3: estilo = Perfil.ST; break;
				}

			}

		}
	}

	public void acao() {
		switch (estilo) {
		case GK: runDefesa(); break;
		case  D: defensor();  break;
		case  M: meio();      break;
		case ST: runBall();   break;
		}

		limites();
	}


	protected void defensor() {
		//bola atrás do jogador
		if(Math.abs(anguloBall) < 90 && distancia < zona1) 
			runBall();
		else
			runDefesa();

	}

	protected void meio() {
		//bola atrás do jogador
		if(Math.abs(anguloBall) < 30 ) 
			runBall();
		else
			runDefesa();

	}

	protected void limites() {

		if(adversaryXdist < 0.15)
			xVelocidade = -0.5f;
		else if(teamXdis < 0.05)
			xVelocidade = 0.5f;

		if(teamYdis > halfFieldHeight - 0.05)
			yVelocidade= -0.5f;
		else if(teamYdis < -halfFieldHeight + 0.05)
			yVelocidade= 0.5f;

		if(distancia > 0.7f || teamXdis < halfFieldWidth || Math.abs(teamYdis -halfFieldHeight ) < 0.2) {
			if(getCurrentSide().equals(TeamSide.LEFT)) {

				setRotation(bussola>180?360-bussola:-bussola);	
			}

			else
				setRotation(180-bussola);
		}

	}

	protected void runBall() {
		xVelocidade = 0f;
		yVelocidade = 0f;
		xVelocidade = regras.getVelocidadeX(anguloBall);
		yVelocidade = regras.getVelocidadeY(anguloBall, normalizarFieldHeight(teamYdis));

		adversaryAngle+= (float)Math.random()*10-5;
		setRotation(adversaryAngle);




	}

	protected void runDefesa() {
		xVelocidade = 0f;
		yVelocidade = 0f;
		xVelocidade = regras.getVelocidadeXGK(teamXdis);
		yVelocidade = regras.getVelocidadeY(anguloBall, normalizarPenAreaHeight(teamYdis));






	}

	private float normalizarFieldHeight(float yPosition) {
		return yPosition/halfFieldHeight;
	}

	private float normalizarPenAreaHeight(float yPosition) {
		return yPosition/halfPenAreaHeight;
	}

	public void draw(PApplet canvas, float scale){
		super.draw(canvas, scale);
		float x = (float) position.x * scale;
		float y = (float) position.y * scale;
		canvas.textSize(20);
		canvas.fill(255);
		canvas.textAlign(PApplet.CENTER);
		canvas.text(getNome(), x, y-scale * getRadius());
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

	public String getNome() {
		

		return jogador + "-"+getEstilo().name();
	}


	public TeamSide getCurrentSide() {
		return currentSide;
	}



	public void setCurrentSide(TeamSide currentSide) {
		this.currentSide = currentSide;
	}



	public Perfil getEstilo() {
		return estilo;
	}



	public void setEstilo(Perfil estilo) {
		this.estilo = estilo;
	}









}
