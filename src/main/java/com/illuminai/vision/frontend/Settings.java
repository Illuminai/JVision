package com.illuminai.vision.frontend;

public class Settings implements Cloneable {
    private boolean pause;
    private int samples;

    public boolean getPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public int getSamples() {
        return samples;
    }

    public void setSamples(int samples) {
        this.samples = samples;
    }

    @Override
    public Settings clone() {
        try {
            return (Settings) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
