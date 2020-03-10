package com.illuminai.vision.backend.scene.shape;

import com.illuminai.vision.backend.math.Point3d;

public class Plane extends Shape {

    /**
     * Constructs and initializes a Shape from the specified position and color.
     *
     * @param position the position
     * @param color    the color
     */
    public Plane(Point3d position, int color) {
        super(position, color);
    }

    @Override
    public double getDistance(Point3d point) {
        return point.getY();
    }
}
