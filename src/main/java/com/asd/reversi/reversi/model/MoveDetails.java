package com.asd.reversi.reversi.model;

import lombok.Data;

@Data
public class MoveDetails {
    private int player;
    private int x;
    private int y;


    public MoveDetails(int x, int y){
        this.x = x;
        this.y = y;
    }

    public MoveDetails(int x, int y, int player){
        this.x = x;
        this.y = y;
        this.player = player;
    }

    public MoveDetails() {

    }
}
