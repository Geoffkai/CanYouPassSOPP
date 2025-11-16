package src.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlayPanel extends JPanel {
    BackgroundPanel backgroundPanel = new BackgroundPanel("src/img/ClickableClassmates.png");
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public PlayPanel() {
        setLayout(null);
        setBounds(0, 0, screenSize.width, screenSize.height);
        backgroundPanel.setBounds(0, 0, screenSize.width, screenSize.height);
        backgroundPanel.setLayout(null);

        String[] names = { "Geoff", "Yvonne", "Anon", "Elmer", "Merry" };
        int[][] bounds = {
                { 66, 341, 320, 393 }, // Geoff
                { 432, 559, 320, 363 }, // Yvonne
                { 797, 339, 320, 389 }, // Anon
                { 1162, 559, 325, 363 }, // Elmer
                { 1537, 348, 320, 392 } // Merry
        };

        for (int i = 0; i < names.length; i++) {
            JPanel character = new JPanel();
            character.setBounds(bounds[i][0], bounds[i][1], bounds[i][2], bounds[i][3]);
            character.setOpaque(false);

            final String name = names[i];
            character.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(character);

                    switch (name) {
                        case "Geoff":
                            topFrame.setContentPane(new CategoryPanel());
                            JOptionPane.showMessageDialog(null, "You clicked on Geoff!");
                            GameState.setCharacter("Geoff");
                            break;
                        case "Yvonne":
                            topFrame.setContentPane(new CategoryPanel());
                            JOptionPane.showMessageDialog(null, "You clicked on Yvonne!");
                            GameState.setCharacter("Yvonne");
                            break;
                        case "Anon":
                            topFrame.setContentPane(new CategoryPanel());
                            JOptionPane.showMessageDialog(null, "You clicked on Anon!");
                            GameState.setCharacter("Anon");
                            break;
                        case "Elmer":
                            topFrame.setContentPane(new CategoryPanel());
                            JOptionPane.showMessageDialog(null, "You clicked on Elmer!");
                            GameState.setCharacter("Elmer");
                            break;
                        case "Merry":
                            topFrame.setContentPane(new CategoryPanel());
                            JOptionPane.showMessageDialog(null, "You clicked on Merry!");
                            GameState.setCharacter("Merry");
                            break;
                    }
                    topFrame.validate();
                    topFrame.repaint();
                }
            });

            backgroundPanel.add(character);
        }

        add(backgroundPanel);

        validate();
        repaint();
    }
}
