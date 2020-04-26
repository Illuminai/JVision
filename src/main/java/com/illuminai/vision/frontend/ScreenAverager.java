package com.illuminai.vision.frontend;

public class ScreenAverager {
    private Screen[] screens;

    public ScreenAverager(int amount, int width, int height) {
        assert amount >= 1: "amount < 1";
        this.screens = new Screen[amount];
    }

    public Screen calculateAverage() {
        Screen result = new Screen(screens[0].getWidth(), screens[0].getHeight());
        int[][] rgb = new int[screens[0].getWidth() * screens[0].getHeight()][3];
        for(Screen s: screens) {
            for(int i = 0; i < s.getPixels().length; i++) {
                rgb[i][0] +=  s.getPixels()[i] & 0xff;
                rgb[i][1] +=  (s.getPixels()[i] >> 8) & 0xff;
                rgb[i][2] +=  (s.getPixels()[i] >> 16) & 0xff;
            }
        }

        for(int i = 0; i < rgb.length; i++) {
            result.setPixel(i, rgb[i][0] / screens.length | ((rgb[i][1] / screens.length) << 8)  | ((rgb[i][2] / screens.length) << 16));
        }
        return result;
    }

    public void setAmount(int amount) {
        this.screens = new Screen[amount];
    }

    public int getAmount() {
        return screens.length;
    }
    public Screen[] getScreens() {
        return screens;
    }
}
