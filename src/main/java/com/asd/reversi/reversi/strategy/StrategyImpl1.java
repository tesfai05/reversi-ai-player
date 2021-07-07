package com.asd.reversi.reversi.strategy;

import com.asd.reversi.reversi.model.MoveDetails;
import com.asd.reversi.reversi.util.ArrayUtil;

public class StrategyImpl1 implements Strategy {

	@Override
	public boolean Move(int[][] board, MoveDetails details) {
		  int[][] target = ArrayUtil.copy(board); //after final comes here  no board printed
	        boolean changed = false;
	        outer: for (int i = 0; i < details.getX(); i++) { // at first detail.getx =0 so it will got to the outer
	            if (board[i][details.getY()] == details.getPlayer()) {
	                for (int j = i+1; j < details.getX(); j++) {
	                    if (board[j][details.getY()] != -details.getPlayer()) {
	                        continue outer;
	                    }
	                }
	                for (int j = i+1; j < details.getX(); j++) {
	                    target[j][details.getY()] = details.getPlayer();
	                    changed = true;
	                }
	            }
	        }
	        outer: for (int i = 7; i > details.getX(); i--) { // this was done second 
	            if (board[i][details.getY()] == details.getPlayer()) {
	                for (int j = i-1; j > details.getX(); j--) {
	                    if (board[j][details.getY()] != -details.getPlayer()) {
	                        continue outer;
	                    }
	                }
	                for (int j = i-1; j > details.getX(); j--) {
	                    target[j][details.getY()] = details.getPlayer();
	                    changed = true;
	                }
	            }
	        }
	        outer: for (int i = 0; i < details.getY(); i++) {
	            if (board[details.getX()][i] == details.getPlayer()) {
	                for (int j = i+1; j < details.getY(); j++) {
	                    if (board[details.getX()][j] != -details.getPlayer()) {
	                        continue outer;
	                    }
	                }
	                for (int j = i+1; j < details.getY(); j++) {
	                    target[details.getX()][j] = details.getPlayer();
	                    changed = true;
	                }
	            }
	        }
	        outer: for (int i = 7; i > details.getY(); i--) { // this is 3 
	            if (board[details.getX()][i] == details.getPlayer()) {
	                for (int j = i-1; j > details.getY(); j--) {
	                    if (board[details.getX()][j] != -details.getPlayer()) {
	                        continue outer;
	                    }
	                }
	                for (int j = i-1; j > details.getY(); j--) {
	                    target[details.getX()][j] = details.getPlayer();
	                    changed = true;
	                }
	            }
	        }			//0 at start				min =0 at start 
	        int startI = details.getX() - Math.min(details.getX(), details.getY()), // this is 4 
	                startJ = details.getY() - Math.min(details.getX(), details.getY());
	        outer: for (int i = 0; i < Math.min(details.getX(), details.getY()); i++) {
	            if (board[startI + i][startJ + i] == details.getPlayer()) {
	                for (int j = i+1; j < Math.min(details.getX(), details.getY()); j++) {
	                    if (board[startI + j][startJ + j] != -details.getPlayer()) {
	                        continue outer;
	                    }
	                }
	                for (int j = i+1; j < Math.min(details.getX(), details.getY()); j++) {
	                    target[startI + j][startJ + j] = details.getPlayer();
	                    changed = true;
	                }
	            }
	        }
	        int endI = details.getX() + (7 - Math.max(details.getX(), details.getY())),//this is 5 
	                endJ = details.getY() + (7 - Math.max(details.getX(), details.getY()));
	        outer: for (int i = 0; i < (7 - Math.max(details.getX(), details.getY())); i++) {
	            if (board[endI - i][endJ - i] == details.getPlayer()) {
	                for (int j = i+1; j < (7 - Math.max(details.getX(), details.getY())); j++) {
	                    if (board[endI - j][endJ - j] != -details.getPlayer()) {
	                        continue outer;
	                    }
	                }
	                for (int j = i+1; j < (7 - Math.max(details.getX(), details.getY())); j++) {
	                    target[endI - j][endJ - j] = details.getPlayer();
	                    changed = true;
	                }
	            }
	        }
	        startI = Math.min(7, details.getX() + details.getY()); // this 6 no board until this 
	        startJ = details.getX() + details.getY() - startI;
	        outer: for (int i = 0; i < (startI - details.getX()); i++) {
	            if (board[startI - i][startJ + i] == details.getPlayer()) {
	                for (int j = i+1; j < (startI - details.getX()); j++) {
	                    if (board[startI - j][startJ + j] != -details.getPlayer()) {
	                        continue outer;
	                    }
	                }
	                for (int j = i+1; j < (startI - details.getX()); j++) {
	                    target[startI - j][startJ + j] = details.getPlayer();
	                    changed = true;
	                }
	            }
	        }
	        endI = startJ; //7 end is 7 at beging
	        endJ = startI;
	        outer: for (int i = 0; i < (endJ - details.getY()); i++) {
	            if (board[endI + i][endJ - i] == details.getPlayer()) {
	                for (int j = i+1; j < (endJ - details.getY()); j++) {
	                    if (board[endI + j][endJ - j] != -details.getPlayer()) {
	                        continue outer;
	                    }
	                }
	                for (int j = i+1; j < (endJ - details.getY()); j++) {
	                    target[endI + j][endJ - j] = details.getPlayer();
	                    changed = true;
	                }
	            }
	        }
	        if (changed) {
	            target[details.getX()][details.getY()] = details.getPlayer();
	        }
	        ArrayUtil.copy(target, board);
	        return changed;
	    }
}
