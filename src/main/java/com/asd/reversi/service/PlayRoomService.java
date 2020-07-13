package com.asd.reversi.service;

import com.asd.reversi.command.Command;
import com.asd.reversi.command.HumanMove;
import com.asd.reversi.factory.StrategyFactory;
import com.asd.reversi.factory.StrategyImplFactory;
import com.asd.reversi.reversi.Reversi;
import com.asd.reversi.reversi.model.MoveDetails;
import com.asd.reversi.reversi.model.ReversiBoard;
import com.asd.reversi.reversi.strategy.Strategy;
import com.asd.reversi.reversi.strategy.StrategyImpl;
import com.asd.reversi.reversi.strategy.StratgyContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void playHumanMove(int[][] board,MoveDetails details){
        StrategyFactory factory= new StrategyImplFactory();

        Strategy strategy=factory.createStrategy() ;
     StratgyContext stratgyContext=new StratgyContext(strategy);
       Command command=new HumanMove(stratgyContext,board,details);
        reversi.submit(command);

    }

}
