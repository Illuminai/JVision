package com.illuminai.vision.frontend;

import com.illuminai.vision.backend.math.Point3d;
import com.illuminai.vision.backend.render.Raytracer;
import com.illuminai.vision.backend.scene.Scene;
import com.illuminai.vision.backend.scene.shape.Sphere;
import com.illuminai.vision.frontend.listener.GameListener;

import java.awt.event.KeyEvent;

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
        Scene scene = new Scene();
        scene.sceneInit();
        tracer = new Raytracer(scene);
    }

    public void render() {
        //TODO render here your image
        renderOn.drawScreen(0,0, tracer.renderScene());
    }

    public void tick() {
        GameListener l = parent.getListener();
        Point3d p = tracer.getPosition();
        if(l.isKeyDown(KeyEvent.VK_W)) {
            p.setX(p.getX() + .1);
        }
        if(l.isKeyDown(KeyEvent.VK_S)) {
            p.setX(p.getX() - .1);
        }
        if(l.isKeyDown(KeyEvent.VK_D)) {
            p.setZ(p.getZ() + .1);
        }
        if(l.isKeyDown(KeyEvent.VK_A)) {
            p.setZ(p.getZ() - .1);
        }
        tracer.getScene().tick();
    }

    public Screen getScreen() {
        return renderOn;
    }
}
