package br.unifor.si.rosos.fuzzyloucos;


import java.util.Vector;

import br.unifor.si.rosos.*;
import processing.core.*;

class SensorAll extends Sensor{

	Vector<Simulatable> itens;

	SensorAll(GameSimulator g, Robot r){
		super(g, r);
	}

	float lastRead = 0;
	public float[] readValues(){
		// Avoid multiple readings within 100ms
		if(game.getTime() >= lastRead + 0.1f)
			doReading();

		return null;
	}
	public Vector<Simulatable> readValues(int x){
		// Avoid multiple readings within 100ms
		
		doReading();

		return itens;
	}

	private void doReading(){
		
		itens = getGameSimulator().simulatables;

	}

}