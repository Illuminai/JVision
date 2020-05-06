package com.illuminai.vision.backend.shape.implicit;

import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.render.Intersection;
import com.illuminai.vision.backend.render.Ray;
import com.illuminai.vision.backend.shape.Shape;

public class SimplePolygon extends Shape {
    private Triangle[] faces;

    /** Constructs a simple polygon*/
    public SimplePolygon(Vector3d[] points, int[][] faces) {
        super(points[0]);
        this.faces = new Triangle[faces.length];

        for(int i = 0; i < faces.length; i++) {
            this.faces[i] = new Triangle(points[faces[i][0]], points[faces[i][1]], points[faces[i][2]]);
        }
    }

    @Override
    public Intersection getIntersection(Ray ray) {
        Intersection i = null;
        Intersection c;
        for(Triangle t: faces) {
            if((c = t.getIntersection(ray)) != null) {
                if(i != null) {
                    if(i.getTime() > c.getTime()) {
                        i = c;
                    }
                } else {
                    i = c;
                }
            }
        }
        if(i != null) {
            i.setShape(this);
        }
        return i;
    }

    @Override
    public void prepare() {
        for(Triangle t: this.faces) {
            t.prepare();
        }
    }
}
