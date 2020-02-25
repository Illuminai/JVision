package com.illuminai.vision.backend.render;

import com.illuminai.vision.backend.math.Point3d;
import com.illuminai.vision.backend.math.Vector;

/**
 * A ray
 */
public class Ray {

    /**
     * the origin
     */
    private Point3d origin;

    /**
     * the direction
     */
    private Vector direction;

    /**
     * Constructs and initializes a Ray from the specified origin and direction
     *
     * @param origin    the origin
     * @param direction the direction
     */
    public Ray(Point3d origin, Vector direction) {
        this.origin = origin;
        this.direction = direction.normalize();
    }

    /**
     * Calculates the point on the ray from the specified time
     *
     * @param time the time
     * @return the point
     */
    public Point3d getPointOnRay(double time) {
        return origin.add(direction.scale(time));
    }

    /**
     * @return the origin
     */
    public Point3d getOrigin() {
        return origin;
    }

    /**
     * @param origin the origin
     */
    public void setOrigin(Point3d origin) {
        this.origin = origin;
    }

    /**
     * @return the direction
     */
    public Vector getDirection() {
        return direction;
    }

    /**
     * @param direction the direction
     */
    public void setDirection(Vector direction) {
        this.direction = direction;
    }
}