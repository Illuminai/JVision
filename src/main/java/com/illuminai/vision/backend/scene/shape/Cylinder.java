package com.illuminai.vision.backend.scene.shape;

import com.illuminai.vision.backend.math.Point3d;
import com.illuminai.vision.backend.math.Vector;
import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.render.Intersection;
import com.illuminai.vision.backend.render.Ray;

public class Cylinder extends Shape {
    private double length;
    private double radius;
    /**
     * Constructs and initializes a cylinder from the specified position and color.
     *
     * @param position the position
     * @param color    the color
     */
    public Cylinder(Point3d position, double radius, double length, int color) {
        super(position, color);
        this.radius = radius;
        this.length = length;
    }


    @Override
    public Intersection getIntersection(Ray ray) {
        Intersection cy = getIntersectionWithCylinder(ray);
        Intersection pl = getIntersectionWithPlanes(ray);

        if(pl == null) {
            return cy;
        } else if(cy != null)  {
            return cy.getTime() < pl.getTime() ? cy : pl;
        }
        return pl;
    }

    private Intersection getIntersectionWithCylinder(Ray ray) {
        Point3d o = ray.getOrigin();
        Vector3d d = ray.getDirection();
        double radius = this.radius * this.radius;
        double discriminant =  -d.getZ() * d.getZ() * o.getY() * o.getY() +
                2 * d.getY() * d.getZ() * o.getY() * o.getZ() -
                d.getY() * d.getY() * o.getZ() * o.getZ()
                + d.getY() * d.getY() * radius +
                d.getZ() * d.getZ() * radius;

        if(discriminant < 0) {
            return  null;
        }
        if(d.getY() * d.getY() + d.getZ() * d.getZ() == 0) { //Both time1 and time2 will be NaN
            return null;
        }

        discriminant = Math.sqrt(discriminant);
        double time1 = (- d.getY() * o.getY() - o.getZ() * d.getZ() + discriminant) /
                (d.getY() * d.getY() + d.getZ() * d.getZ());
        double time2 = (- d.getY() * o.getY() - o.getZ() * d.getZ() - discriminant) /
                (d.getY() * d.getY() + d.getZ() * d.getZ());

        double time;

        if(time1 < 0 && time2 < 0) {
            return null;
        } else if (time1 < 0 ^ time2 < 0) {
            time = Math.max(time1, time2);
        } else {
            time = Math.min(time1, time2);
        }

        Point3d normal = new Point3d(ray.getPointOnRay(time));
        if(normal.getX() > length || normal.getX() < 0) {
            return null;
        }
        normal.setX(0);

        return new Intersection(ray, this, normal.toVector(), time);
    }

    private Intersection getIntersectionWithPlanes(Ray ray) {
        Point3d o = ray.getOrigin();
        Vector3d d = ray.getDirection();
        if(d.getX() == 0) {
            return null;
        }
        double time1 = -o.getX() / d.getX();
        double time2 = (length -o.getX()) / d.getX();
        double time;

        if(time1 < 0 && time2 < 0) {
            return null;
        } else if (time1 < 0 ^ time2 < 0) {
            time = Math.max(time1, time2);
        } else {
            time = Math.min(time1, time2);
        }

        Vector3d p = ray.getPointOnRay(time).toVector();
        p.setX(0);
        if(p.length() > radius) {
            return null;
        }

        Vector3d normal = new Vector3d(time == time1 ? -1: 1,0,0);

        return new Intersection(ray,this,normal,time);
    }

    @Override
    public boolean contains(Point3d point) {
        throw new RuntimeException("Not implemented yet!");
    }
}
