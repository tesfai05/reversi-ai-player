package com.asd.reversi.controller;

import com.asd.reversi.reversi.model.MoveDetails;
import com.asd.reversi.reversi.model.ReversiBoard;
import com.asd.reversi.reversi.player.Player;
import com.asd.reversi.reversi.util.Point;
import com.asd.reversi.reversi.util.Response;
import com.asd.reversi.service.PlayRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/getBoard")
    private ReversiBoard getBoard() {
        return playRoomService.getBoard();
    }

    @CrossOrigin
    @PostMapping("/registerPlayer")
    public Response registerAsJSON(String username) {
        playRoomService.startGame();
        Player p = playRoomService.registerPlayers(username);
        if(p != null)
            return new Response("player "+p.getName()+ " registered");
        else
            return new Response("Error on registering");
    }

    @CrossOrigin
    @PostMapping("/makeMove")
    public Point moveAsJSON(@RequestBody Point details) throws Exception {
        Point p = playRoomService.movePoint(details);
        //getBoard();
        return p;
    }

    @PostMapping("/generateMove")
    public MoveDetails moveAsJSON() throws Exception {
        return playRoomService.generateMove();
    }
}
