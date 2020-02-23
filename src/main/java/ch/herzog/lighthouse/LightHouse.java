package ch.herzog.lighthouse;

import ch.gedik.lighthouse.graphics.GameClass;

public class LightHouse {
    public static void main(String[] args) throws Exception {
        GameClass game = GameClass.createGame(args, 500, 500);
        game.initCanvas();
        game.start();
    }

}
