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
        renderOn.drawScreen(0,0, tracer.renderScene());
    }

    public void tick() {
        GameListener l = parent.getListener();
        Point3d location = tracer.getPosition();
        Point3d rotation = tracer.getRotation();
        if(l.isKeyDown(KeyEvent.VK_W)) {
            tracer.moveForward(.1);
        }
        if(l.isKeyDown(KeyEvent.VK_S)) {
            tracer.moveForward(-.1);
        }
        if(l.isKeyDown(KeyEvent.VK_D)) {
            tracer.moveSideward(-.1);
        }
        if(l.isKeyDown(KeyEvent.VK_A)) {
            tracer.moveSideward(.1);
        }
        if(l.isKeyDown(KeyEvent.VK_SPACE)) {
            tracer.moveUpwards(.1);
        }
        if(l.isKeyDown(KeyEvent.VK_SHIFT)) {
            tracer.moveUpwards(-.1);
        }

        if(l.isKeyDown(KeyEvent.VK_L)) {
            rotation.setZ(rotation.getZ() - .05);
        }
        if(l.isKeyDown(KeyEvent.VK_J)) {
            rotation.setZ(rotation.getZ() + .05);
        }
        if(l.isKeyDown(KeyEvent.VK_I)) {
            rotation.setY(rotation.getY() + .05);
        }
        if(l.isKeyDown(KeyEvent.VK_K)) {
            rotation.setY(rotation.getY() - .05);
        }
        if(l.isKeyDown(KeyEvent.VK_U)) {
            rotation.setX(rotation.getX() - .05);
        }
        if(l.isKeyDown(KeyEvent.VK_O)) {
            rotation.setX(rotation.getX() + .05);
        }
        tracer.getScene().tick();
    }

    public Screen getScreen() {
        return renderOn;
    }
}
