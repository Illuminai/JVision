package ch.herzog.lighthouse.render;

import ch.herzog.lighthouse.math.Point3d;
import ch.herzog.lighthouse.scene.shape.Shape;

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
     * the time
     */
    private double time;

    /**
     * the point
     */
    private Point3d point;

    /**
     * Constructs and initializes an Intersection from ray, shape and time.
     *
     * @param ray   the ray
     * @param shape the shape
     * @param time  the time
     */
    public Intersection(Ray ray, Shape shape, double time) {
        this.ray = ray;
        this.shape = shape;
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
     * @return the time
     */
    public double getTime() {
        return time;
    }

    /**
     * @return the point
     */
    public Point3d getPoint() {
        return point;
    }

}