package com.asd.reversi.service;

import com.asd.reversi.reversi.command.Command;
import com.asd.reversi.reversi.command.HumanMove;
import com.asd.reversi.reversi.factory.StrategyFactory;
import com.asd.reversi.reversi.factory.StrategyImplFactory;
import com.asd.reversi.reversi.Reversi;
import com.asd.reversi.reversi.model.MoveDetails;
import com.asd.reversi.reversi.model.ReversiBoard;

import com.asd.reversi.reversi.strategy.Strategy;
import com.asd.reversi.reversi.strategy.StratgyContext;
import com.asd.reversi.reversi.player.Player;
import com.asd.reversi.reversi.util.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayRoomService {

	@Autowired
    private Reversi reversi;



    public ReversiBoard registerPlayer(String username) {
        return reversi.registerPlayer(username);
    }

    public void startGame() { 
        reversi.startGame();
    }

    public ReversiBoard move(MoveDetails details) throws Exception {
        return reversi.move(details);
    }

    public Player registerPlayers(String username) {
        return reversi.registerPlayers(username);
    }

    public MoveDetails generateMove() {
       return reversi.generateComputerMove();
    }

    public Point movePoint(Point details) throws Exception {
        return reversi.movePoint(details);
    }
}
