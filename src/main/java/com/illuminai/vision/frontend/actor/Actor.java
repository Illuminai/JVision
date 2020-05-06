package com.illuminai.vision.frontend.actor;

import com.illuminai.vision.frontend.Render;
import com.illuminai.vision.frontend.listener.EventExecuter;
import com.illuminai.vision.frontend.listener.GameListener;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.LinkedList;

public class Actor implements EventExecuter {
    private final Render render;
    private final GameListener listener;

    /**This keys are executed regardless of the mode */
    private LinkedList<KeyBinding> alwaysStaticKeys;
    private LinkedList<KeyBindingEvent> alwaysEventKeys;

    /** Static keys are checked each tick and are not event-oriented */
    private HashMap<Mode, LinkedList<KeyBinding>> staticKeys;
    /** Event keys are checked only when an event is invoked */
    private HashMap<Mode, LinkedList<KeyBindingEvent>> eventKeys;

    public Actor(Render render) {
        this.render = render;
        this.listener = render.getListener();

        this.staticKeys = new HashMap<>();
        this.staticKeys.put(Mode.DEFAULT, new LinkedList<>());
        this.staticKeys.put(Mode.ROTATOR, new LinkedList<>());

        this.eventKeys = new HashMap<>();
        this.eventKeys.put(Mode.DEFAULT, new LinkedList<>());
        this.eventKeys.put(Mode.ROTATOR, new LinkedList<>());

        this.alwaysStaticKeys = new LinkedList<>();
        this.alwaysEventKeys = new LinkedList<>();

        loadDefaultKeyBindings();
    }

    private void loadDefaultKeyBindings() {
        //assert false;
        createDefaultKeyBindings();
    }

    private void createDefaultKeyBindings() {
        //Always static keys
        this.alwaysEventKeys.add(new KeyBindingEvent(KeyEvent.VK_ESCAPE, 0, new ActionMode(Mode.DEFAULT), false));
        this.alwaysEventKeys.add(new KeyBindingEvent(KeyEvent.VK_R,0,new ActionMode(Mode.ROTATOR),false));
        //Always event keys

        //Static keys
        {
            LinkedList<KeyBinding> def = staticKeys.get(Mode.DEFAULT);
            def.add(new KeyBinding(KeyEvent.VK_W, 0, new ActionCommand("move +f")));
            def.add(new KeyBinding(KeyEvent.VK_S, 0, new ActionCommand("move -f")));
            def.add(new KeyBinding(KeyEvent.VK_A, 0, new ActionCommand("move +s")));
            def.add(new KeyBinding(KeyEvent.VK_D, 0, new ActionCommand("move -s")));
            def.add(new KeyBinding(KeyEvent.VK_SPACE, 0, new ActionCommand("move +u")));
            def.add(new KeyBinding(KeyEvent.VK_C, 0, new ActionCommand("move -u")));

            def.add(new KeyBinding(KeyEvent.VK_J, 0, new ActionCommand("rotate +z")));
            def.add(new KeyBinding(KeyEvent.VK_L, 0, new ActionCommand("rotate -z")));
            def.add(new KeyBinding(KeyEvent.VK_I, 0, new ActionCommand("rotate +y")));
            def.add(new KeyBinding(KeyEvent.VK_K, 0, new ActionCommand("rotate -y")));
            def.add(new KeyBinding(KeyEvent.VK_O, 0, new ActionCommand("rotate +x")));
            def.add(new KeyBinding(KeyEvent.VK_U, 0, new ActionCommand("rotate -x")));
        }
        {
            LinkedList<KeyBinding> rot = staticKeys.get(Mode.ROTATOR);
            rot.add(new KeyBinding(KeyEvent.VK_D, 0, new ActionCommand("rotator +z")));
            rot.add(new KeyBinding(KeyEvent.VK_A, 0, new ActionCommand("rotator -z")));
            rot.add(new KeyBinding(KeyEvent.VK_W, 0, new ActionCommand("rotator +y")));
            rot.add(new KeyBinding(KeyEvent.VK_S, 0, new ActionCommand("rotator -y")));
            rot.add(new KeyBinding(KeyEvent.VK_SPACE, 0, new ActionCommand("rotator +d")));
            rot.add(new KeyBinding(KeyEvent.VK_C, 0, new ActionCommand("rotator -d")));
        }

        //Event keys
    }

    public void tick() {
        this.alwaysStaticKeys.forEach(KeyBinding::act);
        this.staticKeys.get(render.getMode()).forEach(KeyBinding::act);
    }

    public void addKeyBinding(Mode mode, KeyBinding binding) {
        if(mode == null) {
            alwaysStaticKeys.add(binding);
        } else {
            staticKeys.get(mode).add(binding);
        }
    }
    public void addKeyBindingEvent(Mode mode, KeyBindingEvent binding) {
        if(mode == null) {
            alwaysEventKeys.add(binding);
        } else {
            eventKeys.get(mode).add(binding);
        }
    }
    @Override
    public void keyPressed(int code) {
        alwaysEventKeys.forEach(keyBinding -> {
            if(!keyBinding.isOnRelease()) {
                keyBinding.act();
            }
        });
        eventKeys.get(render.getMode()).forEach(keyBinding -> {
            if(!keyBinding.isOnRelease()) {
                keyBinding.act();
            }
        });
    }

    @Override
    public void keyReleased(int code) {
        alwaysEventKeys.forEach(keyBinding -> {
            if(keyBinding.isOnRelease()) {
                keyBinding.act();
            }
        });
        eventKeys.get(render.getMode()).forEach(keyBinding -> {
            if(keyBinding.isOnRelease()) {
                keyBinding.act();
            }
        });
    }

    @Override
    public void mouseClicked(int x, int y, int mouseButton) {
        this.render.mouseClicked(x,y,mouseButton);
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

    public class KeyBindingEvent extends KeyBinding {
        private boolean onRelease;
        public KeyBindingEvent(int key, int modifiers, Action action, boolean onRelease) {
            super(key, modifiers, action);
            this.onRelease = onRelease;
        }

        public boolean isOnRelease() {
            return onRelease;
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

    public enum Mode {
        DEFAULT("default") , ROTATOR("rotator");

        Mode(String name) {
            this.displayName = name;
        }
        final String displayName;
    }
}
