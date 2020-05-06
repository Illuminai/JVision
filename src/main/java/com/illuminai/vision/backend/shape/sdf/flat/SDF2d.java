package com.illuminai.vision.backend.shape.sdf.flat;

import com.illuminai.vision.backend.math.Vector2d;

public abstract class SDF2d {

    public abstract double getDistance(Vector2d point);
}
