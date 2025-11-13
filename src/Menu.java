package src;
import javax.swing.*;
import java.awt.*;

public class Menu extends JPanel {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    // Main Menu Panel
    BackgroundPanel mainMenu = new BackgroundPanel("src/img/mainMenu.png");

    // Exit Button
    ImageIcon exitIcon = new ImageIcon(new ImageIcon("src/img/Exit.png").getImage().getScaledInstance(596, 112, java.awt.Image.SCALE_SMOOTH));
    JButton exitButton = new JButton(exitIcon);

    // School Record Button
    ImageIcon sRIcon = new ImageIcon(new ImageIcon("src/img/SchoolRecord.png").getImage().getScaledInstance(596, 112, java.awt.Image.SCALE_SMOOTH));
    JButton sRButton = new JButton(sRIcon);

    // How To Play Button
    ImageIcon hTPIcon = new ImageIcon(new ImageIcon("src/img/HowToPlay.png").getImage().getScaledInstance(596, 112, java.awt.Image.SCALE_SMOOTH));
    JButton hTPButton = new JButton(hTPIcon);

    // Classmate Button
    ImageIcon cMIcon = new ImageIcon(new ImageIcon("src/img/Classmate.png").getImage().getScaledInstance(596, 112, java.awt.Image.SCALE_SMOOTH));
    JButton cMButton = new JButton(cMIcon);

    // Play Button
    ImageIcon playIcon = new ImageIcon(new ImageIcon("src/img/Play.png").getImage().getScaledInstance(668, 152, java.awt.Image.SCALE_SMOOTH));
    JButton playButton = new JButton(playIcon);


    public Menu() {
        setLayout(null);
        setBounds(0, 0, screenSize.width, screenSize.height);

        // Main Menu Panel
        mainMenu.setLayout(null);
        mainMenu.setBounds(0, 0, screenSize.width, screenSize.height);

        // Buttons
        mainMenu.add(exitButton);
        mainMenu.add(sRButton);
        mainMenu.add(hTPButton);
        mainMenu.add(cMButton);
        mainMenu.add(playButton);

        menuButton();
        add(mainMenu);
    }

    public void menuButton() {
        exitButton.setFocusable(false);
        exitButton.setBounds(144, 780, 596, 112);
        exitButton.addActionListener(e -> System.exit(0));

        sRButton.setFocusable(false);
        sRButton.setBounds(144, 641, 596, 112);
        sRButton.addActionListener(e -> {});

        hTPButton.setFocusable(false);
        hTPButton.setBounds(144, 501, 596, 112);
        hTPButton.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.setContentPane(new TopicsPanel());
            topFrame.validate();
            topFrame.repaint();
            topFrame.setVisible(true);
        });

        cMButton.setFocusable(false);
        cMButton.setBounds(144, 359, 596, 112);
        cMButton.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.setContentPane(new ClassmatesPanel());
            topFrame.validate();
            topFrame.repaint();
            topFrame.setVisible(true);
        });

        playButton.setFocusable(false);
        playButton.setBounds(108, 179, 668, 152);
        playButton.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.setContentPane(new PlayPanel());
            topFrame.validate();
            topFrame.repaint();
            topFrame.setVisible(true);
        });


    }
}

