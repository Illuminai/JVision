package com.illuminai.vision.backend.scene.shape;

import com.illuminai.vision.backend.math.Point3d;
import com.illuminai.vision.backend.render.Intersection;
import com.illuminai.vision.backend.render.Ray;

/**
 * An abstract shape
 */
public abstract class Shape {

    /**
     * the color
     */
    protected int color;

    /**
     * the position
     */
    protected Point3d position;

    /**
     * Constructs and initializes a Shape from the specified position and color.
     *
     * @param position the position
     * @param color the color
     */
    public Shape(Point3d position, int color) {
        this.position = position;
        this.color = color;
    }

    /**
     * Calculates the intersection between the shape and the ray
     *
     * @param ray the ray
     * @return the intersection
     */
    public abstract Intersection getIntersection(Ray ray);

    /**
     * Returns true if the shape contains the point
     *
     * @param point the point
     * @return true if the shape contains the point
     */
    public abstract boolean contains(Point3d point);

    /**
     * @param position the position
     */
    public void setPosition(Point3d position) {
        this.position = position;
    }

    /**
     * @return the position
     */
    public Point3d getPosition() {
        return position;
    }

    /**
     * @return the color
     */
    public int getColor() {
        return color;
    }
}