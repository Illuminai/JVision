package com.illuminai.vision.backend.render;

import com.illuminai.vision.backend.math.Vector3d;

/**
 * A color
 */
public class Color {

    /**
     * the red component
     */
    private double red;

    /**
     * the green component
     */
    private double green;

    /**
     * the blue component
     */
    private double blue;

    /**
     * Constructs and initializes a Ray from the specified color components
     *
     * @param red   the red component
     * @param green the green component
     * @param blue  the blue component
     */
    public Color(double red, double green, double blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public Color add(Vector3d color) {
        double r = red + color.getX();
        double g = green + color.getY();
        double b = blue + color.getZ();
        return new Color(r, g, b);
    }

    public Color add(Color color) {
        double r = red + color.getRed();
        double g = green + color.getGreen();
        double b = blue + color.getBlue();
        return new Color(r, g, b);
    }

    public Color multiply(double scalar) {
        double r = red * scalar;
        double g = green * scalar;
        double b = blue * scalar;
        return new Color(r, g, b);
    }

    public Color multiply(Color color) {
        double r = red * color.getRed();
        double g = green * color.getGreen();
        double b = blue * color.getBlue();
        return new Color(r, g, b);
    }

    /**
     * @return the color int
     */
    public int getRGB() {
        int r = (((int) Math.round(clamp(red, 0.0, 1.0) * 255)) << 16) & 0xff0000;
        int g = (((int) Math.round(clamp(green, 0.0, 1.0) * 255)) << 8) & 0x00ff00;
        int b = ((int) Math.round(clamp(blue, 0.0, 1.0) * 255)) & 0x0000ff;
        return r | g | b;
    }

    public double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }

    /**
     * @return the red component
     */
    public double getRed() {
        return red;
    }

    /**
     * @param red the red component
     */
    public void setRed(double red) {
        this.red = red;
    }

    /**
     * @return the green component
     */
    public double getGreen() {
        return green;
    }

    /**
     * @param green the green component
     */
    public void setGreen(double green) {
        this.green = green;
    }

    /**
     * @return the blue component
     */
    public double getBlue() {
        return blue;
    }

    /**
     * @param blue the blue component
     */
    public void setBlue(double blue) {
        this.blue = blue;
    }

    @Override
    public String toString() {
        return "Color{" +
                "red=" + red +
                ", green=" + green +
                ", blue=" + blue +
                '}';
    }
}
