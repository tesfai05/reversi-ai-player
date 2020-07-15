package com.asd.reversi.reversi.state;

import java.io.Serializable;

public interface IState  extends  Serializable  {
	public String checkWinOrDraw();
}
