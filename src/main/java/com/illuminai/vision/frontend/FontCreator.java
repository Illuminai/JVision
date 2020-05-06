package com.illuminai.vision.frontend;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class FontCreator {
    private static final Screen LETTERS;
    private static final String LETTER_INDEX = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "\n" + "1234567890.:,+-()_[]{}*&%/" + "\n"
            + "<> !?";
    public static final int LETTER_HEIGHT = 30, LETTER_WIDTH = 30;

    // That color is reserved for the font color
    private static final int NUM_RES_FOR_COLOR = 0xabffffff;

    static {
        Screen fontScreen = Screen.fromURL(FontCreator.class.getResource("/textures/font/DefaultFont.png"));
        for (int i = 0; i < fontScreen.getPixels().length; i++) {
            if (fontScreen.getPixels()[i] == -1) {
                fontScreen.getPixels()[i] = NUM_RES_FOR_COLOR;
            } else {
                fontScreen.getPixels()[i] = -1;
            }
        }
        LETTERS = fontScreen;
    }

    public static String getLetterIndex() {
        return LETTER_INDEX;
    }

    private FontCreator() {
    }

    public static void drawFontOnScreen(String fontText, int x, int y, Screen screen, int color) {
        fontText = fontText.toUpperCase();
        if (fontText.length() == 0) {
            return;
        }
        ArrayList<String> allLines = getLines(fontText);

        for (int line = 0; line < allLines.size(); line++) {
            String lineStr = allLines.get(line);
            for (int i = 0; i < lineStr.length(); i++) {
                char c = lineStr.charAt(i);
                if (c != '\n') {
                    int[] coOnLetters = getCoordinatesOf(c);
                    int[] coOnScreen = new int[] { x + i * LETTER_WIDTH, y + line * LETTER_HEIGHT };
                    screen.drawScreen(coOnScreen[0], coOnScreen[1], coOnLetters[0], coOnLetters[1], LETTER_WIDTH,
                            LETTER_WIDTH, LETTERS);
                }
            }
        }
        for (int i = 0; i < screen.getPixels().length; i++) {
            if (screen.getPixels()[i] == NUM_RES_FOR_COLOR) {
                screen.getPixels()[i] = color;
            }
        }
    }

    public static Screen createFont(String fontText, int color, int background) {
        ArrayList<String> allLines = getLines(fontText);
        int longest = getLongest(allLines);
        Screen screen = new Screen(longest * FontCreator.LETTER_WIDTH, allLines.size() * FontCreator.LETTER_HEIGHT);
        screen.fill(background);
        if (color == background) {
            return screen;
        }
        drawFontOnScreen(fontText, 0, 0, screen, color);
        return screen;
    }

    public static Screen createFont(String fontText, int color, int background, int width, int height) {
        Screen r = createFont(fontText, color, background);
        ScreenScaler s = new ScreenScaler(r, width, height);
        return s.scaledInstance();
    }

    private static ArrayList<String> getLines(String text) {
        ArrayList<String> allLines = new ArrayList<>();
        for (String s : text.split("\n")) {
            allLines.add(s);
        }

        return allLines;
    }

    private static int getLongest(ArrayList<String> allLines) {
        int longest = 0;
        for (String s : allLines) {
            if (s.length() > longest) {
                longest = s.length();
            }
        }
        return longest;
    }

    private static int[] getCoordinatesOf(char c) {
        int index = LETTER_INDEX.indexOf(c + "");
        if (index == -1) {
            return getCoordinatesOf('?');
        }
        int x = index % 27 * LETTER_WIDTH;
        int y = index / 27 * LETTER_HEIGHT;
        int[] xy = new int[] { x, y };
        return xy;
    }

    /** Creates a font image */
    public static BufferedImage createFontBufferedImage(int letterWidth, int letterHeight, String text) {
        ArrayList<String> lines = getLines(text);
        BufferedImage image = new BufferedImage(getLongest(lines) * letterWidth, lines.size() * letterHeight,
                BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setFont(new Font("arial", Font.BOLD, letterHeight));
        g.setColor(Color.WHITE);
        for (int line = 0; line < lines.size(); line++) {
            for (int c = 0; c < lines.get(line).length(); c++) {
                g.drawString(lines.get(line).charAt(c) + "", c * letterWidth, line * letterHeight + letterHeight);
            }
        }
        g.dispose();
        return image;
    }
}