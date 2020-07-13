package com.asd.reversi.reversi.model;


import com.asd.reversi.reversi.player.Player;
import com.asd.reversi.reversi.player.PlayerFactory;
import com.asd.reversi.reversi.state.IState;

public class ReversiBoard extends BoardModel{

    private IState state;

    public ReversiBoard(int c, int r) {
        super(c,r);
        board[3][3] = 1;
        board[4][4] = 1;
        board[4][3] = -1;
        board[3][4] = -1;
    }

    public void reset() {
        playerFactory.getPlayers().removeAll(playerFactory.getPlayers());
        board = new int[8][8];
        board[3][3] = 1;
        board[4][4] = 1;
        board[4][3] = -1;
        board[3][4] = -1;
        next = new int[8][8];
        turn = 1;
        finished = false;
    }

    public static ReversiBoard getInstance() {
    	return SingletonPlayRoom.getPlayroom();
    }

    public PlayerFactory getPlayerFactory() {
        return playerFactory;
    }

    public Player getPlayerA(){
        if (getPlayerFactory().getPlayers().size() > 0)
            return getPlayerFactory().getPlayers().get(0);
        return null;
    }

    public Player getPlayerB(){
        if (getPlayerFactory().getPlayers().size() > 1) {
            return getPlayerFactory().getPlayers().get(1);
        }
        return null;
    }
    public void setState(IState state) {
        this.state = state;
        this.setWinner(state.checkWinOrDraw());
    }

//    private void setWinner(String checkWinOrDraw) {
//    }

}
