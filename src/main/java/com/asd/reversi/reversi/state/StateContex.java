package com.asd.reversi.reversi.state;

public class StateContex {

	private IState state;
	private  long plyPositive;
	private long plyNegative;
	
	public StateContex(long plyPositive,long plyNegative) {
	this.plyPositive=plyPositive;
	this.plyNegative = plyNegative;

		
	}
	
	public IState getState() {
		if(this.plyPositive > this.plyNegative) {
			this.state = new StateWinPositive();
		}else if(this.plyPositive< this.plyNegative) {
				this.state = new StateWinNegative();
			
		}else if(this.plyPositive == this.plyNegative) {
				this.state = new StateDraw();
			}
		return state;
	}
}
