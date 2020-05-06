package com.illuminai.vision.backend.acceleration;

import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.render.Ray;

public class AABB {

    private Vector3d minimum;
    private Vector3d maximum;

    public AABB(Vector3d minimum, Vector3d maximum) {
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public boolean hasIntersection(Ray ray) {
        double tmin = 0;
        double tmax = 100;
        for (int i = 0; i < 3; i++) {
            double invD = 1.0 / ray.getDirection().get(i);
            double t0 = (minimum.get(i) - ray.getOrigin().get(i)) * invD;
            double t1 = (maximum.get(i) - ray.getOrigin().get(i)) * invD;
            if (invD < 0.0) {
                double temp = t1;
                t1 = t0;
                t0 = temp;
            }

            tmin = Math.max(t0, tmin);
            tmax = Math.min(t1, tmax);

            if (tmax <= tmin) {
                return false;
            }
        }
        return true;
    }
}
