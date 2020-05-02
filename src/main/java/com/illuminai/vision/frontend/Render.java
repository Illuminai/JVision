package com.illuminai.vision.frontend;

import com.illuminai.vision.backend.math.Matrix3x3;
import com.illuminai.vision.backend.math.Vector;
import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.render.Intersection;
import com.illuminai.vision.backend.render.Ray;
import com.illuminai.vision.backend.render.Raytracer;
import com.illuminai.vision.backend.scene.Camera;
import com.illuminai.vision.backend.scene.Scene;
import com.illuminai.vision.backend.scene.shape.Shape;
import com.illuminai.vision.frontend.actor.Actor;
import com.illuminai.vision.frontend.listener.EventExecuter;
import com.illuminai.vision.frontend.listener.GameListener;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Render {
    private final Screen renderOn, tempScreen;
    private ScreenAverager averager;
    private final GameCanvas parent;
    private Raytracer tracer;
    private long lastFrame;

    private Actor.Mode mode;

    private Shape selectedShape;

    private Settings settings;

    private FixRotator rot;

    private Actor actor;

    public Render(Screen renderOn, GameCanvas parent) {
        this.renderOn = renderOn;
        this.tempScreen = new Screen(renderOn.getWidth(), renderOn.getHeight());
        this.parent = parent;
        this.settings = new Settings();
        initSettings();

        rot = null;
        this.mode = Actor.Mode.DEFAULT;
        this.actor = new Actor(this);

        this.parent.getListener().addExecuter(actor);
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
        text += "Mode: " + getMode().name() + "\n";
        text += "This Frame: " + lastFrame + " ms";

        Screen screen = FontCreator.createFont(text, 0x0, -1);
        renderOn.drawScreen(0, 0, screen.getScaledScreen(renderOn.getWidth() * 3 / 4, screen.getHeight() / 2));
    }

    public Screen getScreen() {
        return renderOn;
    }

    public void tick() {
        GameListener l = parent.getListener();
        if (!settings.getPause()) {
            if(this.rot != null) {
                rot.apply();
            }
            tracer.getScene().tick();

        }

        actor.tick();
    }

    public GameListener getListener() {
        return parent.getListener();
    }

    public void executeCommand(String command) {
        String[] split = command.split(" ");
        Camera camera = this.tracer.getCamera();
        Vector3d rotation = this.tracer.getCamera().getRotation();
        double distance;
        double angle;
        switch (split[0]) {
            case "move":
                distance = .1;
                if(split.length == 3) {
                    distance = Double.parseDouble(split[2]);
                }
                switch (split[1]) {
                    case "+f": camera.moveForward(distance); break;
                    case "-f": camera.moveForward(-distance); break;
                    case "+s": camera.moveSideward(distance); break;
                    case "-s": camera.moveSideward(-distance); break;
                    case "+u": camera.moveUpwards(distance); break;
                    case "-u": camera.moveUpwards(-distance); break;
                    default: throw new RuntimeException("Invalid parameter: " + split[1]);
                }
                break;
            case "rotate":
                angle =.5;
                if(split.length == 3) {
                    angle = Double.parseDouble(split[2]);
                }
                switch(split[1]) {
                    case "+x": rotation.setX(rotation.getX() + angle); break;
                    case "-x": rotation.setX(rotation.getX() - angle); break;
                    case "+y": rotation.setY(rotation.getY() + angle); break;
                    case "-y": rotation.setY(rotation.getY() - angle); break;
                    case "+z": rotation.setZ(rotation.getZ() + angle); break;
                    case "-z": rotation.setZ(rotation.getZ() - angle); break;
                    default: throw new RuntimeException("Invalid parameter: " + split[1]);
                }
                break;
            case "rotator":
                angle = .1;
                assert rot != null;

                if(split.length == 3) {
                    angle = Double.parseDouble(split[2]);
                }
                switch (split[1]) {
                    case "+z": rot.addZ(angle); break;
                    case "-z": rot.addZ(-angle); break;
                    case "+y": rot.addY(angle); break;
                    case "-y": rot.addY(-angle); break;
                    case "+d": rot.factorDistance(1.1); break;
                    case "-d": rot.factorDistance(1/1.1); break;
                }
            case "mode":
            case "exit":
            case "save": break;
            default:
                throw new RuntimeException("Unknown command:" + split[0]);
        }
    }

    public void setMode(Actor.Mode mode) {
        if(this.mode != mode) {
            this.mode = mode;
            onModeEntry(mode);
        }

    }

    private void onModeEntry(Actor.Mode mode) {
        switch (mode) {
            case DEFAULT:
                rot = null;
                break;
            case ROTATOR:
                rot = new FixRotator();
                break;
            default: throw new RuntimeException("Unknown mode: " + mode.name());
        }
    }

    public Actor.Mode getMode() {
        return mode;
    }

    public void mouseClicked(int x, int y, int mouseButton) {
        if (mouseButton == MouseEvent.BUTTON1) {
            this.selectedShape = getShapeThroughRay(x,y);
        }
    }

    public Shape getShapeThroughRay(double x, double y) {
        Intersection i = tracer.getIntersection(tracer.getCamera().getRay((x - tracer.getRenderWidth() / 2.0) / tracer.getRenderWidth(),
                (y - tracer.getRenderHeight() / 2.0) / tracer.getRenderHeight()));
        if(i != null) {
            return i.getShape();
        } else {
            return null;
        }
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public Settings getSettings() {
        return settings;
    }

    private class FixRotator {
        double angleZ;
        double angleY;
        double distance;
        FixRotator() {
            angleZ = 0;
            angleY = 0;
            distance = 1;
        }

        void apply() {
            if(selectedShape == null) {
                return;
            }
            Vector3d pos = new Vector3d(-distance,0,0);

            pos = Matrix3x3.createRotationMatrix('y', angleY).transformed(pos);
            pos = Matrix3x3.createRotationMatrix('z', angleZ).transformed(pos);

            Render.this.tracer.getCamera().setRotation(new Vector3d(0, (angleY > Math.PI) ? angleY-Math.PI : (angleY < -Math.PI ? angleY + Math.PI: angleY), angleZ));
            Render.this.tracer.getCamera().setPosition(selectedShape.getPosition().add(pos));
        }

        void addZ(double angle) {
            this.angleZ += angle;
        }

        void addY(double angle) {
            this.angleY += angle;
        }

        void factorDistance(double f) {
            distance *= f;
        }
    }
}
