package com.asd.reversi.reversi.player;

import com.asd.reversi.reversi.evaluation.Evaluator;
import com.asd.reversi.reversi.model.MoveDetails;
import com.asd.reversi.reversi.util.Helper;

import java.util.ArrayList;

public class ComputerPlayer implements Player {

    private static int nodesExplored;
    private String name;
    private int flag;

    public ComputerPlayer(String name, int flag){
        this.name = name;
        this.flag = flag;
        MaxDepth = 5;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getFlag() {
        return flag;
    }
    private int MaxDepth;


    //returns max score move
    public static MoveDetails solve(int[][] board, int player, int depth, Evaluator e){
        nodesExplored = 0;
        int bestScore = Integer.MIN_VALUE;
        MoveDetails bestMove = null;
        for(MoveDetails move : Helper.getMovesAsDetails(board,player)){
            //create new node
            int[][] newNode = getNewBoardAfterMove(board,move,player);
            //recursive call
            int childScore = MMAB(newNode,player,depth-1,false,Integer.MIN_VALUE,Integer.MAX_VALUE,e);
            if(childScore > bestScore) {
                bestScore = childScore;
                bestMove = move;
            }
        }
        System.out.println("Nodes Explored : " + nodesExplored);
        return bestMove;
    }

    public static int[][] getNewBoardAfterMove(int[][] board, MoveDetails move , int player){
        //get clone of old board
        int[][] newboard = new int[8][8];
        for (int k = 0; k < 8; k++) {
            for (int l = 0; l < 8; l++) {
                newboard[k][l] = board[k][l];
            }
        }

        //place piece
        newboard[move.getX()][move.getY()] = player;
        //reverse pieces
        ArrayList<MoveDetails> rev = getReversePoints(newboard,player,move.getX(),move.getY());
        for(MoveDetails pt : rev){
            newboard[pt.getX()][pt.getY()] = player;
        }

        return newboard;
    }

    public static ArrayList<MoveDetails> getReversePoints(int[][] board,int player,int i,int j){

        ArrayList<MoveDetails> allReversePoints = new ArrayList<>();

        int mi , mj , c;
        int oplayer = ((player == 1) ? 2 : 1);

        //move up
        ArrayList<MoveDetails> mupts = new ArrayList<>();
        mi = i - 1;
        mj = j;
        while(mi>0 && board[mi][mj] == oplayer){
            mupts.add(new MoveDetails(mi,mj));
            mi--;
        }
        if(mi>=0 && board[mi][mj] == player && mupts.size()>0){
            allReversePoints.addAll(mupts);
        }


        //move down
        ArrayList<MoveDetails> mdpts = new ArrayList<>();
        mi = i + 1;
        mj = j;
        while(mi<7 && board[mi][mj] == oplayer){
            mdpts.add(new MoveDetails(mi,mj));
            mi++;
        }
        if(mi<=7 && board[mi][mj] == player && mdpts.size()>0){
            allReversePoints.addAll(mdpts);
        }

        //move left
        ArrayList<MoveDetails> mlpts = new ArrayList<>();
        mi = i;
        mj = j - 1;
        while(mj>0 && board[mi][mj] == oplayer){
            mlpts.add(new MoveDetails(mi,mj));
            mj--;
        }
        if(mj>=0 && board[mi][mj] == player && mlpts.size()>0){
            allReversePoints.addAll(mlpts);
        }

        //move right
        ArrayList<MoveDetails> mrpts = new ArrayList<>();
        mi = i;
        mj = j + 1;
        while(mj<7 && board[mi][mj] == oplayer){
            mrpts.add(new MoveDetails(mi,mj));
            mj++;
        }
        if(mj<=7 && board[mi][mj] == player && mrpts.size()>0){
            allReversePoints.addAll(mrpts);
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
        if(mi>=0 && mj>=0 && board[mi][mj] == player && mulpts.size()>0){
            allReversePoints.addAll(mulpts);
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
        if(mi>=0 && mj<=7 && board[mi][mj] == player && murpts.size()>0){
            allReversePoints.addAll(murpts);
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
        if(mi<=7 && mj>=0 && board[mi][mj] == player && mdlpts.size()>0){
            allReversePoints.addAll(mdlpts);
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
        if(mi<=7 && mj<=7 && board[mi][mj] == player && mdrpts.size()>0){
            allReversePoints.addAll(mdrpts);
        }

        return allReversePoints;
    }

    private static int MMAB(int[][] node, int player, int depth, boolean max, int alpha, int beta, Evaluator e){
        nodesExplored++;
        //if terminal reached or depth limit reached evaluate
        if(depth == 0 || Helper.isGameFinished(node)){
            return e.eval(node,player);
        }
        int oplayer = (player==1) ? 2 : 1;
        //if no moves available then forfeit turn
        if((max && !Helper.hasAnyMoves(node,player)) || (!max && !Helper.hasAnyMoves(node,oplayer))){
            //System.out.println("Forfeit State Reached !");
            return MMAB(node,player,depth-1,!max,alpha,beta,e);
        }
        int score;
        if(max){
            //maximizing
            score = Integer.MIN_VALUE;
            for(MoveDetails move : Helper.getMovesAsDetails(node,player)){ //my turn
                //create new node
                int[][] newNode = getNewBoardAfterMove(node,move,player);
                //recursive call
                int childScore = MMAB(newNode,player,depth-1,false,alpha,beta,e);
                if(childScore > score) score = childScore;
                //update alpha
                if(score > alpha) alpha = score;
                if(beta <= alpha) break; //Beta Cutoff
            }
        }else{
            //minimizing
            score = Integer.MAX_VALUE;
            for(MoveDetails move : Helper.getMovesAsDetails(node,oplayer)){ //opponent turn
                //create new node
                int[][] newNode = getNewBoardAfterMove(node,move,oplayer);
                //recursive call
                int childScore = MMAB(newNode,player,depth-1,true,alpha,beta,e);
                if(childScore < score) score = childScore;
                //update beta
                if(score < beta) beta = score;
                if(beta <= alpha) break; //Alpha Cutoff
            }
        }
        return score;
    }
}
