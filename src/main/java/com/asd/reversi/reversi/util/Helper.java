package com.asd.reversi.reversi.util;

import com.asd.reversi.reversi.model.MoveDetails;
import com.asd.reversi.reversi.model.ReversiBoard;
import com.asd.reversi.reversi.state.StateContex;
import com.asd.reversi.reversi.strategy.StrategyIMpl;
import com.asd.reversi.reversi.strategy.StratgyContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Helper {
    private static ReversiBoard reversiBoard = ReversiBoard.getInstance();

    public static ArrayList<MoveDetails> getMovesAsDetails(int[][] reversiboard, int player){
        ArrayList<MoveDetails> moveDetails = new ArrayList<MoveDetails>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int[][] board = ArrayUtil.copy(reversiboard);
                if (board[i][j] == 0 && Helper.doMove(board, new MoveDetails(i, j, player))) {
                    moveDetails.add(new MoveDetails(i,j,player));
                }
            }
        }
        return moveDetails;
    }

    public static int[][] calcNextMoves(int[][] reversiboard ,int player) {
        int[][] next = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int[][] board = ArrayUtil.copy(reversiboard);
                if (board[i][j] == 0 && Helper.doMove(board, new MoveDetails(i, j, player))) {
                    next[i][j] = 2*player;
                }
            }
        }
        return next;
    }

    public static boolean hasAnyMoves(int[][] board, int player){
        return getMovesAsDetails(board,player).size() > 0;
    }

    public static boolean doMove(int[][] board, MoveDetails details) {
        StratgyContext context = new StratgyContext(new StrategyIMpl()) ;
        return context.execute(board, details);
    }

    public static boolean isGameFinished(int[][] board) {
        Helper.checkState(board);
        return Arrays.stream(board)
                .flatMapToInt(Arrays::stream)
                .noneMatch(item -> item == 0);
    }

    public static void checkState(int[][] board) {
        long playerNegative = Arrays.stream(board)
                .flatMapToInt(Arrays::stream)
                .filter(x-> x==-1).count();

        long playerPositive=Arrays.stream(board)
                .flatMapToInt(Arrays::stream)
                .filter(x-> x==1).count();

        StateContex cont = new StateContex(playerPositive, playerNegative);
        reversiBoard.setState(cont.getState());
        cont.getState();
    }

    public static int getTotalStoneCount(int[][] board){
        int c = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(board[i][j] != 0) c++;
            }
        }
        return c;
    }

    public static int getPlayerStoneCount(int[][] board, int player){
        int score = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(board[i][j] == player) score++;
            }
        }
        return score;
    }

    public static ArrayList<MoveDetails> getStableDisks(int[][] board,int player,int i,int j){

        ArrayList<MoveDetails> stableDiscs = new ArrayList<>();

        int mi , mj;
        int oplayer = ((player == 1) ? 2 : 1);

        //move up
        ArrayList<MoveDetails> mupts = new ArrayList<>();
        mi = i - 1;
        mj = j;
        while(mi>0 && board[mi][mj] == player){
            mupts.add(new MoveDetails(mi,mj));
            mi--;
        }
        for(MoveDetails sd : mupts){
            boolean redundant = false;
            for(MoveDetails stableDisc : stableDiscs){
                if(sd.equals(stableDisc)){
                    redundant = true;
                    break;
                }
            }
            if(!redundant) stableDiscs.add(sd);
        }

        //move down
        ArrayList<MoveDetails> mdpts = new ArrayList<>();
        mi = i + 1;
        mj = j;
        while(mi<7 && board[mi][mj] == oplayer){
            mdpts.add(new MoveDetails(mi,mj));
            mi++;
        }
        for(MoveDetails sd : mdpts){
            boolean redundant = false;
            for(MoveDetails stableDisc : stableDiscs){
                if(sd.equals(stableDisc)){
                    redundant = true;
                    break;
                }
            }
            if(!redundant) stableDiscs.add(sd);
        }

        //move left
        ArrayList<MoveDetails> mlpts = new ArrayList<>();
        mi = i;
        mj = j - 1;
        while(mj>0 && board[mi][mj] == oplayer){
            mlpts.add(new MoveDetails(mi,mj));
            mj--;
        }
        for(MoveDetails sd : mlpts){
            boolean redundant = false;
            for(MoveDetails stableDisc : stableDiscs){
                if(sd.equals(stableDisc)){
                    redundant = true;
                    break;
                }
            }
            if(!redundant) stableDiscs.add(sd);
        }

        //move right
        ArrayList<MoveDetails> mrpts = new ArrayList<>();
        mi = i;
        mj = j + 1;
        while(mj<7 && board[mi][mj] == oplayer){
            mrpts.add(new MoveDetails(mi,mj));
            mj++;
        }
        for(MoveDetails sd : mrpts){
            boolean redundant = false;
            for(MoveDetails stableDisc : stableDiscs){
                if(sd.equals(stableDisc)){
                    redundant = true;
                    break;
                }
            }
            if(!redundant) stableDiscs.add(sd);
        }

        //move up left
        ArrayList<MoveDetails> mulpts = new ArrayList<>();
        mi = i - 1;
        mj = j - 1;
        while(mi>0 && mj>0 && board[mi][mj] == oplayer){
            mulpts.add(new MoveDetails(mi,mj));
            mi--;
            mj--;
        }
        for(MoveDetails sd : mulpts){
            boolean redundant = false;
            for(MoveDetails stableDisc : stableDiscs){
                if(sd.equals(stableDisc)){
                    redundant = true;
                    break;
                }
            }
            if(!redundant) stableDiscs.add(sd);
        }

        //move up right
        ArrayList<MoveDetails> murpts = new ArrayList<>();
        mi = i - 1;
        mj = j + 1;
        while(mi>0 && mj<7 && board[mi][mj] == oplayer){
            murpts.add(new MoveDetails(mi,mj));
            mi--;
            mj++;
        }
        for(MoveDetails sd : murpts){
            boolean redundant = false;
            for(MoveDetails stableDisc : stableDiscs){
                if(sd.equals(stableDisc)){
                    redundant = true;
                    break;
                }
            }
            if(!redundant) stableDiscs.add(sd);
        }

        //move down left
        ArrayList<MoveDetails> mdlpts = new ArrayList<>();
        mi = i + 1;
        mj = j - 1;
        while(mi<7 && mj>0 && board[mi][mj] == oplayer){
            mdlpts.add(new MoveDetails(mi,mj));
            mi++;
            mj--;
        }
        for(MoveDetails sd : mdlpts){
            boolean redundant = false;
            for(MoveDetails stableDisc : stableDiscs){
                if(sd.equals(stableDisc)){
                    redundant = true;
                    break;
                }
            }
            if(!redundant) stableDiscs.add(sd);
        }

        //move down right
        ArrayList<MoveDetails> mdrpts = new ArrayList<>();
        mi = i + 1;
        mj = j + 1;
        while(mi<7 && mj<7 && board[mi][mj] == oplayer){
            mdrpts.add(new MoveDetails(mi,mj));
            mi++;
            mj++;
        }
        for(MoveDetails sd : mdrpts){
            boolean redundant = false;
            for(MoveDetails stableDisc : stableDiscs){
                if(sd.equals(stableDisc)){
                    redundant = true;
                    break;
                }
            }
            if(!redundant) stableDiscs.add(sd);
        }

        return stableDiscs;
    }

    public static ArrayList<MoveDetails> getFrontierSquares(int[][] board,int player){

        ArrayList<MoveDetails> frontiers = new ArrayList<>();

        int oplayer = ((player == 1) ? 2 : 1);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(board[i][j]==oplayer){

                    ArrayList<MoveDetails> possiblefrontiers = new ArrayList<>();

                    //check 8 directions

                    //up
                    if(i>0 && board[i-1][j]==0) possiblefrontiers.add(new MoveDetails(i-1,j));
                    //down
                    if(i<7 && board[i+1][j]==0) possiblefrontiers.add(new MoveDetails(i+1,j));
                    //right
                    if(j<7 && board[i][j+1]==0) possiblefrontiers.add(new MoveDetails(i,j+1));
                    //left
                    if(j>0 && board[i][j-1]==0) possiblefrontiers.add(new MoveDetails(i,j-1));
                    //up-left
                    if(i>0 && j>0 && board[i-1][j-1]==0) possiblefrontiers.add(new MoveDetails(i-1,j-1));
                    //up-right
                    if(i>0 && j<7 && board[i-1][j+1]==0) possiblefrontiers.add(new MoveDetails(i-1,j+1));
                    //down-left
                    if(i<7 && j>0 && board[i+1][j-1]==0) possiblefrontiers.add(new MoveDetails(i+1,j-1));
                    //down-right
                    if(i<7 && j<7 && board[i+1][j+1]==0) possiblefrontiers.add(new MoveDetails(i+1,j+1));

                    //remove duplicates
                    for(MoveDetails pf : possiblefrontiers){
                        boolean redundant = false;
                        for(MoveDetails f : frontiers){
                            if(f.equals(pf)){
                                redundant = true;
                                break;
                            }
                        }
                        if(!redundant) frontiers.add(pf);
                    }
                }
            }
        }

        return frontiers;
    }
}