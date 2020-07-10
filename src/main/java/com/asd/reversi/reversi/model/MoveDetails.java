package com.asd.reversi.reversi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoveDetails {
    private int player;
    private int x;
    private int y;

    private int mOpponentPieces = 0;
    private int mBestScore = 0;

    public MoveDetails(int x, int y)
    {
        this.X(x);
        this.Y(y);
        return;
    }

    public MoveDetails(int x, int y, int numPieces)
    {
        this.X(x);
        this.Y(y);
        this.opponentPieces(numPieces);
        return;
    }

    public MoveDetails(MoveDetails that)
    {
        if(that != null){
            this.x = that.x;
            this.y = that.y;
            this.mOpponentPieces = that.mOpponentPieces;
        }else{
            System.out.println("Move copy constructor - attempt to pass null object.");
        }
    }

    public void X(int x)
    {
        if(x > -1 && x < 8){
            this.x = x;
        }else{
            System.out.println("Move.X(): Attempt to pass invalid value.");
        }
        return;
    }

    public int X() {
        return this.x;
    }

    public void Y(int y)
    {
        if(y > -1 && y < 8){
            this.y = y;
        }else{
            System.out.println("Move.Y(): Attempt to pass invalid value.");
        }
        return;
    }

    public int Y()
    {
        return this.y;
    }

    public void opponentPieces(int numPieces) {
        if(numPieces > 0 && numPieces < 64){
            this.mOpponentPieces = numPieces;
        }else{
            System.out.println("Move.opponentPieces(): Attempt to pass invalid value.");
        }
        return;
    }

    public int opponentPieces() {
        return this.mOpponentPieces;
    }

    public int getBestScore() {
        return this.mBestScore;
    }

    public void setBestScore(int score) {
        this.mBestScore = score;
        return;
    }
}
