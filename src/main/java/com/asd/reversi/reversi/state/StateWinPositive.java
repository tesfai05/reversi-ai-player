package com.asd.reversi.reversi.state;

public class StateWinPositive implements IState {

	private static final long serialVersionUID = 1L;
	//private String  status="Positive Win";


	@Override
	public String checkWinOrDraw() {
		return " white  win the game";


	}
}

//	public String getStatus() {
//		return status;
//	}


//	public void setStatus(String status) {
//		this.status = status;
//	}
