package com.illuminai.vision.frontend;

import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.render.Intersection;
import com.illuminai.vision.backend.render.Ray;
import com.illuminai.vision.backend.render.Raytracer;
import com.illuminai.vision.backend.scene.Scene;
import com.illuminai.vision.backend.scene.shape.Shape;
import com.illuminai.vision.frontend.listener.EventExecuter;
import com.illuminai.vision.frontend.listener.GameListener;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Render implements EventExecuter {
    private final Screen renderOn, tempScreen;
    private ScreenAverager averager;
    private final GameCanvas parent;
    private Raytracer tracer;
    private long lastFrame;

    private Shape selectedShape;

    private Settings settings;

    public Render(Screen renderOn, GameCanvas parent) {
        this.renderOn = renderOn;
        this.settings = new Settings();
        initSettings();

        this.tempScreen = new Screen(renderOn.getWidth(), renderOn.getHeight());
        this.parent = parent;
        this.parent.getListener().addExecuter(this);
        this.averager = new ScreenAverager(settings.getSamples(), renderOn.getWidth(), renderOn.getHeight());
        init();
    }

    private void init() {
        Scene scene = new Scene();
        scene.sceneInit();
        tracer = new Raytracer(scene);
    }

    private void initSettings() {
        settings.setPause(false);
        settings.setSamples(1);
    }

    /**
     * Applies the settings to the render engine
     */
    private void applySettings() {
        if (averager.getAmount() != settings.getSamples()) {
            averager.setAmount(settings.getSamples());
        }
    }

    public void render() {
        applySettings();
        if (!settings.getPause()) {
            lastFrame = System.currentTimeMillis();

            for (int i = 0; i < averager.getAmount(); i++) {
                averager.getScreens()[i] = tracer.renderScene(i == averager.getAmount() - 1 ? 0 : Math.random());
            }


            tempScreen.drawScreen(0, 0, averager.calculateAverage());
            lastFrame = System.currentTimeMillis() - lastFrame;
        }
        renderOn.drawScreen(0, 0, tempScreen);
        drawHUD();
    }

    private void drawHUD() {
        if (selectedShape != null) {
            long[] outline = tracer.getOutliner();
            int w = (int) tracer.getRenderWidth();
            int h = (int) tracer.getRenderHeight();
            //Do not count first and last pixel
            //Makes checking more complicated because of bounds-checking
            for (int x = 1; x < w - 1; x++) {
                for (int y = 1; y < h - 1; y++) {
                    int i = x + y * w;
                    if (outline[i] == selectedShape.getId()) {
                        if (outline[i - 1] != selectedShape.getId() || outline[i + 1] != selectedShape.getId() || outline[i + w] != selectedShape.getId() || outline[i - w] != selectedShape.getId()) {
                            renderOn.setPixel(i, 0xff00ff);
                        }
                    }
                }
            }
        }

        String text = "";
        text += "renderResolution:  " + this.tracer.getRenderWidth() + "/" + this.tracer.getRenderHeight() + "\n";
        text += "displayResolution: " + this.parent.getWidth() + "/" + this.parent.getHeight() + "\n";

        text += "selected: ";
        if (selectedShape != null) {
            text += "\n  " + selectedShape.getClass().getName();
        }
        text += "\n";

        text += "This Frame: " + lastFrame + " ms";

        Screen screen = FontCreator.createFont(text, 0x0, -1);
        renderOn.drawScreen(0, 0, screen.getScaledScreen(renderOn.getWidth() * 3 / 4, screen.getHeight() / 2));
    }

    public Screen getScreen() {
        return renderOn;
    }

    public void tick() {
        GameListener l = parent.getListener();
        Vector3d location = tracer.getCamera().getPosition();
        Vector3d rotation = tracer.getCamera().getRotation();
        if (!settings.getPause()) {
            if (l.isKeyDown(KeyEvent.VK_W)) {
                tracer.getCamera().moveForward(.1);
            }
            if (l.isKeyDown(KeyEvent.VK_S)) {
                tracer.getCamera().moveForward(-.1);
            }
            if (l.isKeyDown(KeyEvent.VK_D)) {
                tracer.getCamera().moveSideward(-.1);
            }
            if (l.isKeyDown(KeyEvent.VK_A)) {
                tracer.getCamera().moveSideward(.1);
            }
            if (l.isKeyDown(KeyEvent.VK_SPACE)) {
                tracer.getCamera().moveUpwards(.1);
            }
            if (l.isKeyDown(KeyEvent.VK_SHIFT)) {
                tracer.getCamera().moveUpwards(-.1);
            }

            if (l.isKeyDown(KeyEvent.VK_L)) {
                rotation.setZ(rotation.getZ() - .05);
            }
            if (l.isKeyDown(KeyEvent.VK_J)) {
                rotation.setZ(rotation.getZ() + .05);
            }
            if (l.isKeyDown(KeyEvent.VK_I)) {
                rotation.setY(rotation.getY() + .05);
            }
            if (l.isKeyDown(KeyEvent.VK_K)) {
                rotation.setY(rotation.getY() - .05);
            }
            if (l.isKeyDown(KeyEvent.VK_U)) {
                rotation.setX(rotation.getX() - .05);
            }
            if (l.isKeyDown(KeyEvent.VK_O)) {
                rotation.setX(rotation.getX() + .05);
            }
            tracer.getCamera().setRotation(rotation);
            tracer.getScene().tick();
        }
    }

    @Override
    public void keyPressed(int code) {
    }

    @Override
    public void keyReleased(int code) {
    }

    @Override
    public void mouseClicked(int x, int y, int mouseButton) {
        if (mouseButton == MouseEvent.BUTTON1) {
            Ray ray = tracer.getCamera().getRay((x - tracer.getRenderWidth() / 2.0) / tracer.getRenderWidth(),
                    (y - tracer.getRenderHeight() / 2.0) / tracer.getRenderHeight());
            Intersection intersection = tracer.getIntersection(ray);
            if (intersection != null) {
                this.selectedShape = intersection.getShape();
            } else {
                this.selectedShape = null;
            }
        }
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public Settings getSettings() {
        return settings;
    }
}
