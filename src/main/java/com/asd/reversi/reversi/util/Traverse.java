package com.asd.reversi.reversi.util;

import com.asd.reversi.reversi.model.MoveDetails;
import com.asd.reversi.reversi.model.PieceColor;
import com.asd.reversi.reversi.strategy.StrategyImpl2;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;


public class Traverse
{
	private PieceColor[][] mMatrix = null;
	private PieceColor mPlayer = null;
	private PieceColor mOpponent = null;
	private int mX = 0;
	private int mY = 0;
	private List<Integer> mFlips = null; // pieces intended to be flipped.
	private List<MoveDetails> mMoves = null; // all possible moves in relation to one specified grid location.
	private List<Point> mSources = null;
	private boolean mIsValid = false;
	private static final int NUMBER_OF_DIRECTIONS = 8;
	
	private final int[][] directions = new int[][] {{0, -1},	// North.
											 		{1, -1},	// Northeast.
											 		{1, 0},		// East.
											 		{1, 1},		// Southeast.
											 		{0, 1},		// South.
											 		{-1, 1},	// Southwest.
											 		{-1, 0},	// West.
											 		{-1, -1}};	// Northwest.
	
	public Traverse(){return;}
	
	public Traverse(final int x, final int y, final PieceColor opponent, final PieceColor[][] matrix)
	{
		this.setX(x);
		this.setY(y);
		this.setOpponent(opponent);
		this.setMatrix(matrix);
		this.search();
		return;
	}
	
	public Traverse(final int x, final int y, final PieceColor player, final PieceColor opponent, final PieceColor[][] matrix)
	{
		this.setX(x);
		this.setY(y);
		this.setPlayer(player);
		this.setOpponent(opponent);
		this.setMatrix(matrix);
		this.search();
		return;
	}
	
	public void search()
	{
		this.mMoves = new ArrayList<MoveDetails>();
		this.mFlips = new ArrayList<Integer>();
		this.mSources = new ArrayList<Point>();
		int opponentPieces = 0;
		boolean foundMove = false;
		MoveDetails aMove = null;
		PieceColor target = this.mPlayer;
		
		if(target == null){
			target = PieceColor.NONE;
		}
		
		for(int k = 0; k < Traverse.NUMBER_OF_DIRECTIONS; k++) // Search through all eight directions.
		{
			int i = this.mX;
			int j = this.mY;
			opponentPieces = 0;
			foundMove = false;
			ArrayList<Integer> temp = new ArrayList<Integer>();
			boolean done = false;
			while(!done)
			{
				i += this.directions[k][0];
				j += this.directions[k][1];
				if(i >= 0 && j >= 0 && i < 8 && j < 8){
					if(this.mMatrix[i][j] == this.mOpponent){	// Is an opponent's piece.
						opponentPieces++;
						temp.add(new StrategyImpl2().getID(i, j));
					}else if(this.mMatrix[i][j] == target){		// square at end of traversal.
						if(this.mMatrix[i][j] == this.mPlayer && opponentPieces > 0){
							this.mSources.add(new Point(i, j));
						}
						foundMove = true;
						break;
					}else{		// Is player's own piece.
						break;
					}
				}else{		// Edge of game board found.
					break;
				}
			}
			if(foundMove == true && opponentPieces > 0){
				aMove = new MoveDetails(i, j, opponentPieces);
				this.mMoves.add(aMove);
				if(this.mMatrix[mX][mY] == PieceColor.NONE){
					this.mIsValid = true;
				}
				if(temp.size() > 0){
					for(int flip : temp)
						this.mFlips.add(flip);
				}
			}
		}
		return;
	}
	
	public void setMatrix(PieceColor[][] matrix)
	{
		if(matrix != null){
			this.mMatrix = matrix;
		}else{
			System.out.println("Traverse.setMatrix - attempt to pass null object.");
		}
		return;
	}
	
	public void setPlayer(PieceColor player)
	{
		if(player != null){
			this.mPlayer = player;
		}else{
			System.out.println("Traverse.setPlayer - attempt to pass null object.");
		}
		return;
	}
	
	public void setOpponent(PieceColor opponent)
	{
		if(opponent != null){
			this.mOpponent = opponent;
		}else{
			System.out.println("Traverse.setOpponent - attempt to pass null object.");
		}
		return;
	}
	
	public void setX(int x)
	{
		if(x >= 0 && x < 8){
			this.mX = x;
		}else{
			System.out.println("Traverse.setX - attempt to pass invalid value: " + x);
		}
		return;
	}
	
	public int getX()
	{
		return this.mX;
	}
	
	public void setY(int y)
	{
		if(y >= 0 && y < 8){
			this.mY = y;
		}else{
			System.out.println("Traverse.setY - attempt to pass invalid value: " + y);
		}
		return;
	}
	
	public int getY()
	{
		return this.mY;
	}
	
	public List<MoveDetails> getMoves()
	{
		if(this.mMoves != null){
			return this.mMoves;
		}else{
			System.out.println("Traverse.getMoves - this.mMoves is null.");
			return null;
		}
	}
	
	
	public List<Integer> getFlips()
	{
		if(this.mFlips != null){
			return this.mFlips;
		}else{
			System.out.println("Traverse.getFlips - this.mFlips is null.");
			return null;
		}
	}
	
	public List<Point> getSources()
	{
		if(this.mSources != null){
			return this.mSources;
		}else{
			System.out.println("Traverse.getSources - this.mSources is null.");
			return null;
		}
	}
	
	public boolean isValid()
	{
		return this.mIsValid;
	}
	
	
}

