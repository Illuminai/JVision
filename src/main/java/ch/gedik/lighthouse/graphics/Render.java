package ch.gedik.lighthouse.graphics;

public class Render {
    private final Screen renderOn;
    private final GameCanvas parent;

    public Render(Screen renderOn, GameCanvas parent) {
        this.renderOn = renderOn;
        this.parent = parent;
    }

    public void render() {
        //TODO render here your image
        int[] p = this.getScreen().getPixels();
        for(int i = 0; i < p.length; i++){
            p[i] = (int)(Math.random() * 0xFFFFFF);
        }
    }

    public void tick() {
        //do nothing
    }
    public Screen getScreen() {
        return renderOn;
    }
}
