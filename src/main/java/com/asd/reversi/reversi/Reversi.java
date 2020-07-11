package com.asd.reversi.reversi;

import com.asd.reversi.reversi.evaluation.RealTimeEval;
import com.asd.reversi.reversi.model.MoveDetails;
import com.asd.reversi.reversi.model.ReversiBoard;
import com.asd.reversi.reversi.player.ComputerPlayer;
import com.asd.reversi.reversi.player.Player;
import com.asd.reversi.reversi.util.Helper;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class Reversi {
    private static ReversiBoard reversiBoard = ReversiBoard.getInstance();

    public ReversiBoard registerPlayer(String username) {
        if (reversiBoard.getPlayerFactory().getPlayers().size() == 0) {
            reversiBoard.getPlayerFactory().createPlayer("human",username,1);
        } else if (reversiBoard.getPlayerFactory().getPlayers().size() == 1) {
            reversiBoard.getPlayerFactory().createPlayer("computer",username,-1);
        }
        return reversiBoard;
    }

    public List<Player> registerPlayerAsResult(String username) {
        if (reversiBoard.getPlayerFactory().getPlayers().size() == 0) {
            reversiBoard.getPlayerFactory().createPlayer("human",username,1);
        } else if (reversiBoard.getPlayerFactory().getPlayers().size() == 1) {
            reversiBoard.getPlayerFactory().createPlayer("computer",username,-1);
        }
        return reversiBoard.getPlayerFactory().getPlayers();
    }

    public void startGame() {
        if (reversiBoard.isFinished()) {
            reversiBoard.reset();
        }
        reversiBoard.setNext(Helper.calcNextMoves(reversiBoard.getBoard() ,reversiBoard.getTurn()));
    }

    public ReversiBoard move(MoveDetails details) throws Exception {
        if (details.getX() != -5 && details.getY() != -5) { // Error Check Temporarily
        if (!isItPlayersTurn(details)) {
            throw new Exception("It's not your turn");
        }
        if (!isMoveValid(details)) {
            throw new Exception("It's not a valid movement");
        }
            Helper.doMove(reversiBoard.getBoard(), details);
        if (!Helper.isGameFinished(reversiBoard.getBoard())) {
            setTurn(details);
            reversiBoard.setNext(Helper.calcNextMoves(reversiBoard.getBoard() ,reversiBoard.getTurn()));
        } else {
            Helper.checkState(reversiBoard.getBoard());
            reversiBoard.setFinished(true);
            System.out.println("game is over"); // for game  is over state
        }

        if(reversiBoard.getPlayerFactory().getPlayers().get(1).getName().equalsIgnoreCase("computer") && reversiBoard.getTurn() == -1){
            move(generateComputerMove());
        }
        }
        return reversiBoard;
    }

    public MoveDetails generateComputerMove(){
        MoveDetails details = new MoveDetails();
        details.setPlayer(-1);
        ComputerPlayer computerPlayer = null;
        for (Player p: reversiBoard.getPlayerFactory().getPlayers()) {
            if(p.getFlag() == details.getPlayer()){
                computerPlayer = (ComputerPlayer) p;
            }
        }

        details = ComputerPlayer.solve(reversiBoard.getBoard(), computerPlayer.getFlag(), 3,new RealTimeEval(new int[][] {
                {8, 85, -40, 10, 210, 520},
                {8, 85, -40, 10, 210, 520},
                {33, -50, -15, 4, 416, 2153},
                {46, -50, -1, 3, 612, 4141},
                {51, -50, 62, 3, 595, 3184},
                {33, -5,  66, 2, 384, 2777},
                {44, 50, 163, 0, 443, 2568},
                {13, 50, 66, 0, 121, 986},
                {4, 50, 31, 0, 27, 192},
                {8, 500, 77, 0, 36, 299}}, new int[] {0, 55, 56, 57, 58, 59, 60, 61, 62, 63}));
        //Move Checking
        if(details == null) { details = new MoveDetails(-5,-5,-1);}
            if (details.getX() == -5 && details.getY() == -5) {
                    setTurn(details);
            }
        return details;
    }

    private boolean isMoveValid(MoveDetails details) {
        int[][] nextMoves = Helper.calcNextMoves(reversiBoard.getBoard(), details.getPlayer());
        return nextMoves[details.getX()][details.getY()] == 2*details.getPlayer();
    }

    private boolean isItPlayersTurn(MoveDetails details) {
        return reversiBoard.getTurn() == details.getPlayer();
    }

    private boolean canOtherPlayerMove(int player) {
        int otherPlayer = -player;
        int[][] nextMoves = Helper.calcNextMoves(reversiBoard.getBoard(), otherPlayer);
        return Arrays.stream(nextMoves).flatMapToInt(Arrays::stream).anyMatch(item -> item == (2*otherPlayer));
    }

    private void setTurn(MoveDetails details) { // change player turn  to the oposit by puting -ve
        if (canOtherPlayerMove(details.getPlayer())) {
            reversiBoard.setTurn(-reversiBoard.getTurn());
        }
    }
}
