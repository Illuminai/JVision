package com.illuminai.vision.backend.render;

/**
 * A color
 */
public class Color {

    /**
     * the red component
     */
    private int red;

    /**
     * the green component
     */
    private int green;

    /**
     * the blue component
     */
    private int blue;

    /**
     * Constructs and initializes a Ray from the specified color
     *
     * @param color the color
     */
    public Color(int color) {
        this((color & 0xFF0000) >> 16, (color & 0xFF00) >> 8, (color & 0xFF));
    }

    /**
     * Constructs and initializes a Ray from the specified color components
     *
     * @param red   the red component
     * @param green the green component
     * @param blue  the blue component
     */
    public Color(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    /**
     * Adjust the intensity of the color
     *
     * @param intensity the intensity
     * @return the color
     */
    public Color intensify(double intensity) {
        float[] hsv = new float[3];
        java.awt.Color.RGBtoHSB(red, green, blue, hsv);
        hsv[2] = hsv[2] * (float) intensity;
        int rgb = java.awt.Color.HSBtoRGB(hsv[0], hsv[1], hsv[2]);

        return new Color(rgb);
    }

    /**
     * @return the color int
     */
    public int getRGB() {
        int r = (red << 16) & 0xff0000;
        int g = (green << 8) & 0x00ff00;
        int b = blue & 0x0000ff;
        return r | g | b;
    }

    /**
     * @return the red component
     */
    public int getRed() {
        return red;
    }

    /**
     * @param red the red component
     */
    public void setRed(int red) {
        this.red = red;
    }

    /**
     * @return the green component
     */
    public int getGreen() {
        return green;
    }

    /**
     * @param green the green component
     */
    public void setGreen(int green) {
        this.green = green;
    }

    /**
     * @return the blue component
     */
    public int getBlue() {
        return blue;
    }

    /**
     * @param blue the blue component
     */
    public void setBlue(int blue) {
        this.blue = blue;
    }

}
