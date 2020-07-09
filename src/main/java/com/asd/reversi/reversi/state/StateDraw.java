package com.asd.reversi.reversi.state;

public class StateDraw  implements IState{

	private String  status;
	private static final long serialVersionUID = 1L;

@Override
public String checkWinOrDraw() {
	return "the game is drawn ";

}

public String getStatus() {
	
	return status;
}

public void setStatus(String status) {
	this.status = status;
}

}
