package com.asd.reversi.controller;

import com.asd.reversi.reversi.model.MoveDetails;
import com.asd.reversi.reversi.model.ReversiBoard;
import com.asd.reversi.service.PlayRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class PlayRoomController {

    @Autowired
    private PlayRoomService playRoomService;

    @MessageMapping("/register")
    @SendTo("/topic/start")
    public ReversiBoard register(String username) {
        playRoomService.startGame();
        return playRoomService.registerPlayer(username);
    }

    @MessageMapping("/move")
    @SendTo("/topic/move")
    public ReversiBoard move(MoveDetails details) throws Exception {
        return playRoomService.move(details);
    }
}
