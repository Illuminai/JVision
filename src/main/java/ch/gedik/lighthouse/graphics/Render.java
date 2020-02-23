package ch.gedik.lighthouse.graphics;

import ch.herzog.lighthouse.math.Point3d;
import ch.herzog.lighthouse.render.Raytracer;
import ch.herzog.lighthouse.scene.Scene;
import ch.herzog.lighthouse.scene.shape.Sphere;

public class Render {
    private final Screen renderOn;
    private final GameCanvas parent;
    private Raytracer tracer;
    public Render(Screen renderOn, GameCanvas parent) {
        this.renderOn = renderOn;
        this.parent = parent;
        init();
    }

    private void init() {
        Sphere sphere = new Sphere(new Point3d(0, 0, 2), 1);
        Scene scene = new Scene();
        scene.getShapes().add(sphere);

        tracer = new Raytracer(scene);
    }

    public void render() {
        //TODO render here your image


        int[] p = this.getScreen().getPixels();
        for(int i = 0; i < p.length; i++){
            p[i] = (int)(Math.random() * 0xFFFFFF);
        }
        renderOn.drawScreen(0,0,tracer.renderScene());
    }

    public void tick() {
        //do nothing
    }
    public Screen getScreen() {
        return renderOn;
    }
}
