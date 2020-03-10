package com.illuminai.vision.backend.scene.shape;

import com.illuminai.vision.backend.math.Point3d;
import com.illuminai.vision.backend.math.Vector3d;

/**
 * A sphere
 */
public class Sphere extends Shape {

    /**
     * the radius
     */
    private double radius;

    /**
     * Constructs and initializes a Sphere from the specified position.
     *
     * @param position the position
     */
    public Sphere(Point3d position) {
        this(position, 1.0, 0xffffff);
    }

    /**
     * Constructs and initializes a Sphere from the specified position, radius and color.
     *
     * @param position the position
     * @param radius   the radius
     */
    public Sphere(Point3d position, double radius, int color) {
        super(position, color);
        this.radius = radius;
    }

    @Override
    public double getDistance(Point3d point) {
        return new Vector3d(point, position).length() - radius;
    }

    /**
     * @return the radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * @param radius the radius
     */
    public void setRadius(double radius) {
        this.radius = radius;
    }

}