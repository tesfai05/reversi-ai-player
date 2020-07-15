package com.asd.reversi.reversi.command;

import com.asd.reversi.reversi.model.MoveDetails;
import com.asd.reversi.reversi.strategy.StratgyContext;

public class HumanMove implements Command{
   private StratgyContext context;
   private  int[][] board;
   private  MoveDetails details;
    public HumanMove(StratgyContext context,int[][] board,MoveDetails details){
        this.context=context;
        this.board=board;
        this.details=details;
    }
    @Override
    public void execute() {
        context.execute(board,details);
    }
}
