package com.asd.reversi.reversi.strategy;

import com.asd.reversi.reversi.model.MoveDetails;


public interface Strategy {
	
	boolean Move(int[][] board, MoveDetails details);

}
