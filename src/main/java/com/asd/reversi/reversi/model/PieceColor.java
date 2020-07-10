package com.asd.reversi.reversi.model;

public enum PieceColor {

    WHITE(1), BLACK(-1),NONE(0);

    PieceColor(int value) {
        this.value = value;
    }

    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
