package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;

import gui.audio.SoundManager;

public class Main {
    public static void main(String[] args) {
        System.setProperty("sun.java2d.uiScale", "1.0");

        // Start background music
        SoundManager.playBackgroundMusic("src/gui/audio/background_music.wav");

        JFrame frame = new JFrame("Game Splash Screen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                SoundManager.stopBackgroundMusic();
            }
        });

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize.width, screenSize.height);

        // Background panel
        BackgroundPanel splashScreen = new BackgroundPanel("src/img/InitialImg/Splash.png");
        splashScreen.setBounds(0, 0, screenSize.width, screenSize.height);
        splashScreen.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                frame.setContentPane(new Menu());
                frame.revalidate();
                frame.repaint();
            }
        });

        frame.setContentPane(splashScreen);
        frame.pack();

        // Exit button
        frame.setLayout(null);
        JButton exitButton = new JButton("X");
        exitButton.setFocusPainted(false);
        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.setBounds(screenSize.width - 60, 20, 50, 50); // position top-right
        exitButton.addActionListener(e -> System.exit(0));
        frame.add(exitButton);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }
}
