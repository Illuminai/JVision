package com.illuminai.vision.backend.sdf;

import com.illuminai.vision.backend.math.Vector3d;

public abstract class SignedDistanceField {

    public abstract double getDistance(Vector3d point);
}
