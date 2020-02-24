package com.illuminai.vision.frontend.listener;

/** The {@link GameListener} flushes registered events with instances of this interface, provided they were registered with {@link GameListener#addExecuter(EventExecuter)}*/
public interface EventExecuter {
    public void keyPressed(int code);
    public void keyReleased(int code);
    public void mouseClicked(int x, int y, int mouseButton);
}
