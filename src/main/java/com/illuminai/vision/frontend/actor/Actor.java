package com.illuminai.vision.frontend.actor;

import com.illuminai.vision.frontend.Render;
import com.illuminai.vision.frontend.listener.GameListener;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Actor {
    public static final Mode MODE_DEFAULT = new Mode("default");
    public static final Mode ROTATION_MODE = new Mode("rotation");

    private final Render render;
    private final GameListener listener;
    private Mode mode;

    /**This keys are executed regardless of the mode */
    private LinkedList<KeyBinding> alwaysKey;
    /** Static keys are checked each tick and are not event-oriented */
    private HashMap<Mode, LinkedList<KeyBinding>> staticKeys;
    /** Event keys are checked only when an event is invoked */
    private HashMap<Mode, LinkedList<KeyBinding>> eventKeys;

    public Actor(Render render) {
        this.render = render;
        this.listener = render.getListener();
        this.mode = MODE_DEFAULT;

        this.staticKeys = new HashMap<>();
        this.staticKeys.put(MODE_DEFAULT, new LinkedList<>());
        this.staticKeys.put(ROTATION_MODE, new LinkedList<>());

        this.alwaysKey = new LinkedList<>();
        this.alwaysKey.add(new KeyBinding(KeyEvent.VK_ESCAPE, 0, new ActionMode(MODE_DEFAULT)));

        loadDefaultKeyBindings();
    }

    private void loadDefaultKeyBindings() {
        //assert false;
        createDefaultKeyBindings();
    }

    private void createDefaultKeyBindings() {
        this.alwaysKey.add(new KeyBinding(KeyEvent.VK_ESCAPE, 0, new ActionMode(MODE_DEFAULT)));
        {
            LinkedList<KeyBinding> def = staticKeys.get(MODE_DEFAULT);
            def.add(new KeyBinding(KeyEvent.VK_W, 0, new ActionCommand("move +f")));
            def.add(new KeyBinding(KeyEvent.VK_S, 0, new ActionCommand("move -f")));
            def.add(new KeyBinding(KeyEvent.VK_A, 0, new ActionCommand("move -s")));
            def.add(new KeyBinding(KeyEvent.VK_D, 0, new ActionCommand("move +s")));

        }
    }

    public void tick() {
        this.alwaysKey.forEach(KeyBinding::act);
        this.staticKeys.get(mode).forEach(KeyBinding::act);
    }

    public class KeyBinding {
        public static final int SHIFT = GameListener.MOD_SHIFT, CTRL = GameListener.MOD_CTRL, ALT = GameListener.MOD_ALT;
        private int key;
        private int modifiers;
        private Action action;

        public KeyBinding(int key, int modifiers, Action action) {
            this.key = key;
            this.modifiers = modifiers;
            this.action = action;
        }

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public int getModifiers() {
            return modifiers;
        }

        public void setModifiers(int modifiers) {
            this.modifiers = modifiers;
        }

        public Action getAction() {
            return action;
        }

        public void setAction(Action action) {
            this.action = action;
        }

        /** Checks if the required modifiers (shift, control etc.)
         * are pressed and if that's the case, the action is executed*/
        public void act() {
            if(listener.isKeyDown(key) && listener.checkModifiers(modifiers)) {
                action.act();
            }
        }
    }

    public interface Action {
        void act();
    }

    public class ActionCommand implements Action {
        private String command;
        public ActionCommand(String command) {
            this.command = command;
        }

        @Override
        public void act() {
            render.executeCommand(command);
        }
    }

    public class ActionMode implements Action {
        private Mode mode;
        public ActionMode(Mode mode) {
            this.mode = mode;
        }

        @Override
        public void act() {
            render.setMode(mode);
        }
    }

    public static class Mode {
        private final String displayName;
        public Mode(String name) {
            this.displayName = name;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}
