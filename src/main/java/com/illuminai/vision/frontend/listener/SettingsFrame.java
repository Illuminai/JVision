package com.illuminai.vision.frontend.listener;

import com.illuminai.vision.frontend.Render;
import com.illuminai.vision.frontend.Settings;

import javax.swing.*;

public class SettingsFrame extends JFrame  {
    private Render render;
    private Settings settings;

    public SettingsFrame(Render r) {
        super("FÄISCHTER VODE SÄTTINGS");
        this.render = r;
        this.settings = r.getSettings().clone();
        init();
        this.requestFocus();
    }

    private void init() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        createContent();
        setVisible(true);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    private void createContent() {
        GroupLayout l = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(l);
        JPanel[] jPanels = new JPanel[10];

        GroupLayout.SequentialGroup sGP = l.createSequentialGroup();
        GroupLayout.ParallelGroup pGP = l.createParallelGroup();

        prepareSlider(jPanels,0, pGP, sGP,"Samples", 1,100,1).addChangeListener((e) -> this.settings.setSamples(((JSlider)e.getSource()).getValue()));

        for(int i = 1; i < jPanels.length; i++) {
            prepareSlider(jPanels, i, pGP, sGP, "NOT_USED", 0, 100, 5);
        }

        GroupLayout.ParallelGroup pG = l.createParallelGroup(GroupLayout.Alignment.CENTER);
        GroupLayout.SequentialGroup sG = l.createSequentialGroup();

        GroupLayout.ParallelGroup sGB = l.createParallelGroup();
        GroupLayout.ParallelGroup pGB = l.createParallelGroup();

        JButton b = new JButton("Accept");
        b.addActionListener((e) -> {
            //TODO only the difference should be changed, not all the settings
            //E. g. when setting it to pause, the opening the settings, then setting pause to false, the accepting the settings
            //will change the pause to true, although this was not meant
            this.render.setSettings(this.settings);
        });

        sGB.addComponent(b);
        pGB.addComponent(b);
        pG.addGroup(sGB);
        sG.addGroup(pGB);

        sG.addGroup(pGP);
        pG.addGroup(sGP);

        l.setHorizontalGroup(sG);
        l.setVerticalGroup(pG);

        l.setAutoCreateContainerGaps(true);
        l.setAutoCreateGaps(true);
    }

    private JSlider prepareSlider(JPanel[] p, int i, GroupLayout.Group pGP, GroupLayout.Group sGP, String description, int minimum, int maximum, int currValue) {
        p[i] = new JPanel();
        p[i].setLayout(new BoxLayout(p[i], BoxLayout.Y_AXIS));

        JLabel label = new JLabel();

        p[i].add(label);
        JSlider slider = new JSlider(JSlider.HORIZONTAL);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(maximum/4);
        slider.setMinorTickSpacing(maximum/10);
        p[i].add(slider);

        slider.addChangeListener((e) -> label.setText("<html>"+description + "<br>Current Value: " + slider.getValue()));

        pGP.addComponent(p[i],0,GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
        sGP.addComponent(p[i],0,GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);

        slider.setMinimum(minimum);
        slider.setMaximum(maximum);
        slider.setValue(currValue);

        return slider;
    }
}
