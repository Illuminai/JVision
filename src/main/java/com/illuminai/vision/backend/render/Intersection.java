package com.illuminai.vision.backend.render;

import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.scene.shape.Mesh;

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
    private Mesh mesh;

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
     * @param mesh the mesh
     * @param time the time
     */
    public Intersection(Ray ray, Mesh mesh, Vector3d normal, double time) {
        this.ray = ray;
        this.mesh = mesh;
        this.normal = normal.normalize();
        this.time = time;
        point = ray.getPointOnRay(time);
    }

    /**
     * @return the reflection ray
     */
    public Ray getReflectionRay() {
        Vector3d incoming = ray.getDirection().normalize();
        return new Ray(point, incoming.subtract(normal.scale(2.0 * incoming.dot(normal))));
    }

    /**
     * @return the ray
     */
    public Ray getRay() {
        return ray;
    }

    /**
     * @return the mesh
     */
    public Mesh getMesh() {
        return mesh;
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

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
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