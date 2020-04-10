package com.illuminai.vision.backend.scene.shape;

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
     * the color
     */
    protected int color;

    /**
     * the position
     */
    protected Vector3d position;

    /**
     * Constructs and initializes a Shape from the specified position and color.
     *
     * @param position the position
     * @param color the color
     */
    public Shape(Vector3d position, int color) {
        this.position = position;
        this.color = color;
        this.id = counter.addAndGet(1);
    }

    /**
     * Calculates the intersection between the shape and the ray
     *
     * @param ray the ray
     * @return the intersection
     */
    public abstract Intersection getIntersection(Ray ray);

    /**
     * Returns true if the shape contains the point
     *
     * @param point the point
     * @return true if the shape contains the point
     */
    public abstract boolean contains(Vector3d point);

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

    /**
     * @return the color
     */
    public int getColor() {
        return color;
    }

    public long getId() {
        return id;
    }
}