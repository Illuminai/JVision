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


    public Matrix3x3 multiple(Matrix3x3 o) {
        //DO NOT QUESTION THE CODE!!!
        return new Matrix3x3(new double[]{
                this.get(0,0)* o.get(0,0) + this.get(0,1)* o.get(1,0) + this.get(0,2)* o.get(2,0),
                this.get(0,0) * o.get(0,1) + this.get(0,1) * o.get(1,1) + this.get(0,2) * o.get(2,1),
                this.get(0,0) * o.get(0,2) + this.get(0,1) * o.get(1,2) + this.get(0,2) * o.get(2,2),

                this.get(1,0)* o.get(0,0) + this.get(1,1)* o.get(1,0) + this.get(1,2)* o.get(2,0),
                this.get(1,0) * o.get(0,1) + this.get(1,1) * o.get(1,1) + this.get(1,2) * o.get(2,1),
                this.get(1,0) * o.get(0,2) + this.get(1,1) * o.get(1,2) + this.get(1,2) * o.get(2,2),

                this.get(2,0)* o.get(0,0) + this.get(2,1)* o.get(1,0) + this.get(2,2)* o.get(2,0),
                this.get(2,0) * o.get(0,1) + this.get(2,1) * o.get(1,1) + this.get(2,2) * o.get(2,1),
                this.get(2,0) * o.get(0,2) + this.get(2,1) * o.get(1,2) + this.get(2,2) * o.get(2,2)

        });
    }

    /** Creates a rotation matrix, which will be able to rotate a {@link Vector3d} on the axis specified by {@code axis} and by
     * {@code angle} radians
     * @param axis may be 'x','y' or 'z'. Other characters throw exceptions
     * @param angle the angle of rotation in radians*/
    public static Matrix3x3 createRotationMatrix(char axis, double angle) {
        switch (axis) {
            case 'x':
                return new Matrix3x3(new double[] {
                    1,0,0,
                    0,Math.cos(angle),-Math.sin(angle),
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

    public static Matrix3x3 multiply(Matrix3x3 one, Matrix3x3 two) {
        return one.multiple(two);
    }

    public static Matrix3x3 createRotationMatrix(double x, double y, double z) {
        return multiply(multiply(createRotationMatrix('x',x), createRotationMatrix('y',y)),createRotationMatrix('z', z));
    }

    /**This method returns a value that reverses the effect of the matrix created by {@link #createRotationMatrix(double, double, double)} <br>
     *
     * This method returns a matrix, that when multiplied with a matrix returned by {@link #createRotationMatrix(double, double, double)} yields a matrix that is diagonally filled with ones.<br>
     * Example:
     * <code>
     *     double alpha = ..., beta = ..., gamma = ...; <br>
     *     Matrix3x3 a = createRotationMatrix(alpha, beta, gamma);<br>
     *     Matrix3x3 b = createReverseRotationMatrix(-alpha, -beta, -gamma);<br>
     *     Matrix3x3 c = a.multiply(b); <br>
     * </code>
     * In this example, regardless of the values of {@code alpha}, {@code beta} and {@code gamma}, {@code c} will have the value of
     * <table>
     *     <tr>
     *         <td>1</td>
     *         <td>0</td>
     *         <td>0</td>
     *     </tr>
     *     <tr>
     *          <td>0</td>
     *          <td>1</td>
     *          <td>0</td>
     *      </tr>
     *      <tr>
     *          <td>0</td>
     *          <td>0</td>
     *          <td>1</td>
     *      </tr>
     * </table>
     * */
    public static Matrix3x3 createReverseRotationMatrix(double x, double y, double z) {
        return multiply(createRotationMatrix('z',z),multiply(createRotationMatrix('y',y),createRotationMatrix('x',x)));
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
