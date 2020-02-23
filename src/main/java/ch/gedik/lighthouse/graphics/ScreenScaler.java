package ch.gedik.lighthouse.graphics;


public class ScreenScaler {
    private final Screen original;
    private final int[] loc;
    private final int width, height;

    public ScreenScaler(Screen original, int width, int height) {
        this.original = original;
        loc = new int[width * height];
        this.width = width;
        this.height = height;

        calculate();
    }

    private void calculate() {
        double xFactor = 1.0 * original.getWidth() / width;
        double yFactor = 1.0 * original.getHeight() / height;
        int xVal;
        for (int x = 0; x < width; x++) {
            xVal = (int) (xFactor * x);
            for (int y = 0; y < height; y++) {
                loc[width * y + x] = xVal + ((int) (yFactor * y) * original.getWidth());
            }
        }

        System.gc();
    }

    public Screen scaledInstance() {
        return scaleOn(new Screen(width, height));
    }

    public Screen scaleOn(Screen s) {
        if (s.getWidth() != width || s.getHeight() != height) {
            throw new RuntimeException("Invalid attributes");
        }
        for (int i = 0; i < loc.length; i++) {
            s.getPixels()[i] = original.getPixels()[loc[i]];
        }
        return s;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Screen getOriginal() {
        return original;
    }
}
