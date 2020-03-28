package com.illuminai.vision;

import com.illuminai.vision.frontend.GameClass;

public class Vision {

    public static void main(String[] args) throws Exception {
        GameClass game = GameClass.createGame(args, 800, 600);
        game.initCanvas();
        game.start();
    }

}
