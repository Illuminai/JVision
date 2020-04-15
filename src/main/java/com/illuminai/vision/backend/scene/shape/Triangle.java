package com.illuminai.vision.backend.scene.shape;

import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.render.Intersection;
import com.illuminai.vision.backend.render.Ray;
import com.illuminai.vision.backend.scene.material.Material;

public class Triangle extends Shape {
    private Vector3d p2, p3;
    private Vector3d normal;

    /**0: plane is parallel to yz-Plane
     * 1: plane is parallel to xz-Plane
     * 2: plane is parallel to xy-Plane*/
    public byte mode;
    /** Creates a triangle containing these 3 points*/
    public Triangle(Vector3d p1, Vector3d p2, Vector3d p3, Material material) {
        super(p1, material);
        this.p2 = p2;
        this.p3 = p3;
        normal = p2.subtract(p1).cross(p3.subtract(p1)).normalize();
        mode = (byte) (normal.getX() > 0.01 ? 0 : (normal.getY() > 0.01 ? 1 : 2));
    }

    @Override
    public Intersection getIntersection(Ray ray) {
        /*
        double frac = ray.getDirection().dot(normal);
        if(frac < 0.001) {
            return null;
        }
        double factor = (getPosition().subtract(ray.getOrigin()).dot(normal)) / frac;
        Vector3d q = ray.getPointOnRay(factor);
        Vector3d k = p2.subtract(getPosition());
        Vector3d m = p3.subtract(getPosition());
        Vector3d p = getPosition();
        double s, t, d;
        switch(mode) {
            case 0:
                d = (k.getY() * m.getZ() - k.getZ() * m.getY());
                t = - (k.getZ() * p.getZ() - k.getZ() * p.getY() - k.getY() * q.getZ() - k.getZ() * q.getY()) /
                        d;
                s = - (m.getY() * p.getZ() - m.getZ() * p.getY() - m.getY() * q.getZ() + m.getZ() * q.getY()) /
                        -d;
                break;
            case 1:
                d = (k.getX() * m.getZ() - k.getZ() * m.getX());
                t = - (k.getZ() * p.getZ() - k.getZ() * p.getX() - k.getX() * q.getZ() - k.getZ() * q.getX()) /
                        d;
                s = - (m.getX() * p.getZ() - m.getZ() * p.getX() - m.getX() * q.getZ() + m.getZ() * q.getX()) /
                        -d;
                break;
            case 2:
                d = (k.getX() * m.getY() - k.getY() * m.getX());
                t = - (k.getY() * p.getY() - k.getY() * p.getX() - k.getX() * q.getY() - k.getY() * q.getX()) /
                        d;
                s = - (m.getX() * p.getY() - m.getY() * p.getX() - m.getX() * q.getY() + m.getY() * q.getX()) /
                        -d;
                break;
            default:
                throw new RuntimeException("Illegal mode: " + mode);
        }
        
        if(s + t <= 1 && s > 0 && t > 0) {
            return new Intersection(ray, this, Math.acos(frac) >= 0 ? normal.scale(-1) : normal, factor);
            return new Intersection(ray, this, ray.getDirection().scale(-1), factor);

        }
        return null;
         */
        Vector3d p1 = getPosition();
        Vector3d o = ray.getOrigin();
        Vector3d d = ray.getDirection();
        double f = -((((o.getZ() - p1.getZ()) * (p1.getX() - p2.getX()) - (o.getX() - p1.getX()) * (p1.getZ() - p2.getZ())) * (-p1.getY()* p2.getX() +
                p1.getX() *p2.getY() + p1.getY() *p3.getX() - p2.getY()* p3.getX() - p1.getX()* p3.getY() +
                p2.getX() *p3.getY()) - ((o.getY() - p1.getY())* (p1.getX() - p2.getX()) - (o.getX() - p1.getX()) *(p1.getY() -
                p2.getY())) *(-(p1.getZ() - p2.getZ())* (p1.getX() - p3.getX()) + (p1.getX() - p2.getX())* (p1.getZ() -
                p3.getZ())))/((d.getZ()* (p1.getX() - p2.getX()) - d.getX() * (p1.getZ() - p2.getZ())) *(-p1.getY()* p2.getX() +
                p1.getX() * p2.getY() + p1.getY() *  p3.getX() - p2.getY() *p3.getX() - p1.getX() *p3.getY() +
                p2.getX()* p3.getY()) - (d.getY() *(p1.getX() - p2.getX()) -
                d.getX()* (p1.getY() - p2.getY())) *(-(p1.getZ() - p2.getZ()) * (p1.getX() - p3.getX()) + (p1.getX() -
                p2.getX())* (p1.getZ() - p3.getZ()))));

        double s = -((d.getZ() *o.getY() *p1.getX() - d.getY()* o.getZ() *p1.getX() -
                d.getZ()* o.getX() * p1.getY() + d.getX()* o.getZ()* p1.getY() + d.getY()* o.getX()* p1.getZ() - d.getX()* o.getY()* p1.getZ() - d.getZ()* o.getY()* p2.getX() +
                d.getY()* o.getZ() * p2.getX() + d.getZ()* p1.getY()* p2.getX() - d.getY()* p1.getZ()* p2.getX() + d.getZ()* o.getX()* p2.getY() - d.getX()* o.getZ()* p2.getY() -
                d.getZ()* p1.getX()* p2.getY() + d.getX()* p1.getZ()* p2.getY() - d.getY()* o.getX()* p2.getZ() + d.getX()* o.getY()* p2.getZ() + d.getY()* p1.getX()* p2.getZ() -
                d.getX()* p1.getY()* p2.getZ())/(-d.getZ()* p1.getY()* p2.getX() + d.getY()* p1.getZ()* p2.getX() + d.getZ()* p1.getX()* p2.getY() -
                d.getX()* p1.getZ()* p2.getY() - d.getY()* p1.getX()* p2.getZ() + d.getX()* p1.getY()* p2.getZ() + d.getZ()* p1.getY()* p3.getX() - d.getY()* p1.getZ()* p3.getX() -
                d.getZ()* p2.getY()* p3.getX() + d.getY()* p2.getZ()* p3.getX() - d.getZ()* p1.getX()* p3.getY() + d.getX()* p1.getZ()* p3.getY() +
                d.getZ()* p2.getX()* p3.getY() - d.getX()* p2.getZ()* p3.getY() + d.getY()* p1.getX()* p3.getZ() - d.getX()* p1.getY()* p3.getZ() - d.getY()* p2.getX()* p3.getZ() +
                d.getX()* p2.getY()* p3.getZ()));

        double t = -((d.getZ()* o.getY()* p1.getX() - d.getY()* o.getZ()* p1.getX() - d.getZ()* o.getX()* p1.getY() +
                d.getX()* o.getZ()* p1.getY() + d.getY()* o.getX()* p1.getZ() - d.getX()* o.getY()* p1.getZ() - d.getZ()* o.getY()* p3.getX() + d.getY()* o.getZ()* p3.getX() +
                d.getZ()* p1.getY()* p3.getX() - d.getY()* p1.getZ()* p3.getX() + d.getZ()* o.getX()* p3.getY() - d.getX()* o.getZ()* p3.getY() - d.getZ()* p1.getX()* p3.getY() +
                d.getX()* p1.getZ()* p3.getY() - d.getY()* o.getX()* p3.getZ() + d.getX()* o.getY()* p3.getZ() + d.getY()* p1.getX()* p3.getZ() -
                d.getX()* p1.getY()* p3.getZ())/(d.getZ()* p1.getY()* p2.getX() - d.getY()* p1.getZ()* p2.getX() - d.getZ()* p1.getX()* p2.getY() + d.getX()* p1.getZ()* p2.getY() +
                d.getY()* p1.getX()* p2.getZ() - d.getX()* p1.getY()* p2.getZ() - d.getZ()* p1.getY()* p3.getX() + d.getY()* p1.getZ()* p3.getX() +
                d.getZ()* p2.getY()* p3.getX() - d.getY()* p2.getZ()* p3.getX() + d.getZ()* p1.getX()* p3.getY() - d.getX()* p1.getZ()* p3.getY() - d.getZ()* p2.getX()* p3.getY() +
                d.getX()* p2.getZ()* p3.getY() - d.getY()* p1.getX()* p3.getZ() + d.getX()* p1.getY()* p3.getZ() + d.getY()* p2.getX()* p3.getZ() -
                d.getX()* p2.getY()* p3.getZ()));

        if(s + t <= 1 && s > 0 && t > 0) {
            return new Intersection(ray, this, ray.getDirection().scale(-1), f);
        }
        return null;
    }

    @Override
    public boolean contains(Vector3d point) {
        return false;
    }
}
