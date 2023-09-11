package com.asd.reversi.reversi.state;

public class StateWinNegative implements IState  {

	private static final long serialVersionUID = 1L;
	private String  status="Negative Win";

	@Override
	public String checkWinOrDraw() {
		return  "Remote Player Won";
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}

}
