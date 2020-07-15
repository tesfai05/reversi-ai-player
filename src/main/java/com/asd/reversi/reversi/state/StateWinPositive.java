package com.asd.reversi.reversi.state;

public class StateWinPositive implements IState {

	private static final long serialVersionUID = 1L;

	@Override
	public String checkWinOrDraw() {
		return "Home Player Won";
	}
}
