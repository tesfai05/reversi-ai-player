package com.asd.reversi.reversi.strategy;//package ir.payam1986128.reversi.service;

import com.asd.reversi.reversi.model.MoveDetails;
import com.asd.reversi.reversi.model.PieceColor;
import com.asd.reversi.reversi.util.Node;
import com.asd.reversi.reversi.util.Traverse;

import java.util.ArrayList;
import java.util.List;

public class StrategyImpl2 implements Strategy {

//  PieceColor

    private final PieceColor mPlayer = PieceColor.WHITE;
    private final PieceColor mComputer = PieceColor.BLACK;
    private int mTotalMovesAhead = 3;
    private int mCornerBias = 10;
    private int mEdgeBias = 5;
    private int mRegion4Bias = -5;

//	private MainGUI mGUI = null;

    private int mPlayerMoves = 0;
    private int mComputerMoves = 0;
    private PieceColor[][] matrix = new PieceColor[8][8]; // Game board dimensions.
    private Node mRoot = null;
    private int mMovesAhead = 0;
    private boolean gameInProgress = false;
    private List<MoveDetails> possibleMoves;

    public StrategyImpl2() {}

    public void setMovesAhead(int moves)
    {
        if(moves < 2){
            this.mTotalMovesAhead = 2;
        }else{
            this.mTotalMovesAhead = moves;
        }
        if(moves > 10){
            this.mTotalMovesAhead = 10;
        }else{
            this.mTotalMovesAhead = moves;
        }
        return;
    }

    public int getMovesAhead()
    {
        return this.mTotalMovesAhead;
    }

    public void setFavorEdges(int favor)
    {
        if(favor < -50){
            this.mEdgeBias = -50;
        }else{
            this.mEdgeBias = favor;
        }
        if(favor > 50){
            this.mEdgeBias = 50;
        }else{
            this.mEdgeBias = favor;
        }
        return;
    }

    public int getFavorEdges()
    {
        return this.mEdgeBias;
    }

    public void setFavorCorners(int favor)
    {
        if(favor < -50){
            this.mCornerBias = -50;
        }else{
            this.mCornerBias = favor;
        }
        if(favor > 50){
            this.mCornerBias = 50;
        }else{
            this.mCornerBias = favor;
        }
        return;
    }

    public int getFavorCorners()
    {
        return this.mCornerBias;
    }

    public void setDisfavorRegion4(int favor)
    {
        if(favor < -50){
            this.mRegion4Bias = -50;
        }else{
            this.mRegion4Bias = favor;
        }
        if(favor > 50){
            this.mRegion4Bias = 50;
        }else{
            this.mRegion4Bias = favor;
        }
        return;
    }

    public int getDisfavorRegion4()
    {
        return this.mRegion4Bias;
    }

    public synchronized boolean performMove(final int x, final int y, final PieceColor player, final PieceColor opponent)
    {
        boolean isValid = false;
        Traverse t = new Traverse(x, y, player, opponent, this.matrix);
        if(t.isValid()){
            if(player == PieceColor.WHITE){
                //this.mGUI.setPiece(x, y, PieceColor.WHITE);
                this.matrix[x][y] = PieceColor.WHITE;
            }else{
                //this.mGUI.setPiece(x, y, PieceColor.BLACK);
                this.matrix[x][y] = PieceColor.BLACK;
            }
            t = new Traverse(x, y, player, opponent, this.matrix);
            List<Integer> flips = t.getFlips();
            flipPieces(flips, player, this.matrix, true);
            isValid = true;
        }
        return isValid;
    }

    private void simMoves()
    {
        this.mRoot = new Node();
        this.mMovesAhead = 0;

        // Get a list of all immediately available moves.
        List<MoveDetails> moves = this.findAllPossibleMoves(this.mComputer, this.mPlayer, this.matrix);

        if(moves.size() > 0){
            // Simulate moves from the immediate list.
            this.simMoves(this.mRoot, moves, this.matrix, this.mComputer, this.mPlayer);

        }
        return;
    }

    private void simMoves(final Node root,
                          final List<MoveDetails> moves,
                          final PieceColor[][] aMatrix,
                          final PieceColor playerA,
                          final PieceColor playerB)
    {
        if(++this.mMovesAhead < this.mTotalMovesAhead){
            for(MoveDetails aMove: moves)
            {
                Node aNode = new Node(aMove, root);
                //ID : 0 to 63 {8*y+x}
                root.setChild(getID(aMove.X(), aMove.Y()), aNode);

                // Make a copy of the game board.
                PieceColor[][] tempMatrix = new PieceColor[8][8];
                for(int y = 0; y < 8; y++)
                    for(int x = 0; x < 8; x++)
                        tempMatrix[x][y] = aMatrix[x][y];

                // Make a possible prospective move.
                tempMatrix[aMove.X()][aMove.Y()] = playerA;

                // Flip the simulated pieces for the move.
                Traverse t = new Traverse(aMove.X(), aMove.Y(), playerA, playerB, tempMatrix);
                List<Integer> flips = t.getFlips();
                this.flipPieces(flips, playerA, tempMatrix, false);

                // Simulate the opponent's possible counter moves.
                List<MoveDetails> tempMoves = this.findAllPossibleMoves(playerB, playerA, tempMatrix);
                if(tempMoves.size() > 0){
                    this.simMoves(aNode, tempMoves, tempMatrix, playerB, playerA);
                }
            }
        }
        return;
    }


    private MoveDetails findBestMove()
    {
        MoveDetails bestMove = null;
        List<Node> children = this.mRoot.getChildren();
        if(children.size() > 0){
            findBestMove(this.mRoot, true);

            // Now get the max from the root's children
            int bestIndex = 0;

            for(int i = 0; i < children.size(); i++)
            {
                // Bias is imposed here to simulate more strategic behavior.  Occupying corners and
                // edges of the game board often lead to strategic advantages in the game.

                if(children.get(i).getMove().X() == 0 && children.get(i).getMove().Y() == 0 ||
                        children.get(i).getMove().X() == 0 && children.get(i).getMove().Y() == 7 ||
                        children.get(i).getMove().X() == 7 && children.get(i).getMove().Y() == 0 ||
                        children.get(i).getMove().X() == 7 && children.get(i).getMove().Y() == 7){
                    // Highest bias toward corners.
                    children.get(i).setMaxScore(children.get(i).getMaxScore() + this.mCornerBias);
                }else if(children.get(i).getMove().X() == 1 && children.get(i).getMove().Y() == 0 ||
                        children.get(i).getMove().X() == 0 && children.get(i).getMove().Y() == 1 ||
                        children.get(i).getMove().X() == 1 && children.get(i).getMove().Y() == 1 ||
                        children.get(i).getMove().X() == 6 && children.get(i).getMove().Y() == 0 ||
                        children.get(i).getMove().X() == 7 && children.get(i).getMove().Y() == 1 ||
                        children.get(i).getMove().X() == 6 && children.get(i).getMove().Y() == 1 ||
                        children.get(i).getMove().X() == 0 && children.get(i).getMove().Y() == 6 ||
                        children.get(i).getMove().X() == 1 && children.get(i).getMove().Y() == 7 ||
                        children.get(i).getMove().X() == 1 && children.get(i).getMove().Y() == 6 ||
                        children.get(i).getMove().X() == 7 && children.get(i).getMove().Y() == 6 ||
                        children.get(i).getMove().X() == 6 && children.get(i).getMove().Y() == 7 ||
                        children.get(i).getMove().X() == 6 && children.get(i).getMove().Y() == 6){
                    // Bias against Region4.
                    children.get(i).setMaxScore(children.get(i).getMaxScore() + this.mRegion4Bias);
                }else if(children.get(i).getMove().X() == 0 ||
                        children.get(i).getMove().X() == 7 ||
                        children.get(i).getMove().Y() == 0 ||
                        children.get(i).getMove().Y() == 7){
                    // Lower bias toward edges.
                    children.get(i).setMaxScore(children.get(i).getMaxScore() + this.mEdgeBias);
                }
                if(children.get(i).getMaxScore() > children.get(bestIndex).getMaxScore()){
                    bestIndex = i;
                }
            }
            bestMove = children.get(bestIndex).getMove();
        }

        return bestMove;
    }

    private void findBestMove(final Node root, final boolean getMaxFromMin)
    {
        // The idea behind this recursive method, is to traverse all the way out to the leaves of
        // the tree, then calculate scores for parent nodes while returning back to the root.
        List<Node> children = root.getChildren();
        if(children.size() <= 0){
            for(Node child : children)
            {
                this.findBestMove(root, !getMaxFromMin);
                child.setMaxScore(child.getMinMaxFromChildren(getMaxFromMin));
                child.setMinScore(child.getMinMaxFromChildren(!getMaxFromMin));
            }
        }
        return;
    }

    private List<MoveDetails> findAllPossibleMoves(final PieceColor player, final PieceColor opponent, final PieceColor[][] aMatrix)
    {
        // Traverse the full grid for specified player pieces.
        List<MoveDetails> allPossibleMoves = new ArrayList<MoveDetails>();
        for(int y = 0; y < 8; y++)
            for(int x = 0; x < 8; x++)
                if(aMatrix[x][y] == player){
                    Traverse t = new Traverse(x, y, opponent, aMatrix);
                    List<MoveDetails> someMoves = t.getMoves();
                    // Don't want to double-count moves, only tally the pieces that can be taken.
                    for(MoveDetails thisMove : someMoves)
                    {
                        boolean found = false;
                        for(MoveDetails thatMove : allPossibleMoves){
                            if(thisMove.X() == thatMove.X() && thisMove.Y() == thatMove.Y()){
                                thatMove.opponentPieces(thatMove.opponentPieces() + thisMove.opponentPieces());
                                found = true;
                                break;
                            }
                        }
                        if(!found)
                            allPossibleMoves.add(thisMove);

                    }
                }
        this.possibleMoves=allPossibleMoves;
        return allPossibleMoves;
    }


    private void flipPieces(final List<Integer> flips, final PieceColor player, final PieceColor[][] aMatrix, final boolean isActualMove)
    {
        if(flips.size() > 0){
            for(int flip : flips)
            {
                int i = getX(flip);
                int j = getY(flip);
                if(player == PieceColor.WHITE){
                    aMatrix[i][j] = PieceColor.WHITE;
                    if(isActualMove){	// An actual move would be displayed on the board.
                        //this.mGUI.setPiece(i, j, PieceColor.WHITE);
                    }
                }else{
                    aMatrix[i][j] = PieceColor.BLACK;
                    if(isActualMove){
                        //this.mGUI.setPiece(i, j, PieceColor.BLACK);
                    }
                }
            }
        }
        return;
    }

    private int countPieces(final PieceColor player){
        int count = 0;
        for(int y = 0; y < 8; y++)
            for(int x = 0; x < 8; x++)
                if(this.matrix[x][y] == player)
                    count++;
        return count;
    }




    //=========Board Grid ==========

    public int getID(int x, int y){
        return (8 * y) + x;
    }
    public static int getX(int square){
        return (int)(square % ( 8*1.0));
    }
    public static int getY(int square){
        return (int)Math.floor(square /( 8*1.0));

    }


    @Override
    public boolean Move(int[][] board, MoveDetails details) {

        return this.possibleMoves.size()>0;
    }

}