package com.illuminai.vision.backend.shape;

import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.render.Intersection;
import com.illuminai.vision.backend.render.Ray;

import java.util.concurrent.atomic.AtomicLong;

/**
 * An abstract shape
 */
public abstract class Shape {

    private static final AtomicLong counter = new AtomicLong(0);
    private long id;

    /**
     * the position
     */
    protected Vector3d position;

    /**
     * Constructs and initializes a Shape from the specified position and color.
     *
     * @param position the position
     */
    public Shape(Vector3d position) {
        this.position = position;
        this.id = counter.addAndGet(1);
    }

    /**
     * Calculates the intersection between the shape and the ray
     *
     * @param ray the ray
     * @return the intersection
     */
    public abstract Intersection getIntersection(Ray ray);

    /** Prepares the shape to be rendered. For example, the normal of a triangle could be recalculated after e vertex was changed*/
    public abstract void prepare();

    /**
     * @param position the position
     */
    public void setPosition(Vector3d position) {
        this.position = position;
    }

    /**
     * @return the position
     */
    public Vector3d getPosition() {
        return position;
    }

    public long getId() {
        return id;
    }
}