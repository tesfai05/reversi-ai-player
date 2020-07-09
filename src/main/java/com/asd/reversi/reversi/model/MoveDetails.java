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
}
