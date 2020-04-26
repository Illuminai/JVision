package com.illuminai.vision.backend.scene;

import com.illuminai.vision.backend.math.Matrix3x3;
import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.render.Ray;

public class Camera {

    private Vector3d position;
    private Vector3d rotation;
    private Matrix3x3 rotationMatrix;

    public Camera(Vector3d position, Vector3d rotation) {
        this.position = position;
        this.rotation = rotation;
        this.rotationMatrix = Matrix3x3.createRotationMatrix(rotation);
    }

    public Ray getRay(double u, double v) {
        Vector3d direction = rotationMatrix.transformed(new Vector3d(10, 0, 0)
                .add(new Vector3d(0, 4, 0).scale(u)))
                .add(new Vector3d(0, 0, -3).scale(v));
        return new Ray(position, direction);
    }

    public Vector3d getDirection() {
        return Matrix3x3.createRotationMatrix(rotation.getX(), rotation.getY(), rotation.getZ()).transformed(new Vector3d(1, 0, 0));
    }

    public Vector3d getPosition() {
        return position;
    }

    public void setPosition(Vector3d position) {
        this.position = position;
    }

    public Vector3d getRotation() {
        return rotation;
    }

    public void setRotation(Vector3d rotation) {
        this.rotation = rotation;
    }

    /** Adjusts the camera to the rotation*/
    public void prepare() {
        rotationMatrix = Matrix3x3.createRotationMatrix(rotation);
    }

    // Hard coded
    public void moveForward(double amount) {
        position.set(position.add(getDirection().scale(amount)));
    }
    public void moveSideward(double amount) {
        Vector3d p = new Vector3d(getDirection());
        p.setZ(0);
        position.set(position.add(Matrix3x3.createRotationMatrix('z', Math.PI / 2).transformed(p.scale(amount))));
    }
    public void moveUpwards(double amount) {
        position.set(position.add(new Vector3d(0, 0, 1).scale(amount)));
    }


}
