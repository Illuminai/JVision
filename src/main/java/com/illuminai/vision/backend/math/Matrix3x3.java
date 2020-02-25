package com.illuminai.vision.backend.math;

public class Matrix3x3 {
    private final double[] content;

    public Matrix3x3(double[] content) {
        this.content = content.clone();
    }

    public double get(int row, int column) {
        return content[column + 3 * row];
    }

    public void set(int row, int column, double val) {
        this.content[column + 3 * row] = val;
    }

    public Vector3d transformed(Vector3d other) {
        return new Vector3d(this.get(0,0) * other.getX() + this.get(1,0) * other.getY() + this.get(2,0) * other.getZ(),
                this.get(0,1) * other.getX() + this.get(1,1) * other.getY() + this.get(2,1) * other.getZ(),
                this.get(0,2) * other.getX() + this.get(1,2) * other.getY() + this.get(2,2) * other.getZ());
    }
    
    public static Matrix3x3 createRotationMatrix(char axis, double angle) {
        switch (axis) {
            case 'x':
                return new Matrix3x3(new double[] {
                    1,0,0,
                    0,Math.cos(angle),-Math.cos(angle),
                    0, Math.sin(angle),Math.cos(angle) });
            case 'y':
                return new Matrix3x3(new double[] {
                    Math.cos(angle), 0, Math.sin(angle),
                    0, 1, 0,
                    -Math.sin(angle), 0, Math.cos(angle)
            });
            case 'z':
                return new Matrix3x3(new double[]  {
                        Math.cos(angle),-Math.sin(angle), 0,
                        Math.sin(angle), Math.cos(angle), 0,
                    0, 0, 1
            });
            default:
                throw new RuntimeException("Invalid axis");
        }
    }
    
    /** Only for debug */
    public void print() {
        for(int row = 0; row < 3; row ++) {
            for(int column = 0; column < 3; column++) {
                System.out.print(get(row, column) + " ");
            }
            System.out.println();
        }
    }
}
