package ch.gedik.lighthouse.graphics;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintStream;

import javax.swing.JFrame;

public class GameClass extends JFrame {
    private static final long serialVersionUID = -624471870025862738L;

    private GameCanvas canvas;

    public static final int VERSION = 0;
    public static final String VERSION_NAME = "Alpha 0.0.1";

    private GameClass(int width, int height) throws HeadlessException {
        super("PrimitiveGame " + VERSION_NAME + " (" + VERSION + ")");
        System.out.println("Starting on version: " + VERSION_NAME + " (" + VERSION + ")");
        initFrame(width, height);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                canvas.stop();
            }
        });
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
            }
        });
        this.setVisible(true);
        System.out.println("Warning! Canvas not started!");
        // canvas.start();
    }

    public synchronized void start() {
        canvas.start();
    }

    public void initCanvas() {
        canvas.init();
    }

    public GameCanvas getGameCanvas() {
        return canvas;
    }

    private void initFrame(int width, int height) {
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(true);

        this.canvas = new GameCanvas();
        canvas.setPreferredSize(new Dimension(width, height));
        this.getContentPane().add(canvas);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    public static GameClass createGame(String[] args, int width, int height) throws Exception {
        System.setOut(new PrintStream(System.out) {
            @Override
            public void print(String x) {
                super.print(getHistory() + x);
            }

            @Override
            public void print(boolean b) {
                super.print(getHistory() + b);
            }

            public void print(int b) {
                super.print(getHistory() + b);
            }

            public void print(long b) {
                super.print(getHistory() + b);
            }

            public void print(double b) {
                super.print(getHistory() + b);
            }

            public void print(float b) {
                super.print(getHistory() + b);
            }

            public void print(Object x) {
                super.print(getHistory() + x);
            }

            private String getHistory() {
                return "[" + Thread.currentThread().getStackTrace()[4].getClassName() + "."
                        + Thread.currentThread().getStackTrace()[4].getMethodName() + ":"
                        + Thread.currentThread().getStackTrace()[4].getLineNumber() + "]";
            }
        });
        GameClass game = new GameClass(width, height);
        return game;
    }

    public static void main(String[] args) throws Exception {
        GameClass game = GameClass.createGame(args, 500, 500);
        game.initCanvas();
        game.start();
    }

}

