package com.illuminai.vision.frontend.listener;

import com.illuminai.vision.frontend.GameCanvas;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

/** This class serves as keylistener, focuslistener and mouselistener. There are two ways of checking the current status of the keys/mouse. Either one adds an {@link EventExecuter} and listens in a event-oriented manner
 *  (careful tough, as the events are registered and only given to the executers once {@link #flushEvents()} is called; this means that it is not real-time), or one can check the
 * {@link #isKeyDown(int)}, {@link #isMouseDown()} and {@link #getMouseButton()} or {@link #getMouseX()} and {@link #getMouseY()} the methods for the current status.*/
public class GameListener implements KeyListener, FocusListener, MouseListener, MouseMotionListener {
    private static final String EVENT_MOUSE_PRESSED = "EMP", EVENT_KEY_RELEASED = "EKR", EVENT_KEY_PRESSED = "EKP";

    private final boolean[] keys = new boolean[65536];

    private final ArrayList<EventExecuter> executers;

    private final LinkedHashMap<String, Object> queuedEvents;

    private int mouseX, mouseY, mouseButton;
    private boolean mouseDown;

    private GameCanvas gameCanvas;

    private final Object LOCK = new Object();

    public GameListener(GameCanvas gameCanvas) {
        this.gameCanvas = gameCanvas;
        executers = new ArrayList<>();
        this.queuedEvents = new LinkedHashMap<>();
    }

    @Override
    public void focusGained(FocusEvent e) {
    }

    @Override
    public void focusLost(FocusEvent e) {
        Arrays.fill(keys, false);
        mouseDown = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent event) {
        synchronized (LOCK) {
            keys[event.getKeyCode()] = true;
            queuedEvents.put(GameListener.EVENT_KEY_PRESSED, event);
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        synchronized (LOCK) {
            keys[event.getKeyCode()] = false;
            queuedEvents.put(GameListener.EVENT_KEY_RELEASED, event);
        }
    }

    public boolean isKeyDown(int id) {
        return keys[id];
    }

    public void addExecuter(EventExecuter exe) {
        if (!executers.contains(exe)) {
            this.executers.add(exe);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
    }

    public boolean isMouseDown() {
        return mouseDown;
    }

    public int getMouseButton() {
        return mouseButton;
    }

    public int getMouseX() {
        return (int) (mouseX * 1.0 * gameCanvas.getOriginalWidth() / gameCanvas.getScaledWidth());
    }

    public int getMouseY() {
        return (int) (1.0 * mouseY * gameCanvas.getOriginalHeight() / gameCanvas.getScaledHeight());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        synchronized (LOCK) {
            mouseDown = true;
            mouseX = e.getX();
            mouseY = e.getY();
            mouseButton = e.getButton();

            queuedEvents.put(GameListener.EVENT_MOUSE_PRESSED, e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        synchronized (LOCK) {
            mouseDown = false;
        }
    }

    @Override
    public void mouseDragged(MouseEvent arg0) {
    }

    @Override
    public void mouseMoved(MouseEvent event) {
        synchronized (LOCK) {
            mouseX = event.getX();
            mouseY = event.getY();
        }
    }

    public void flushEvents() {
        synchronized (LOCK) {
            if (!queuedEvents.isEmpty()) {
                queuedEvents.forEach((name, value) -> {
                    switch (name) {
                        case GameListener.EVENT_MOUSE_PRESSED:
                            for (EventExecuter ex : this.executers) {
                                MouseEvent e = (MouseEvent) value;
                                double wFac, hFac;
                                wFac = 1.0 * gameCanvas.getOriginalWidth() / gameCanvas.getScaledWidth();
                                hFac = 1.0 * gameCanvas.getOriginalHeight() / gameCanvas.getScaledHeight();
                                int x = (int) (e.getX() * wFac);
                                int y = (int) (e.getY() * hFac);
                                ex.mouseClicked(x, y, mouseButton);
                            }
                            break;
                        case GameListener.EVENT_KEY_PRESSED:
                            for (EventExecuter ex : this.executers) {
                                KeyEvent e = (KeyEvent) value;
                                ex.keyPressed(e.getKeyCode());
                            }
                            break;
                        case GameListener.EVENT_KEY_RELEASED:
                            for (EventExecuter ex : this.executers) {
                                KeyEvent e = (KeyEvent) value;
                                ex.keyReleased(e.getKeyCode());
                            }
                            break;
                        default:
                            throw new RuntimeException("Unknown name: " + name);
                    }
                });
                queuedEvents.clear();
            }
        }
    }
}
