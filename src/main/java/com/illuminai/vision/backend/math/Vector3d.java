package com.illuminai.vision.backend.math;

/**
 * A 3 dimensional vector
 */
public class Vector3d {

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
     * Constructs and initializes a Vector3d from the specified xyz coordinates.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @param z the z coordinate
     */
    public Vector3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3d(Vector3d other) {
        this(other.getX(), other.getY(), other.getZ());
    }

    /**
     * Constructs and initializes a Vector3d from the distance of two points
     *
     * @param from the start point
     * @param to   the end point
     */
    public Vector3d(Point3d from, Point3d to) {
        this(to.getX() - from.getX(), to.getY() - from.getY(), to.getZ() - from.getZ());
    }


    /**
     * Calculates the sum of itself and the second vector
     *
     * @param vector the second vector
     * @return the sum of itself and the second vector
     */
    public Vector3d add(Vector3d vector) {
        return new Vector3d(x + vector.x, y + vector.y, z + vector.z);
    }

    /**
     * Calculates the difference of itself and the second vector
     *
     * @param vector the second vector
     * @return the difference of itself and the second vector
     */
    public Vector3d subtract(Vector3d vector) {
        return new Vector3d(x - vector.x, y - vector.y, z - vector.z);
    }

    /**
     * Calculates the scalar multiplication of itself and the scalar value
     *
     * @param scalar the scalar value
     * @return the scaled vector
     */
    public Vector3d scale(double scalar) {
        return new Vector3d(x * scalar, y * scalar, z * scalar);
    }

    /**
     * Calculates the dot product of itself and the second vector
     *
     * @param vector the second vector
     * @return the dot product
     */
    public double dot(Vector3d vector) {
        return (x * vector.x) + (y * vector.y) + (z * vector.z);
    }

    /**
     * Calculates the cross product of itself and the second vector
     *
     * @param vector the second vector
     * @return the cross product
     */
    public Vector3d cross(Vector3d vector) {
        return new Vector3d(y * vector.z - z * vector.y, z * vector.x - x * vector.z, x * vector.y - y * vector.x);
    }

    /**
     * Calculates the length of itself
     *
     * @return the length
     */
    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    /**
     * Calculates the normalized vector
     *
     * @return the normalized vector
     */
    public Vector3d normalize() {
        return scale(1 / length());
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

    /**
     * Returns a string that contains the values of the vector
     *
     * @return the String representation
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }

}
