package com.illuminai.vision.backend.sdf;

import com.illuminai.vision.backend.math.Vector3d;

public class SDFMesh {

    private SignedDistanceField sdf;

    public SDFMesh(SignedDistanceField sdf) {
        this.sdf = sdf;
    }

    public double getDistance(Vector3d point) {
        return sdf.getDistance(point);
    }

}
