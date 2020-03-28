package com.illuminai.vision.backend.sdf.alteration;

import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.sdf.SignedDistanceField;

public class IntersectionSDF extends SignedDistanceField {

    private SignedDistanceField sdf1;
    private SignedDistanceField sdf2;

    public IntersectionSDF(SignedDistanceField sdf1, SignedDistanceField sdf2) {
        this.sdf1 = sdf1;
        this.sdf2 = sdf2;
    }

    @Override
    public double getDistance(Vector3d point) {
        return Math.max(sdf1.getDistance(point), sdf2.getDistance(point));
    }

}
