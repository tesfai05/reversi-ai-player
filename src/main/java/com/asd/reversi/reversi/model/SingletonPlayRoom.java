package com.asd.reversi.reversi.model;

public class SingletonPlayRoom {
	private SingletonPlayRoom(){
	}

	private static ReversiBoard singletonPlayRoom;

	public static ReversiBoard getPlayroom() {
		if(singletonPlayRoom == null){
			singletonPlayRoom = new ReversiBoard(8, 8);
		}
		return singletonPlayRoom;
	}
}
