package com.illuminai.vision.frontend.listener;

/** The {@link GameListener} flushes registered events with instances of this interface, provided they were registered with {@link GameListener#addExecuter(EventExecuter)}*/
public interface EventExecuter {
    void keyPressed(int code);
    void keyReleased(int code);
    void mouseClicked(int x, int y, int mouseButton);
    void changeAttribute(String name, Object newVal);
}
