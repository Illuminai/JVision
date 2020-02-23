package com.reflectral.vision.backend.scene.shape;

import com.reflectral.vision.backend.math.Point3d;
import com.reflectral.vision.backend.math.Vector3d;
import com.reflectral.vision.backend.render.Intersection;
import com.reflectral.vision.backend.render.Ray;

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
        this(position, 1.0);
    }

    /**
     * Constructs and initializes a Sphere from the specified position and radius.
     *
     * @param position the position
     * @param radius   the radius
     */
    public Sphere(Point3d position, double radius) {
        super(position);
        this.radius = radius;
    }

    /**
     * Calculates the intersection between the sphere and the ray
     *
     * @param ray the ray
     * @return the intersection
     */
    @Override
    public Intersection getIntersection(Ray ray) {
        double a = ray.getDirection().dot(ray.getDirection());
        double b = 2.0 * ray.getDirection().dot(ray.getOrigin().toVector().subtract(position.toVector()));
        double c = ray.getOrigin().toVector().subtract(position.toVector())
                .dot(ray.getOrigin().toVector().subtract(position.toVector())) - radius * radius;

        double discriminant = b * b - 4.0 * a * c;

        if (discriminant < 0) {
            return null;
        } else {
            double time1 = (-b + Math.sqrt(discriminant)) / 2.0 * a;
            double time2 = (-b - Math.sqrt(discriminant)) / 2.0 * a;
            double time;

            if(time1 < 0) {
                if(time2 < 0) {
                    //Object behind ray
                    return null;
                } else {
                    time = time2;
                }
            } else {
                if(time2 < 0) {
                    time = time1;
                } else {
                    time = Math.min(time1, time2);
                }
            }

            return new Intersection(ray, this, time);
        }
    }

    /**
     * Returns true if the sphere contains the point
     *
     * @param point the point
     * @return true if the sphere contains the point
     */
    @Override
    public boolean contains(Point3d point) {
        return new Vector3d(position, point).length() < radius;
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