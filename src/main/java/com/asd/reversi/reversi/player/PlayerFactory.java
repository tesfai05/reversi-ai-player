package com.asd.reversi.reversi.player;

import java.util.ArrayList;
import java.util.List;

public class PlayerFactory {
    private List<Player> players = new ArrayList<>();
    public Player createPlayer(String player, String name, int flag){
        if(player == null){
            return null;
        }
        if(player.equalsIgnoreCase("human")){
            Player p = new HumanPlayer(name, flag);
            players.add(p);
            return p;
        }
        if(player.equalsIgnoreCase("computer")){
            Player p = new ComputerPlayer(name, flag);
            players.add(p);
            return p;
        }
        if(player.equalsIgnoreCase("remote")){
            Player p = new RemotePlayer(name, flag);
            players.add(p);
            return p;
        }
        return null;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
