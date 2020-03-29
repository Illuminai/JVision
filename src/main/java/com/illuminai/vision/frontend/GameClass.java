package com.illuminai.vision.frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintStream;

public class GameClass extends JFrame {
    private static final long serialVersionUID = -624471870025862738L;

    private GameCanvas canvas;

    public static final int VERSION = 0;
    public static final String VERSION_NAME = "Alpha 0.0.1";

    private GameClass(int width, int height) throws HeadlessException {
        super("PrimitiveRenderer " + VERSION_NAME + " (" + VERSION + ")");
        System.out.println("Starting on version: " + VERSION_NAME + " (" + VERSION + ")");
        initFrame(width, height);
        addFocusListener(canvas.getListener());
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onExit();
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

    private void onExit() {
        canvas.stop();
    }

    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        JMenuItem itemPause = new JCheckBoxMenuItem("Pause");
        itemPause.addActionListener((e) -> this.canvas.getListener().changeAttribute(Render.ATT_PAUSE, itemPause.isSelected()));

        JMenuItem itemSettings = new JMenuItem("Settings");
        itemSettings.addActionListener((e) -> this.canvas.getListener().changeAttribute(Render.ATT_SAMPLES, Integer.valueOf(JOptionPane.showInputDialog("Input amount of samples"))));

        JMenuItem itemExit = new JMenuItem("Exit");
        itemExit.addActionListener((e) -> {
            onExit();
            dispose();
        });

        menuFile.add(itemPause);
        menuFile.addSeparator();
        menuFile.add(itemSettings);
        menuFile.addSeparator();
        menuFile.add(itemExit);

        menuBar.add(menuFile);
        setJMenuBar(menuBar);
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
        createMenu();

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(true);

        this.canvas = new GameCanvas();
        canvas.setPreferredSize(new Dimension(width, height));
        this.getContentPane().add(canvas);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    public static GameClass createGame(String[] args, int width, int height) {
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
        return new GameClass(width, height);
    }
}

