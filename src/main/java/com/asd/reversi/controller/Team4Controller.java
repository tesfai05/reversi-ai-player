package com.asd.reversi.controller;

import com.asd.reversi.reversi.player.Player;
import com.asd.reversi.reversi.util.Point;
import com.asd.reversi.reversi.util.Response;
import com.asd.reversi.service.PlayRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/team4")
public class Team4Controller {
    @Autowired
    private PlayRoomService playRoomService;

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
        return playRoomService.movePoint(details);
    }
}
