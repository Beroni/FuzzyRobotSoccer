package br.unifor.si.rosos.breno_pedro_team;

import br.unifor.si.rosos.GameSimulator;
import br.unifor.si.rosos.Robot;
import br.unifor.si.rosos.Team;
import br.unifor.si.rosos.TeamSide;

import java.util.ArrayList;


public class MonkeyTeam implements Team {
    ArrayList<PlayerBot> players;

    public String getTeamName() {
        return "Breno e Pedro";
    }

    private void assertPlayers() {
        if (players == null) {
            players = new ArrayList<>();
        }
    }

    public void setTeamSide(TeamSide side) {
        assertPlayers();

        for (PlayerBot player : players) {
            player.setIsSideSet(false);
        }
    }

    public Robot buildRobot(GameSimulator s, int index) {
        assertPlayers();

        PlayerBot robot = new PlayerBot(s);
        players.add(robot);
        return robot;
    }
}