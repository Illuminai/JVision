package com.illuminai.vision.backend.shape.implicit;

import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.render.Intersection;
import com.illuminai.vision.backend.render.Ray;
import com.illuminai.vision.backend.shape.Shape;

public class Plane extends Shape {

    private Vector3d normal;

    public Plane(Vector3d position, Vector3d normal) {
        super(position);
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

}
