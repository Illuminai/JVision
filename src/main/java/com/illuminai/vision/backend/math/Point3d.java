package com.illuminai.vision.backend.math;

/**
 * A 3 dimensional point
 */
public class Point3d {

    /**
     * the x coordinate
     */
    private double x;

    /**
     * the y coordinate
     */
    private double y;

    /**
     * the z coordinate
     */
    private double z;

    /**
     * Constructs and initializes a Point3d from the specified xyz coordinates.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @param z the z coordinate
     */
    public Point3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Calculates the sum of itself and the second vector
     *
     * @param vector the second vector
     * @return the sum of itself and the second vector
     */
    public Point3d add(Vector vector) {
        return new Point3d(x + vector.getX(), y + vector.getY(), z + vector.getZ());
    }

    /**
     * Calculates the difference of itself and the second vector
     *
     * @param vector the second vector
     * @return the difference of itself and the second vector
     */
    public Point3d subtract(Vector vector) {
        return new Point3d(x - vector.getX(), y - vector.getY(), z - vector.getZ());
    }

    /**
     * Converts itself to a vector
     *
     * @return the vector with the xyz coordinates of itself
     */
    public Vector toVector() {
        return new Vector(x, y, z);
    }

    /**
     * @return the x coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * @param x the x coordinate
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * @return the y coordinate
     */
    public double getY() {
        return y;
    }

    /**
     * @param y the y coordinate
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * @return the z coordinate
     */
    public double getZ() {
        return z;
    }

    /**
     * @param z the z coordinate
     */
    public void setZ(double z) {
        this.z = z;
    }
}