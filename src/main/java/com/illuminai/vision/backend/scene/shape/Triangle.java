package com.illuminai.vision.backend.scene.shape;

import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.render.Intersection;
import com.illuminai.vision.backend.render.Ray;
import com.illuminai.vision.backend.scene.material.Material;

public class Triangle extends Shape {
    private Vector3d p1, p2;

    /** Creates a triangle containing these 3 points<br>
     * The points should be given in counterclockwise order (for the normal)*/
    public Triangle(Vector3d p0, Vector3d p1, Vector3d p2, Material material) {
        super(p0, material);
        this.p1 = p1;
        this.p2 = p2;
    }

    public void prepare() {
        //
    }

    @Override
    public Intersection getIntersection(Ray ray) {
        //See wikipedia
        //https://en.wikipedia.org/wiki/Line%E2%80%93plane_intersection 20.04.2002
        Vector3d normal = p1.subtract(getPosition()).cross(p2.subtract(getPosition()));
        double divisor = ray.getDirection().scale(-1).dot(p1.subtract(getPosition()).cross(p2.subtract(getPosition())));

        double t = p1.subtract(getPosition()).cross(p2.subtract(getPosition())).
                dot(ray.getOrigin().subtract(getPosition())) / divisor;

        double u = (p2.subtract(getPosition()).cross(ray.getDirection().scale(-1))).
                dot(ray.getOrigin().subtract(getPosition()))/divisor;
        double v = (ray.getDirection().scale(-1).cross(p1.subtract(getPosition()))).
                dot(ray.getOrigin().subtract(getPosition()))/divisor;
        if(t >= 0 && v >= 0 && u >= 0 && u + v <= 1) {
            if(normal.dot(ray.getDirection()) > 0) {
                normal = normal.scale(-1);
            }
            return new Intersection(ray, this, normal, t);
        }

        return null;
    }

    @Override
    public boolean contains(Vector3d point) {
        assert false;
        return false;
    }

    public Vector3d getP1() {
        return p1;
    }

    public void setP1(Vector3d p1) {
        this.p1 = p1;
    }

    public Vector3d getP2() {
        return p2;
    }

    public void setP2(Vector3d p2) {
        this.p2 = p2;
    }
}
