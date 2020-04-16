package com.illuminai.vision.backend.render;

import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.scene.shape.Shape;

/**
 * An intersection
 */
public class Intersection {

    /**
     * the ray
     */
    private Ray ray;

    /**
     * the shape
     */
    private Shape shape;

    /**
     * the normal vector
     */
    private Vector3d normal;

    /**
     * the time
     */
    private double time;

    /**
     * the point
     */
    private Vector3d point;

    /**
     * Constructs and initializes an Intersection from ray, shape and time.
     *
     * @param ray  the ray
     * @param shape the shape
     * @param time the time
     */
    public Intersection(Ray ray, Shape shape, Vector3d normal, double time) {
        this.ray = ray;
        this.shape = shape;
        this.normal = normal.normalize();
        this.time = time;
        point = ray.getPointOnRay(time);
    }

    /**
     * @return the ray
     */
    public Ray getRay() {
        return ray;
    }

    /**
     * @return the shape
     */
    public Shape getShape() {
        return shape;
    }

    /**
     * @return the normal vector
     */
    public Vector3d getNormal() {
        return normal;
    }

    /**
     * @return the time
     */
    public double getTime() {
        return time;
    }

    /**
     * @return the point
     */
    public Vector3d getPoint() {
        return point;
    }

    public void setRay(Ray ray) {
        this.ray = ray;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public void setNormal(Vector3d normal) {
        this.normal = normal;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public void setPoint(Vector3d point) {
        this.point = point;
    }
}