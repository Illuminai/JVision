package com.illuminai.vision.backend.scene.shape;

import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.sdf.SignedDistanceField;

import java.util.concurrent.atomic.AtomicLong;

public class Mesh {
    private static final AtomicLong counter = new AtomicLong(0);
    private long id;

    private SignedDistanceField sdf;

    public Mesh(SignedDistanceField sdf) {
        this.sdf = sdf;
        this.id = counter.addAndGet(1);
    }

    public double getDistance(Vector3d point) {
        return sdf.getDistance(point);
    }

    public long getId() {
        return id;
    }
}
