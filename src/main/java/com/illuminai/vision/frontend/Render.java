package com.illuminai.vision.frontend;

import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.render.Intersection;
import com.illuminai.vision.backend.render.Ray;
import com.illuminai.vision.backend.render.Raymarcher;
import com.illuminai.vision.backend.scene.Scene;
import com.illuminai.vision.backend.scene.shape.Mesh;
import com.illuminai.vision.frontend.listener.EventExecuter;
import com.illuminai.vision.frontend.listener.GameListener;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Render implements EventExecuter {
    /** Attribute for samples*/
    public static final String ATT_SAMPLES = "ATT_SAMPLES", ATT_PAUSE = "ATT_PAUSE";

    private final Screen renderOn, tempScreen;
    private ScreenAverager averager;
    private final GameCanvas parent;
    private Raymarcher tracer;
    private boolean pause;

    private Mesh selectedMesh;

    public Render(Screen renderOn, GameCanvas parent) {
        this.renderOn = renderOn;
        this.tempScreen = new Screen(renderOn.getWidth(), renderOn.getHeight());
        this.parent = parent;
        this.parent.getListener().addExecuter(this);
        this.averager = new ScreenAverager(1,renderOn.getWidth(), renderOn.getHeight());
        init();
    }

    private void init() {
        Scene scene = new Scene();
        scene.sceneInit();
        tracer = new Raymarcher(scene);
    }

    public void render() {
        if(!pause) {
            for(int i = 0; i < averager.getAmount(); i++) {
                averager.getScreens()[i] = tracer.renderScene(Math.random() * 0.001);
            }
            tempScreen.drawScreen(0,0, averager.calculateAverage());
        }
        renderOn.drawScreen(0,0,tempScreen);
        drawHUD();
    }

    private void drawHUD() {
        String text = "";
        text += "renderResolution: " + "TODO" + "\n";
        text += "displayResolution: " + this.parent.getWidth() + "/" + this.parent.getHeight() + "\n";

        text += "selected: ";
        if(selectedMesh != null) {
            text += "\n  " + selectedMesh.getClass().getName();
        }
        text += "\n";

        Screen screen =  FontCreator.createFont(text, 0x0, -1);
        renderOn.drawScreen(0,0, screen.getScaledScreen(renderOn.getWidth() * 3 / 4, screen.getHeight()));
    }

    public Screen getScreen() {
        return renderOn;
    }

    public void tick() {
        GameListener l = parent.getListener();
        Vector3d location = tracer.getCamera().getPosition();
        Vector3d rotation = tracer.getCamera().getRotation();
        if(!pause) {
            if(l.isKeyDown(KeyEvent.VK_W)) {
                tracer.getCamera().moveForward(.1);
            }
            if(l.isKeyDown(KeyEvent.VK_S)) {
                tracer.getCamera().moveForward(-.1);
            }
            if(l.isKeyDown(KeyEvent.VK_D)) {
                tracer.getCamera().moveSideward(-.1);
            }
            if(l.isKeyDown(KeyEvent.VK_A)) {
                tracer.getCamera().moveSideward(.1);
            }
            if(l.isKeyDown(KeyEvent.VK_SPACE)) {
                tracer.getCamera().moveUpwards(.1);
            }
            if(l.isKeyDown(KeyEvent.VK_SHIFT)) {
                tracer.getCamera().moveUpwards(-.1);
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
            tracer.getCamera().setRotation(rotation);
            tracer.getScene().tick();
        }
    }

    public boolean isPause() {
        return pause;
    }

    public int getSamples() {
        return this.averager.getAmount();
    }

    public void setSamples(int samples) {
        this.averager.setAmount(samples);
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    @Override
    public void keyPressed(int code) {}

    @Override
    public void keyReleased(int code) {}

    @Override
    public void mouseClicked(int x, int y, int mouseButton) {
        if(mouseButton == MouseEvent.BUTTON1) {
            Ray ray = tracer.getCamera().getRay((x - tracer.getRenderWidth() / 2.0) / tracer.getRenderWidth(),
                    (y - tracer.getRenderHeight() / 2.0) / tracer.getRenderHeight() );
            Intersection intersection = tracer.getIntersection(ray);
            if(intersection != null) {
                this.selectedMesh = intersection.getMesh();
            } else {
                this.selectedMesh = null;
            }
        }
    }

    @Override
    public void changeAttribute(String name, Object newVal) {
        switch (name) {
            case ATT_SAMPLES:
                setSamples((int)newVal);
                break;
            case ATT_PAUSE:
                setPause((boolean) newVal);
                break;
            default:
                throw new RuntimeException("Unknown name: " + name);
        }
    }
}
