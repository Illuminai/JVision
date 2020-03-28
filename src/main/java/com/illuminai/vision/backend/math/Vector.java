package com.illuminai.vision.backend.math;

import java.util.Arrays;

/**
 * A multidimensional vector
 * This implementation is much slower than Vector3d
 */
public class Vector {

    private final double[] content;

    public Vector(int size) {
        this.content = new double[3];
    }

    public Vector(Vector o) {
        this.content = o.content.clone();
    }

    /**
     * Constructs and initializes a Vector from the specified xyz coordinates.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @param z the z coordinate
     */
    public Vector(double x, double y, double z) {
        this(3);
        setX(x);
        setY(y);
        setZ(z);
    }

    /**
     * Calculates the sum of itself and the second vector
     *
     * @param vector the second vector
     * @return the sum of itself and the second vector
     */
    public Vector add(Vector vector) {
        Vector v = new Vector(this);
        for (int i = 0; i < this.content.length; i++) {
            v.content[i] += vector.content[i];
        }
        return v;
    }

    /**
     * Calculates the difference of itself and the second vector
     *
     * @param vector the second vector
     * @return the difference of itself and the second vector
     */
    public Vector subtract(Vector vector) {
        Vector v = new Vector(this);
        for (int i = 0; i < this.content.length; i++) {
            v.content[i] -= vector.content[i];
        }
        return v;
    }

    /**
     * Calculates the scalar multiplication of itself and the scalar value
     *
     * @param scalar the scalar value
     * @return the scaled vector
     */
    public Vector scale(double scalar) {
        Vector t = new Vector(this);
        for (int i = 0; i < this.content.length; i++) {
            t.content[i] *= scalar;
        }
        return t;
    }

    /**
     * Calculates the dot product of itself and the second vector
     *
     * @param vector the second vector
     * @return the dot product
     */
    public double dot(Vector vector) {
        double d = 0;
        for (int i = 0; i < content.length; i++) {
            d += vector.content[i] * this.content[i];
        }
        return d;
    }

    /**
     * Calculates the cross product of itself and the second vector
     *
     * @param vector the second vector
     * @return the cross product
     */
    public Vector cross(Vector vector) {
        return new Vector(getY() * vector.getZ() - getZ() * vector.getY(), getZ() * vector.getX() - getX() * vector.getZ(), getX() * vector.getY() - getY() * vector.getX());
    }

    /**
     * Calculates the length of itself
     *
     * @return the length
     */
    public double length() {
        double d = 0;
        for (double f : content) {
            d += f * f;
        }
        return Math.sqrt(d);
    }

    /**
     * Calculates the normalized vector. If the length of the vector is zero, a vector filled with 0 is returned
     *
     * @return the normalized vector
     */
    public Vector normalize() {
        if (this.length() == 0) {
            return new Vector(this);
        }
        return scale(1 / length());
    }

    /**
     * @return the x coordinate
     */
    public double getX() {
        return content[0];
    }

    /**
     * @param x the x coordinate
     */
    public void setX(double x) {
        this.content[0] = x;
    }

    /**
     * @return the y coordinate
     */
    public double getY() {
        return content[1];
    }

    /**
     * @param y the y coordinate
     */
    public void setY(double y) {
        this.content[1] = y;
    }

    /**
     * @return the z coordinate
     */
    public double getZ() {
        return content[2];
    }

    /**
     * @param z the z coordinate
     */
    public void setZ(double z) {
        this.content[2] = z;
    }

    /**
     * Returns a string that contains the values of the vector
     *
     * @return the String representation
     */
    @Override
    public String toString() {
        return Arrays.toString(this.content);
    }

}
