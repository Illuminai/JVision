package com.illuminai.vision.frontend;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

public class GameCanvas extends Canvas {
    private static final long serialVersionUID = -4056684583167815036L;
    private BufferedImage image;
    private int[] pixels;
    private Screen originalScreen;
    private boolean running = false;
    private RenderThread thread;
    private boolean hasInit = false;
    private ScreenScaler scaler;
    private boolean shouldResize = false;
    public static final int MAX_TICKS_PER_SECOND = 50;
    private Render render;
    private BufferedImage errorImage;

    public GameCanvas() {
        this.errorImage = new BufferedImage(20,50,BufferedImage.TYPE_INT_RGB);
        errorImage.getGraphics().drawString("Error",0,25);
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                shouldResize = true;
            }
        });
    }

    private void resizeCanvas() {
        image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB); // TYPE_4BYTE_ABGR
        pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        scaler = new ScreenScaler(originalScreen, getWidth(), getHeight());
    }

    public synchronized void start() {
        if (running) {
            return;
        }
        init();
        running = true;
        (thread = new RenderThread()).start();
    }

    public synchronized void init() {
        if (running || hasInit) {
            return;
        }
        hasInit = true;
        image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB); // TYPE_4BYTE_ABGR
        pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        originalScreen = new Screen(image.getWidth(), image.getHeight());
        scaler = new ScreenScaler(originalScreen, originalScreen.getWidth(), originalScreen.getHeight());
        createBufferStrategy(2);
        this.render = new Render(originalScreen, this);
    }

    public synchronized void stop() {
        try {
            if (thread != null) {
                running = false;
                thread.join();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private class RenderThread extends Thread {
        private int fps, ticks;
        private long lastTimeTicked;

        public RenderThread() {
            super("Tick and Render Thread");
        }

        @Override
        public void run() {
            Arrays.fill(pixels, -1);
            Graphics g = getGraphics();
            long now = System.currentTimeMillis();
            fps = 0;
            ticks = 0;
            while (running) {
                if (g == null) {
                    GameCanvas.this.createBufferStrategy(2);
                    g = getGraphics();
                }
                try {
                    executeTick();
                } catch (Exception e) {
                    System.out.println("Exception while ticking the game");
                    System.out.println("Thread will be stopped");
                    e.printStackTrace();
                    running = false;
                    continue;
                }
                try {
                    if (shouldResize) {
                        resizeCanvas();
                        shouldResize = false;
                    }
                    executeRender(g);
                } catch (Exception e) {
                    System.out.println("Exception while rendering the game");
                    System.out.println("Thread will be stopped");
                    e.printStackTrace();
                    try {
                        g.drawImage(errorImage, 0, 0, null);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    running = false;
                    continue;
                }

                if (System.currentTimeMillis() - now > 1000) {
                    System.out.println("fps/ticks per second: " + fps + "/" + ticks);
                    now = System.currentTimeMillis();
                    fps = 0;
                    ticks = 0;
                }
            }
            running = false;
        }

        private void executeRender(Graphics g) {
            render.render();
            Screen s = scaler.scaledInstance();
            assert s.getPixels().length == pixels.length : "Wrong things";
            for (int i = 0; i < pixels.length; i++) {
                pixels[i] = s.getPixels()[i];
            }
            g.drawImage(image, 0, 0, null);
            this.fps++;
        }

        private void executeTick() {
            if (System.currentTimeMillis() - lastTimeTicked > 1000 / MAX_TICKS_PER_SECOND) {
                render.tick();
                lastTimeTicked = System.currentTimeMillis();
                ticks++;
            }
        }
    }

    public int getOriginalWidth() {
        return originalScreen.getWidth();
    }

    public int getOriginalHeight() {
        return originalScreen.getHeight();
    }

    public int getScaledWidth() {
        return image.getWidth();
    }

    public int getScaledHeight() {
        return image.getHeight();
    }
}
