package com.asd.reversi.reversi.player;

public class RemotePlayer implements Player{

    private String name;
    private int flag;

    public RemotePlayer(String name, int flag){
        this.name = name;
        this.flag = flag;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getFlag() {
        return flag;
    }
}
