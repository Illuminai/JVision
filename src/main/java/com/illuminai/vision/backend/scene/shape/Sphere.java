package com.illuminai.vision.backend.scene.shape;

import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.render.Color;
import com.illuminai.vision.backend.render.Intersection;
import com.illuminai.vision.backend.render.Ray;
import com.illuminai.vision.backend.scene.material.DiffuseMaterial;
import com.illuminai.vision.backend.scene.material.Material;

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
    public Sphere(Vector3d position) {
        this(position, 1.0, new DiffuseMaterial(0.18, new Color(0, 0, 0)));
    }

    /**
     * Constructs and initializes a Sphere from the specified position, radius and color.
     *
     * @param position the position
     * @param radius   the radius
     */
    public Sphere(Vector3d position, double radius, Material material) {
        super(position, material);
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
        double b = 2.0 * ray.getDirection().dot(ray.getOrigin().subtract(position));
        double c = ray.getOrigin().subtract(position)
                .dot(ray.getOrigin().subtract(position)) - radius * radius;

        double discriminant = b * b - 4.0 * a * c;

        if (discriminant < 0) {
            return null;
        } else {
            double time1 = (-b + Math.sqrt(discriminant)) / 2.0 * a;
            double time2 = (-b - Math.sqrt(discriminant)) / 2.0 * a;

            double time;
            if (time1 < 0 && time2 < 0) {
                return null;
            } else if (time1 < 0 || time2 < 0) {
                time = Math.max(time1, time2);
            } else {
                time = Math.min(time1, time2);
            }

            Vector3d normal = ray.getPointOnRay(time).subtract(position);
            return new Intersection(ray, this, normal, time);
        }
    }

    /**
     * Returns true if the sphere contains the point
     *
     * @param point the point
     * @return true if the sphere contains the point
     */
    @Override
    public boolean contains(Vector3d point) {
        return point.subtract(position).length() < radius;
    }

    @Override
    public void prepare() {
        //Nothing to do
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