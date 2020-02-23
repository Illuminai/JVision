package ch.herzog.lighthouse.scene.shape;

import ch.herzog.lighthouse.math.Point3d;
import ch.herzog.lighthouse.render.Intersection;
import ch.herzog.lighthouse.render.Ray;

/**
 * An abstract shape
 */
public abstract class Shape {

    /**
     * the position
     */
    protected Point3d position;

    /**
     * Constructs and initializes a Shape from the specified position.
     *
     * @param position the position
     */
    public Shape(Point3d position) {
        this.position = position;
    }

    public Point3d getPosition() {
        return position;
    }

    public void setPosition(Point3d position) {
        this.position = position;
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

}