package com.asd.reversi.service;

import com.asd.reversi.reversi.Reversi;
import com.asd.reversi.reversi.model.MoveDetails;
import com.asd.reversi.reversi.model.ReversiBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayRoomService {

	@Autowired
    private Reversi reversi;

    public ReversiBoard registerPlayer(String username) {
        return reversi.registerPlayer(username);
    }

    public void startGame() { 
        reversi.startGame();
    }

    public ReversiBoard move(MoveDetails details) throws Exception {
        return reversi.move(details);
    }
}
