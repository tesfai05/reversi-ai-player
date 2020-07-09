package com.asd.reversi.reversi.strategy;//package ir.payam1986128.reversi.service;
//
//import ir.payam1986128.reversi.model.MoveDetails;
//import ir.payam1986128.reversi.model.PieceColor;
//import ir.payam1986128.reversi.util.Globals;
//
//public class StrategyImp2 implements Strategy {
//	
//	//PieceColor
//	///
//	private final PieceColor mPlayer = PieceColor.WHITE;
//	private final PieceColor mComputer = PieceColor.BLACK;
//	private int mTotalMovesAhead = 3;
//	private int mCornerBias = 10;
//	private int mEdgeBias = 5;
//	private int mRegion4Bias = -5;
//	
////	private MainGUI mGUI = null;
//	private int mPlayerMoves = 0;
//	private int mComputerMoves = 0;
//	private PieceColor[][] matrix = new PieceColor[Globals.GRID_SIZE_INTEGER][Globals.GRID_SIZE_INTEGER]; // Game board dimensions.
//	private Node mRoot = null;
//	private int mMovesAhead = 0;
//	private boolean blinkingIsFinished = true;
//	private boolean gameInProgress = false;
//	
//	//
//	
//	
//	
//	private void simMoves()
//	{
//	    this.mRoot = new Node();
//	    this.mMovesAhead = 0;
//	    
//	    // Get a list of all immediately available moves.
//	    ArrayList moves = this.findAllPossibleMoves(this.mComputer, this.mPlayer, this.matrix);
//	    
//	    if(moves.size() > 0){
//	        // Simulate moves from the immediate list.
//	        this.simMoves(this.mRoot, moves, this.matrix, this.mComputer, this.mPlayer);
//	    }
//	    return;
//	}
//
//	private void simMoves(final Node root, 
//	                      final ArrayList moves, 
//	                      final SquareState[][] aMatrix, 
//	                      final SquareState playerA, 
//	                      final SquareState playerB)
//	{
//	    if(++this.mMovesAhead < this.mTotalMovesAhead){
//	        for(Move aMove: moves)
//	        {
//	            Node aNode = new Node(aMove, root);
//	            root.setChild(GridMath.getID(aMove.X(), aMove.Y()), aNode);
//	            
//	            // Make a copy of the game board.
//	            SquareState[][] tempMatrix = new SquareState[Globals.GRID_SIZE_INTEGER][Globals.GRID_SIZE_INTEGER];
//	            for(int y = 0; y < Globals.GRID_SIZE_INTEGER; y++)
//	                for(int x = 0; x < Globals.GRID_SIZE_INTEGER; x++)
//	                    tempMatrix[x][y] = aMatrix[x][y];
//	            
//	            // Make a possible prospective move.
//	            tempMatrix[aMove.X()][aMove.Y()] = playerA;
//	            // Flip the simulated pieces for the move.
//	            Traverse t = new Traverse(aMove.X(), aMove.Y(), playerA, playerB, tempMatrix);
//	            ArrayList flips = t.getFlips();
//	            this.flipPieces(flips, playerA, tempMatrix, false);
//	            
//	            // Simulate the opponent's possible counter moves.
//	            ArrayList tempMoves = this.findAllPossibleMoves(playerB, playerA, tempMatrix);
//	            if(tempMoves.size() > 0){
//	                this.simMoves(aNode, tempMoves, tempMatrix, playerB, playerA);
//	            }
//	        }
//	    }
//	    return;
//	}
//	
//	
//	public Move findBestMove()
//	{
//	    Move bestMove = null;
//	    ArrayList children = this.mRoot.getChildren();
//	    if(children.size() > 0){
//	        findBestMove(this.mRoot, true);
//	        // Now get the max from the root's children
//	        int bestIndex = 0;
//	        for(int i = 0; i < children.size(); i++)
//	        { 
//	            // Bias is imposed here to simulate more strategic behavior.  Occupying corners and
//	            // edges of the game board often lead to strategic advantages in the game.
//	            if(children.get(i).getMove().X() == 0 && children.get(i).getMove().Y() == 0 || 
//	               children.get(i).getMove().X() == 0 && children.get(i).getMove().Y() == Globals.GRID_SIZE_INTEGER - 1 || 
//	               children.get(i).getMove().X() == Globals.GRID_SIZE_INTEGER - 1 && children.get(i).getMove().Y() == 0 || 
//	               children.get(i).getMove().X() == Globals.GRID_SIZE_INTEGER - 1 && children.get(i).getMove().Y() == Globals.GRID_SIZE_INTEGER - 1){
//	                // Highest bias toward corners.
//	                children.get(i).setMaxScore(children.get(i).getMaxScore() + this.mCornerBias);
//	            }else if(children.get(i).getMove().X() == 1 && children.get(i).getMove().Y() == 0 ||
//	               children.get(i).getMove().X() == 0 && children.get(i).getMove().Y() == 1 ||
//	               children.get(i).getMove().X() == 1 && children.get(i).getMove().Y() == 1 ||
//	               children.get(i).getMove().X() == Globals.GRID_SIZE_INTEGER - 2 && children.get(i).getMove().Y() == 0 ||
//	               children.get(i).getMove().X() == Globals.GRID_SIZE_INTEGER - 1 && children.get(i).getMove().Y() == 1 ||
//	               children.get(i).getMove().X() == Globals.GRID_SIZE_INTEGER - 2 && children.get(i).getMove().Y() == 1 ||
//	               children.get(i).getMove().X() == 0 && children.get(i).getMove().Y() == Globals.GRID_SIZE_INTEGER - 2 ||
//	               children.get(i).getMove().X() == 1 && children.get(i).getMove().Y() == Globals.GRID_SIZE_INTEGER - 1 ||
//	               children.get(i).getMove().X() == 1 && children.get(i).getMove().Y() == Globals.GRID_SIZE_INTEGER - 2 ||
//	               children.get(i).getMove().X() == Globals.GRID_SIZE_INTEGER - 1 && children.get(i).getMove().Y() == Globals.GRID_SIZE_INTEGER - 2 ||
//	               children.get(i).getMove().X() == Globals.GRID_SIZE_INTEGER - 2 && children.get(i).getMove().Y() == Globals.GRID_SIZE_INTEGER - 1 ||
//	               children.get(i).getMove().X() == Globals.GRID_SIZE_INTEGER - 2 && children.get(i).getMove().Y() == Globals.GRID_SIZE_INTEGER - 2){
//	                // Bias against Region4.
//	                children.get(i).setMaxScore(children.get(i).getMaxScore() + this.mRegion4Bias);
//	            }else if(children.get(i).getMove().X() == 0 || 
//	                 children.get(i).getMove().X() == Globals.GRID_SIZE_INTEGER - 1 || 
//	                 children.get(i).getMove().Y() == 0 || 
//	                 children.get(i).getMove().Y() == Globals.GRID_SIZE_INTEGER - 1){
//	                // Lower bias toward edges.
//	                children.get(i).setMaxScore(children.get(i).getMaxScore() + this.mEdgeBias);
//	            }
//	            if(children.get(i).getMaxScore() > children.get(bestIndex).getMaxScore()){
//	                bestIndex = i;
//	            }
//	        }
//	        bestMove = children.get(bestIndex).getMove();
//	    }
//	    
//	    return bestMove;
//	}
//
//	private void findBestMove(final Node root, final boolean getMaxFromMin)
//	{
//	    // The idea behind this recursive method, is to traverse all the way out to the leaves of 
//	    // the tree, then calculate scores for parent nodes while returning back to the root.
//	    ArrayList children = root.getChildren();
//	    if(children.size() <= 0){
//	        for(Node child : children)
//	        {
//	            this.findBestMove(root, !getMaxFromMin);
//	            child.setMaxScore(child.getMinMaxFromChildren(getMaxFromMin));
//	            child.setMinScore(child.getMinMaxFromChildren(!getMaxFromMin));
//	        }
//	    }
//	    return;
//	}
//
//	@Override
//	public boolean Move(int[][] board, MoveDetails details) {
//		
//		return false;
//	}
//
//}
