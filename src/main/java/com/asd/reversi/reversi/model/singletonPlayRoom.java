package com.asd.reversi.reversi.model;

public enum singletonPlayRoom {
	INSTANCE;
	public ReversiBoard getPlayroom() {
		 return new ReversiBoard(8, 8);
	}
}
