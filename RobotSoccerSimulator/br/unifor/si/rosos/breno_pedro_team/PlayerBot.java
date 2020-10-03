package br.unifor.si.rosos.breno_pedro_team;

import br.unifor.si.rosos.GameSimulator;
import br.unifor.si.rosos.RobotBasic;
import br.unifor.si.rosos.Sensor;
import br.unifor.si.rosos.TeamSide;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Variable;
import processing.core.PApplet;

import java.io.File;

//      c_a
// & -----------          
//   \ (teta)   | 
//    \         |
//     \        |
//      \       |
//       \      |
//      h \     |  c_b
//         \    |
//          \   |
//           \  |
//            \ |
//              *

// c_a = cos(teta) * h - bola.width -> "fuzzifica" ~ speedx
// c_b = sen(teta) * h -> "fuzzifica" ~ speedy

// teta -> graus
// teta_(c_a)x(h)

public class PlayerBot extends RobotBasic {
    private Sensor ballSensor, compassSensor, goalSensor;
    private Sensor[] distanceSensors; // L, R, F, B

    private float botSpeed, maxSpeed;

    private FIS fis;
    private FunctionBlock functionBlock;

    private TeamSide teamSide;
    private boolean isSideSet = false;
    private GameSimulator game;

    public PlayerBot(GameSimulator g) {
        super(g);
        game =g;
    }

    /**
     * Bot initialization code, runs only once.
     * <p>
     * Sensor and variable initialization.
     */
    public void setup() {
    	maxSpeed=botSpeed = 0.5f;

        ballSensor = getSensor("BALL");
        compassSensor = getSensor("COMPASS");
        goalSensor = new SensorGoal(game, this);
        registerSensor(goalSensor, "GOAL");
        distanceSensors = new Sensor[]{
                getSensor("ULTRASONIC_LEFT"),
                getSensor("ULTRASONIC_RIGHT"),
                getSensor("ULTRASONIC_FRONT"),
                getSensor("ULTRASONIC_BACK")
        };

        loadFcl();
    }

    private void loadFcl() {
        // Load from 'FCL' file
        String fclFileName = 
                "br" + File.separator +
                "unifor" + File.separator +
                "si" + File.separator +
                "rosos" + File.separator +
                "breno_pedro_team" + File.separator + "PlayerBot.fcl";
        fis = FIS.load(fclFileName, true);

        // Error while loading?
        if (fis == null) {
            System.err.println("Can't load file: '" + fclFileName + "'");
            return;
        }

        functionBlock = fis.getFunctionBlock("PlayerBot");

        // Show
//        JFuzzyChart.get().chart(functionBlock);

        // Print ruleSet
        System.out.println(fis);
    }

    public void loop() {
        if (!isSideSet) {
            float[] goalReadings = goalSensor.readValues();
            if (goalReadings[1] < goalReadings[3]) {
                teamSide = TeamSide.RIGHT;
            } else {
                teamSide = TeamSide.LEFT;
            }
            isSideSet = true;
        }

        logReadings();

        updateFuzzyInputs();

        Variable speed = functionBlock.getVariable("speed");
        Variable playerRotation = functionBlock.getVariable("player_rotation");

//        plotVariableCharts(speed, playerRotation);

        // Speed control
        float defSpeed = maxSpeed * (float) speed.defuzzify();

        setSpeed(defSpeed);
        setRotation((float) playerRotation.defuzzify() * 2);

        delay(100);
    }

    private void plotVariableCharts(Variable... variables) {
        for (Variable var : variables) {
            JFuzzyChart.get().chart(var, var.getDefuzzifier(), true);
        }
    }

    private void updateFuzzyInputs() {
        fis.setVariable("player_side", teamSide == TeamSide.LEFT ? 0f : 1f);
//        fis.setVariable("player_compass", compassSensor.readValue(0));

        float ballAngle = ballSensor.readValue(0);
        float ballDistance = ballSensor.readValue(1);

        fis.setVariable("ball_angle", ballAngle);
        fis.setVariable("ball_distance", ballDistance);

        float leftGoalAngle = goalSensor.readValue(0);
        float leftGoalDistance = goalSensor.readValue(1);
        float rightGoalAngle = goalSensor.readValue(2);
        float rightGoalDistance = goalSensor.readValue(3);

        fis.setVariable("goal_angle", teamSide == TeamSide.LEFT ? rightGoalAngle : leftGoalAngle);
        fis.setVariable("goal_distance", teamSide == TeamSide.LEFT ? rightGoalDistance : leftGoalDistance);

//        float ballDistanceX = (float) (Math.cos(Math.toRadians(ballAngle)) * ballDistance) - game.ball.getRadius() / 2;
//        float ballDistanceY = (float) (Math.sin(Math.toRadians(ballAngle)) * ballDistance);
//
//        fis.setVariable("ball_distance_x", ballDistanceX);
//        fis.setVariable("ball_distance_y", ballDistanceY);

//        System.out.printf("Distância da bola em X: %.2f, Y: %.2f\n", ballDistanceX, ballDistanceY);
//
//        String[] obstacle_inputs = new String[]{"left", "right", "front", "back"};
//        for (int i = 0; i < obstacle_inputs.length; i++) {
//            float sensorReading = distanceSensors[i].readValue(0);
//            fis.setVariable(obstacle_inputs[i] + "_obstacle_distance",
//                    sensorReading <= 2.5 ? sensorReading : 2.5);
//        }

        // Evaluate
        fis.evaluate();
    }

    private void logReadings() {
        float[] ballSensorReadings = ballSensor.readValues();
        System.out.println("Ângulo da bola: " + ballSensorReadings[0]);
        System.out.println("Distância da bola: " + ballSensorReadings[1]);

        float[] goalSensorReadings = goalSensor.readValues();
        System.out.println("Ângulo do gol da esquerda: " + goalSensorReadings[0]);
        System.out.println("Distância do gol da esquerda: " + goalSensorReadings[1]);
        System.out.println("Ângulo do gol da direita: " + goalSensorReadings[2]);
        System.out.println("Distância do gol da direita: " + goalSensorReadings[3]);

//        float compassReading = compassSensor.readValue(0);
//        System.out.println("Ângulo do jogador: " + compassReading);
//
//        List<Float> distanceReadings = Arrays.stream(distanceSensors).parallel()
//                .map(sensor -> sensor.readValue(0))
//                .collect(Collectors.toList());
//        System.out.println("Sensores ultrasônicos:");
//        System.out.println("Esquerda: " + distanceReadings.get(0));
//        System.out.println("Direita: " + distanceReadings.get(1));
//        System.out.println("Frente: " + distanceReadings.get(2));
//        System.out.println("Trás: " + distanceReadings.get(3));
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
    public void decorateRobot(PApplet canvas) {

    }

    /*
        Called whenever a robot is:
            PAUSED
            REMOVED from field
            PLACED in field
            STARTED
    */
    public void onStateChanged(String state) {
    }

    public boolean getIsSideSet() {
        return isSideSet;
    }

    public void setIsSideSet(boolean sideSet) {
        this.isSideSet = sideSet;
    }
}
