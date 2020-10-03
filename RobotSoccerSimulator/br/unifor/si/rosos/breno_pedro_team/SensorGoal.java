package br.unifor.si.rosos.breno_pedro_team;

import br.unifor.si.rosos.GameSimulator;
import br.unifor.si.rosos.Goal;
import br.unifor.si.rosos.Robot;
import br.unifor.si.rosos.Sensor;
import processing.core.PVector;

public class SensorGoal extends Sensor {

    float[] values = new float[4];
    float sensorLimit = 2.5f;
    float lastRead = 0;

    public SensorGoal(GameSimulator g, Robot r) {
        super(g, r);
    }

    public float[] readValues() {
        // Avoid multiple readings within 100ms
        if (game.getTime() >= lastRead + 0.1f)
            doReading();

        return values;
    }

    private void doReading() {
        Robot thisRobot = getRobot();
        Goal leftGoal = getGameSimulator().goalLeft;
        Goal rightGoal = getGameSimulator().goalRight;

        // Find relative distance from Ball to Robot
        PVector distLeft = PVector.sub(leftGoal.getPosition(), thisRobot.getPosition());
        distLeft.rotate(-thisRobot.getOrientation());

        // index 0 contains the Angle of the ball
        values[0] = (float) Math.toDegrees(distLeft.heading());
        // index 1 contains the distance to the ball
        values[1] = (float) Math.min(distLeft.mag(), sensorLimit);

        // Find relative distance from Ball to Robot
        PVector distRight = PVector.sub(rightGoal.getPosition(), thisRobot.getPosition());
        distRight.rotate(-thisRobot.getOrientation());

        // index 0 contains the Angle of the ball
        values[2] = (float) Math.toDegrees(distRight.heading());
        // index 1 contains the distance to the ball
        values[3] = (float) Math.min(distRight.mag(), sensorLimit);
    }
}