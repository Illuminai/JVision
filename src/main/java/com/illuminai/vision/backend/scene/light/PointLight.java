package com.illuminai.vision.backend.scene.light;

import com.illuminai.vision.backend.math.Point3d;

/**
 * a point light
 */
public class PointLight extends Light {

    /**
     * Constructs and initializes a PointLight from the specified position and color
     *
     * @param position the position
     * @param color    the color
     */
    public PointLight(Point3d position, int color) {
        super(position, color);
    }

}