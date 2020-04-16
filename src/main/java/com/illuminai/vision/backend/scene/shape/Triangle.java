package com.illuminai.vision.backend.scene.shape;

import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.render.Intersection;
import com.illuminai.vision.backend.render.Ray;
import com.illuminai.vision.backend.scene.material.Material;

public class Triangle extends Shape {
    private Vector3d p2, p3;
    private Vector3d normal;

    /** Creates a triangle containing these 3 points*/
    public Triangle(Vector3d p1, Vector3d p2, Vector3d p3, Material material) {
        super(p1, material);
        this.p2 = p2;
        this.p3 = p3;
        normal = p2.subtract(p1).cross(p3.subtract(p1)).normalize();
    }

    @Override
    public void setPosition(Vector3d p1) {
        normal = p2.subtract(p1).cross(p3.subtract(p1)).normalize();
        super.setPosition(position);
    }

    @Override
    public Intersection getIntersection(Ray ray) {
        /*
        * Mathematica code:
        (*Clear["Global`*"];*)
   p1x = 5; p1y = 4; p1z = 3;
p2x = 1; p2y = 4; p2z = 1;
p3x = 1; p3y = 1; p3z = 1;
ox = 3; oy = 3; oz = 0;
dx = 0; dy = 0; dz = 1;

o = {ox, oy, oz};
dr = {dx, dy, dz};
p1 = {p1x, p1y, p1z};
p2 = {p2x, p2y, p2z};
p3 = {p3x, p3y, p3z};

Graphics3D[{Triangle[{p1, p2, p3}], Arrow[{o, o + dr}], Yellow,
  Arrow[{p1, p2}], Green, Arrow[{{p1, p3}}]}]
Solve[o + d*dr == p1 + t*(p2 - p1) + s*(p3 - p1), {d, s, t}]
        * */

        Vector3d p1 = getPosition();
        Vector3d o = ray.getOrigin();
        Vector3d d = ray.getDirection();

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

        double f = o.subtract(p1.add(p2.subtract(p1).scale(t)).add(p3.subtract(p1).scale(s))).length();
/*
        if(s + t <= 1 && s > 0 && t > 0) {
            return new Intersection(ray, this, normal.scale(1), f);
        }
 */
        return new Intersection(ray, this, normal, f);

        //return null;
    }

    @Override
    public boolean contains(Vector3d point) {
        assert false;
        return false;
    }
}
