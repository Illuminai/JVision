package com.illuminai.vision.backend.scene.shape;

import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.render.Intersection;
import com.illuminai.vision.backend.render.Ray;
import com.illuminai.vision.backend.scene.material.Material;

public class Triangle extends Plane {
    private Vector3d p2, p3;

    /** Creates a triangle containing these 3 points*/
    public Triangle(Vector3d p1, Vector3d p2, Vector3d p3, Material material) {
        super(p1, material, p2.subtract(p1).cross(p3.subtract(p1)).scale(-1).normalize());
        this.p2 = p2;
        this.p3 = p3;
    }

    @Override
    public Intersection getIntersection(Ray ray) {
        //https://www.scratchapixel.com/lessons/3d-basic-rendering/ray-tracing-rendering-a-triangle/ray-triangle-intersection-geometric-solution
        Intersection t = super.getIntersection(ray);

        if(t == null) {
            return null;
        }

        Vector3d p = t.getPoint();
        Vector3d e1 = p2.subtract(getPosition());
        if(t.getNormal().dot(e1.cross(p.subtract(getPosition()))) > 0) {
            return null;
        }
        Vector3d e2 = p3.subtract(p2);
        if(t.getNormal().dot(e2.cross(p.subtract(p2))) > 0) {
            return null;
        }
        Vector3d e3 = getPosition().subtract(p3);
        if(t.getNormal().dot(e3.cross(p.subtract(p3))) > 0) {
            return null;
        }

        return t;
    }

    @Override
    public boolean contains(Vector3d point) {
        assert false;
        return false;
    }
}
