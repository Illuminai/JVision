package com.illuminai.vision.backend.scene.light;

import com.illuminai.vision.backend.math.Point3d;

/**
 * An abstract light
 */
public abstract class Light {

    /**
     * the position
     */
    protected Point3d position;

    /**
     * the color
     */
    protected int color;

    /**
     * Constructs and initializes a Light from the specified position and color.
     *
     * @param position the position
     * @param color    the color
     */
    public Light(Point3d position, int color) {
        this.position = position;
        this.color = color;
    }

    /**
     * @return the position
     */
    public Point3d getPosition() {
        return position;
    }

    /**
     * @param position the position
     */
    public void setPosition(Point3d position) {
        this.position = position;
    }

    /**
     * @return the color
     */
    public int getColor() {
        return color;
    }

    /**
     * @param color the color
     */
    public void setColor(int color) {
        this.color = color;
    }

}
