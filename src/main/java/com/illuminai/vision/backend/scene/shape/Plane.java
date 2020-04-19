package com.illuminai.vision.backend.scene.shape;

import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.render.Intersection;
import com.illuminai.vision.backend.render.Ray;
import com.illuminai.vision.backend.scene.material.Material;

public class Plane extends Shape {

    private Vector3d normal;

    public Plane(Vector3d position, Material material, Vector3d normal) {
        super(position, material);
        this.normal = normal.normalize();
    }

    @Override
    public Intersection getIntersection(Ray ray) {
        double denominator = normal.scale(-1.0).dot(ray.getDirection());
        if (denominator > 1e-6) { // epsilon bias
            Vector3d pointTo = position.subtract(ray.getOrigin());
            double time = pointTo.dot(normal.scale(-1.0)) / denominator;
            if (time >= 0) {
                return new Intersection(ray, this, normal, time);
            }
        }
        return null;
    }

    public Vector3d getNormal() {
        return normal;
    }

    public void setNormal(Vector3d normal) {
        this.normal = normal.normalize();
    }

    @Override
    public void prepare() {
        //Nothing to prepare
    }

    @Override
    public boolean contains(Vector3d point) {
        assert false;
        return false;
    }
}
