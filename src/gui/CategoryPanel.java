package src.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class CategoryPanel extends JPanel {
    BackgroundPanel backgroundPanel = new BackgroundPanel("src/img/Category.png");
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    JTextField usernameField = new JTextField("ENTER YOUR USERNAME...");

    ImageIcon selectIcon = new ImageIcon(
            new ImageIcon("src/img/Select.png").getImage().getScaledInstance(382, 93, Image.SCALE_SMOOTH));
    ImageIcon selectActiveIcon = new ImageIcon(
            new ImageIcon("src/img/Selected.png").getImage().getScaledInstance(382, 93, Image.SCALE_SMOOTH));

    final JButton[] activeButton = { null };

    public CategoryPanel() {
        setLayout(null);
        setBounds(0, 0, screenSize.width, screenSize.height);
        backgroundPanel.setBounds(0, 0, screenSize.width, screenSize.height);
        backgroundPanel.setLayout(null);

        JButton selectButton1 = new JButton(selectIcon);
        selectButton1.setBounds(332, 771, 382, 93);
        selectButton1.addActionListener(e -> {
            if (activeButton[0] != null) {
                activeButton[0].setIcon(selectIcon);
            }

            selectButton1.setIcon(selectActiveIcon);
            activeButton[0] = selectButton1;
        });

        JButton selectButton2 = new JButton(selectIcon);
        selectButton2.setBounds(1078, 770, 382, 93);
        selectButton2.addActionListener(e -> {
            if (activeButton[0] != null) {
                activeButton[0].setIcon(selectIcon);
            }

            selectButton2.setIcon(selectActiveIcon);
            activeButton[0] = selectButton2;
        });

        textField();
        add(selectButton1);
        add(selectButton2);
        add(usernameField);
        add(backgroundPanel);

        validate();
        repaint();
    }

    public void textField() {
        usernameField.setBounds(419, 943, 954, 65);
        usernameField.setFont(new Font("Arial", Font.ITALIC, 20));
        usernameField.setHorizontalAlignment(JTextField.CENTER);
        usernameField.setForeground(Color.GRAY);
        usernameField.setOpaque(false);
        usernameField.setCaretColor(Color.BLACK);
        usernameField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Add placeholder behavior
        usernameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (usernameField.getText().equals("ENTER YOUR USERNAME...")) {
                    usernameField.setText("");
                    usernameField.setFont(new Font("Arial", Font.PLAIN, 25));
                    usernameField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (usernameField.getText().isEmpty()) {
                    usernameField.setText("ENTER YOUR USERNAME...");
                    usernameField.setFont(new Font("Arial", Font.ITALIC, 25));
                    usernameField.setForeground(Color.GRAY);
                }
            }
        });

        usernameField.addActionListener(e -> {
            if (activeButton[0] == null) {
                JOptionPane.showMessageDialog(null, "Please select a Category first!");
                return;
            }

            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(usernameField);
            topFrame.setContentPane(new TopicsPanel());
            topFrame.validate();
            topFrame.repaint();
        });
    }
}
