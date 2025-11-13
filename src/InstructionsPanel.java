package src;
import javax.swing.*;
import java.awt.*;

public class InstructionsPanel extends JPanel {
    BackgroundPanel backgroundPanel = new BackgroundPanel("src/img/Instructions.png");
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public InstructionsPanel() {
        setLayout(null);
        setBounds(0, 0, screenSize.width, screenSize.height);
        backgroundPanel.setBounds(0, 0, screenSize.width, screenSize.height);
        backgroundPanel.setLayout(null);

        add(backgroundPanel);

        // add back button here
        JButton exitButton = new JButton("X");
        exitButton.setFocusPainted(false);
        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.YELLOW);
        exitButton.setBounds(screenSize.width - 60, 20, 50, 50); // position top-right


        exitButton.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.setContentPane(new Menu());
            topFrame.validate();
            topFrame.repaint();
        });

        backgroundPanel.add(exitButton);
        validate();
        repaint();
    }
}
