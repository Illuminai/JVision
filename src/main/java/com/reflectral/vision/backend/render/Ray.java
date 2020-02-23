package com.reflectral.vision.backend.render;

import com.reflectral.vision.backend.math.Point3d;
import com.reflectral.vision.backend.math.Vector3d;

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
    private Vector3d direction;

    /**
     * Constructs and initializes a Ray from the specified origin and direction
     *
     * @param origin    the origin
     * @param direction the direction
     */
    public Ray(Point3d origin, Vector3d direction) {
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
    public Vector3d getDirection() {
        return direction;
    }

    /**
     * @param direction the direction
     */
    public void setDirection(Vector3d direction) {
        this.direction = direction;
    }
}