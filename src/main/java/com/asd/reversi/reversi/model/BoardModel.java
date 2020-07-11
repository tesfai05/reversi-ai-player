package com.asd.reversi.reversi.model;

import com.asd.reversi.reversi.player.PlayerFactory;
import lombok.Data;

@Data
public class BoardModel {
    protected PlayerFactory playerFactory = new PlayerFactory();
    protected int[][] board;
    protected int[][] next;
    protected int turn = 1;
    protected boolean finished = false;
    protected String winner = "";

    public BoardModel(int c, int r) {
        board = new int[c][r];
        next = new int[c][r];
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
