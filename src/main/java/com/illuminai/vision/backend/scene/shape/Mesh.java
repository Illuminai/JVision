package com.illuminai.vision.backend.scene.shape;

import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.sdf.SignedDistanceField;

public class Mesh {

    private SignedDistanceField sdf;

    public Mesh(SignedDistanceField sdf) {
        this.sdf = sdf;
    }

    public double getDistance(Vector3d point) {
        return sdf.getDistance(point);
    }

}
