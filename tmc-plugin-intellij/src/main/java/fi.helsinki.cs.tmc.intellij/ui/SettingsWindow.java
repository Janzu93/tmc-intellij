package fi.helsinki.cs.tmc.intellij.ui;


import fi.helsinki.cs.tmc.intellij.ui.elements.SettingsPanel;

import javax.swing.*;
import java.awt.*;

public class SettingsWindow {

    JFrame frame;


    public SettingsWindow() {
            frame = new JFrame();
            JPanel panel = new SettingsPanel(frame).getPanel();

            frame.add(panel);
            frame.setTitle("TMC Settings");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.setResizable(false);
            frame.setSize(new Dimension(800, 500));
        }

    public boolean isClosed() {
        return (frame == null || !frame.isVisible());
    }

    public void show() {
        frame.setVisible(false);
        frame.setVisible(true);

    }
}

