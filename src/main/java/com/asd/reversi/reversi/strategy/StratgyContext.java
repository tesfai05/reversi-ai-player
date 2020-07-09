package com.asd.reversi.reversi.strategy;


import com.asd.reversi.reversi.model.MoveDetails;

public class StratgyContext {
	private Strategy strategy;

	public StratgyContext(Strategy strategy) {
		super();
		this.strategy = strategy;
	}
	
	public boolean execute(int[][] board, MoveDetails details) {
		return strategy.Move(board, details);
		
	}

}
