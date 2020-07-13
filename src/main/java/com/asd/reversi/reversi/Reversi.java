package com.asd.reversi.reversi;

import com.asd.reversi.command.Command;
import com.asd.reversi.reversi.model.MoveDetails;
import com.asd.reversi.reversi.model.ReversiBoard;
import com.asd.reversi.reversi.state.IState;
import com.asd.reversi.reversi.state.StateContex;
import com.asd.reversi.reversi.strategy.StrategyImpl;
import com.asd.reversi.reversi.strategy.StratgyContext;
import com.asd.reversi.reversi.util.ArrayUtil;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class Reversi {
    private static ReversiBoard reversiBoard = ReversiBoard.getInstance();

    public ReversiBoard registerPlayer(String username) {
        if (reversiBoard.getPlayerFactory().getPlayers().size() == 0) {
            reversiBoard.getPlayerFactory().createPlayer("human",username,1);
        } else if (reversiBoard.getPlayerFactory().getPlayers().size() == 1) {
            reversiBoard.getPlayerFactory().createPlayer("human",username,-1);
        }
        return reversiBoard;
    }

    public void startGame() {
        if (reversiBoard.isFinished()) {
            reversiBoard.reset();
        }
        reversiBoard.setNext(calcNextMoves(reversiBoard.getTurn()));
    }

    public ReversiBoard move(MoveDetails details) throws Exception {
        if (!isItPlayersTurn(details)) {
            throw new Exception("It's not your turn");
        }
        if (!isMoveValid(details)) {
            throw new Exception("It's not a valid movement");
        }
        doMove(reversiBoard.getBoard(), details);
        if (!isGameFinished()) {
            setTurn(details);
            reversiBoard.setNext(calcNextMoves(reversiBoard.getTurn()));
        } else {
            reversiBoard.setFinished(true);
            System.out.println("game is over"); // for game  is over state
        }
        return reversiBoard;
    }

    public boolean doMove(int[][] board, MoveDetails details) {
        StratgyContext context = new StratgyContext(new StrategyImpl()) ;
        return context.execute(board, details);
    }


    private int[][] calcNextMoves(int player) {
        int[][] next = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int[][] board = ArrayUtil.copy(reversiBoard.getBoard());
                if (board[i][j] == 0 && doMove(board, new MoveDetails(player, i, j))) {
                    next[i][j] = 2*player;
                }
            }
        }
        return next;
    }

    private boolean isMoveValid(MoveDetails details) {
        int[][] nextMoves = calcNextMoves(details.getPlayer());
        return nextMoves[details.getX()][details.getY()] == 2*details.getPlayer();
    }

    private boolean isItPlayersTurn(MoveDetails details) {
        return reversiBoard.getTurn() == details.getPlayer();
    }

    private boolean canOtherPlayerMove(int player) {
        int otherPlayer = -player;
        int[][] nextMoves = calcNextMoves(otherPlayer);
        return Arrays.stream(nextMoves).flatMapToInt(Arrays::stream).anyMatch(item -> item == (2*otherPlayer));
    }

    private void setTurn(MoveDetails details) { // change player turn  to the oposit by puting -ve
        if (canOtherPlayerMove(details.getPlayer())) {
            reversiBoard.setTurn(-reversiBoard.getTurn());
        }
    }

    private boolean isGameFinished() {
        checkState();
        return Arrays.stream(reversiBoard.getBoard())
                .flatMapToInt(Arrays::stream)
                .noneMatch(item -> item == 0);
    }

    public IState checkState() {
        //	private final IState state =null;
        long playerNegative=	Arrays.stream(reversiBoard.getBoard())
                .flatMapToInt(Arrays::stream)
                .filter(x-> x==-1).count();

        long playerPositive=Arrays.stream(reversiBoard.getBoard())
                .flatMapToInt(Arrays::stream)
                .filter(x-> x==1).count();

        // return state depending on the input (alter its behaviour when its internal state chng)
        StateContex cont = new StateContex(playerPositive, playerNegative);
        reversiBoard.setState(cont.getState());
        return cont.getState(); // new StateWinPositive()
    }

    public void submit(Command command){
       command.execute();
    }

}

