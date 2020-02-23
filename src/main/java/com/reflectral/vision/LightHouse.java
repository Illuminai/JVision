package com.reflectral.vision;

import com.reflectral.vision.frontend.GameClass;

public class LightHouse {

    public static void main(String[] args) throws Exception {
        GameClass game = GameClass.createGame(args, 500, 500);
        game.initCanvas();
        game.start();
    }

}
