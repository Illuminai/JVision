package com.illuminai.vision.backend.scene.shape;

import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.render.Intersection;
import com.illuminai.vision.backend.render.Ray;
import com.illuminai.vision.backend.scene.material.Material;

public class SimplePolygon extends Shape {
    private Triangle[] faces;

    /** Constructs a simple polygon*/
    public SimplePolygon(Material material,
                         Vector3d[] points, int[][] faces) {
        super(points[0], material);
        this.faces = new Triangle[2 * faces.length];

        for(int i = 0; i < faces.length; i++) {
            this.faces[i] = new Triangle(points[faces[i][0]], points[faces[i][1]], points[faces[i][2]], material);
            this.faces[i + faces.length] = new Triangle(points[faces[i][0]], points[faces[i][2]], points[faces[i][1]], material);

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
        return i;
    }

    @Override
    public boolean contains(Vector3d point) {
        assert false;
        return false;
    }

    @Override
    public void prepare() {
        for(Triangle t: this.faces) {
            t.prepare();
        }
    }
}
