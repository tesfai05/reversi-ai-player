package com.asd.reversi.controller;

import com.asd.reversi.reversi.model.MoveDetails;
import com.asd.reversi.reversi.model.ReversiBoard;
import com.asd.reversi.reversi.player.Player;
import com.asd.reversi.service.PlayRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
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

    @PostMapping("/registerPlayer")
    public List<Player> registerAsJSON(String username) {
        playRoomService.startGame();
        return playRoomService.registerPlayerReturnResult(username);
    }

    @PostMapping("/makeMove")
    public ReversiBoard moveAsJSON(@RequestBody MoveDetails details) throws Exception {
        return playRoomService.move(details);
    }

    @PostMapping("/generateMove")
    public MoveDetails moveAsJSON() throws Exception {
        return playRoomService.generateMove();
    }
}
