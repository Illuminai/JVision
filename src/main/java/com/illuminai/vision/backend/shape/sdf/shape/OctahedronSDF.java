package com.illuminai.vision.backend.shape.sdf.shape;

import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.shape.sdf.SignedDistanceField;

public class OctahedronSDF extends SignedDistanceField {


    @Override
    public double getDistance(Vector3d point) {
        double s = .3; //Size

        Vector3d p = point.abs();
        double m = p.getX() + p.getY() + p.getZ() - s;

        Vector3d q;
        if (3.0 * p.getX() < m) {
            q = p;
        } else if (3.0 * p.getY() < m) {
            q = new Vector3d(p.getY(), p.getZ(), p.getX());
        } else if (3.0 * p.getZ() < m) {
            q = new Vector3d(p.getZ(), p.getX(), p.getY());
        } else {
            return m * 0.57735027;
        }

        double k = Math.max(Math.min(s, 0.5 * (q.getZ() - q.getY() + s)), 0.0);

        return new Vector3d(q.getX(), q.getY() - s + k, q.getZ() - k).length();
    }
}
