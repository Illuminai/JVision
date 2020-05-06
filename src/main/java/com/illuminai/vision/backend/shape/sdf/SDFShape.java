package com.illuminai.vision.backend.shape.sdf;

import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.render.Intersection;
import com.illuminai.vision.backend.render.Ray;
import com.illuminai.vision.backend.shape.Shape;

public class SDFShape extends Shape {

    private SignedDistanceField sdf;

    public SDFShape(Vector3d position, SignedDistanceField sdf) {
        super(position);
        this.sdf = sdf;
    }

    @Override
    public Intersection getIntersection(Ray ray) {
        double time = 0;
        int maxDist = 100;

        while (time < maxDist) {
            Vector3d point = ray.getPointOnRay(time);
            double distance = getDistance(point);

            if (distance <= 10e-6 * time) {
                Vector3d normal = estimateNormal(point);
                return new Intersection(ray, this, normal, time);
            }
            time += getDistance(point);
        }
        return null;
    }

    @Override
    public void prepare() {
    }

    private double getDistance(Vector3d point) {
        return sdf.getDistance(point.subtract(position));
    }

    private Vector3d estimateNormal(Vector3d point) {
        return new Vector3d(
                getDistance(point.add(new Vector3d(10e-6, 0, 0))) - getDistance(point.subtract(new Vector3d(10e-6, 0, 0))),
                getDistance(point.add(new Vector3d(0, 10e-6, 0))) - getDistance(point.subtract(new Vector3d(0, 10e-6, 0))),
                getDistance(point.add(new Vector3d(0, 0, 10e-6))) - getDistance(point.subtract(new Vector3d(0, 0, 10e-6)))
        ).normalize();
    }

}
