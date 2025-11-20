package gui;

import java.awt.*;
import javax.swing.*;

/**
 * GUI entry point for the Programming Paradigms Quiz Game.
 * Called by the unified Main launcher.
 */
public class GUIMain {
    public static void main(String[] args) {
        System.setProperty("sun.java2d.uiScale", "1.0");
        JFrame frame = new JFrame("Game Splash Screen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize.width, screenSize.height);

        // Background panel
        BackgroundPanel splashScreen = new BackgroundPanel("src/img/InitialImg/Splash.png");
        splashScreen.setBounds(0, 0, screenSize.width, screenSize.height);
        splashScreen.setLayout(null);
        splashScreen.setOpaque(false);

        // Make splash screen clickable
        splashScreen.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                frame.setContentPane(new Menu());
                frame.validate();
                frame.repaint();
            }
        });

        // Exit button (add to splash screen, not frame)
        JButton exitButton = new JButton("X");
        exitButton.setFocusPainted(false);
        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.setBounds(screenSize.width - 60, 20, 50, 50);
        exitButton.addActionListener(e -> System.exit(0));
        splashScreen.add(exitButton);

        frame.setContentPane(splashScreen);
        frame.setLayout(null);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }
}
