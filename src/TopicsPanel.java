import javax.swing.*;
import java.awt.*;

public class TopicsPanel extends JPanel {
    BackgroundPanel backgroundPanel = new BackgroundPanel("src/img/Topics.png");
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    ImageIcon Prod5 = new ImageIcon(new ImageIcon("src/img/topics/Prod5.png").getImage().getScaledInstance(746, 93, java.awt.Image.SCALE_SMOOTH));
    JButton Prod5btn = new JButton(Prod5);

    ImageIcon Func5 = new ImageIcon(new ImageIcon("src/img/topics/Func5.png").getImage().getScaledInstance(746, 93, java.awt.Image.SCALE_SMOOTH));
    JButton Func5btn = new JButton(Func5);

    ImageIcon OOP4 = new ImageIcon(new ImageIcon("src/img/topics/OOP4.png").getImage().getScaledInstance(746, 93, java.awt.Image.SCALE_SMOOTH));
    JButton OOP4btn = new JButton(OOP4);

    ImageIcon Imp4 = new ImageIcon(new ImageIcon("src/img/topics/Imp4.png").getImage().getScaledInstance(746, 93, java.awt.Image.SCALE_SMOOTH));
    JButton Imp4btn = new JButton(Imp4);

    ImageIcon Imp3 = new ImageIcon(new ImageIcon("src/img/topics/Imp3.png").getImage().getScaledInstance(746, 93, java.awt.Image.SCALE_SMOOTH));
    JButton Imp3btn = new JButton(Imp3);

    ImageIcon Dec3 = new ImageIcon(new ImageIcon("src/img/topics/Dec3.png").getImage().getScaledInstance(746, 93, java.awt.Image.SCALE_SMOOTH));
    JButton Dec3btn = new JButton(Dec3);

    ImageIcon Evdr2 = new ImageIcon(new ImageIcon("src/img/topics/Evdr2.png").getImage().getScaledInstance(746, 93, java.awt.Image.SCALE_SMOOTH));
    JButton Evdr2btn = new JButton(Evdr2);

    ImageIcon OOP2 = new ImageIcon(new ImageIcon("src/img/topics/OOP2.png").getImage().getScaledInstance(746, 93, java.awt.Image.SCALE_SMOOTH));
    JButton OOP2btn = new JButton(OOP2);

    ImageIcon Prod1 = new ImageIcon(new ImageIcon("src/img/topics/Prod1.png").getImage().getScaledInstance(746, 93, java.awt.Image.SCALE_SMOOTH));
    JButton Prod1btn = new JButton(Prod1);

    ImageIcon Func1 = new ImageIcon(new ImageIcon("src/img/topics/Func1.png").getImage().getScaledInstance(746, 93, java.awt.Image.SCALE_SMOOTH));
    JButton Func1btn = new JButton(Func1);

    public TopicsPanel() {
        setLayout(null);
        setBounds(0, 0, screenSize.width, screenSize.height);
        backgroundPanel.setBounds(0, 0, screenSize.width, screenSize.height);
        backgroundPanel.setLayout(null);

        Prod5btn.setBounds(151, 275, 746, 93);
        Prod5btn.addActionListener(e -> {
            GameState.setLevel("Prod5");
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(Prod5btn);
            topFrame.setContentPane(new GameScreen());
            topFrame.validate();
            topFrame.repaint();
        });

        Func5btn.setBounds(1022, 275, 746, 93);
        Func5btn.addActionListener(e -> {
            GameState.setLevel("Func5");
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(Prod5btn);
            topFrame.setContentPane(new GameScreen());
            topFrame.validate();
            topFrame.repaint();
        });

        OOP4btn.setBounds(151, 427, 746, 93);
        OOP4btn.addActionListener(e -> {
            GameState.setLevel("OOP4");
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(Prod5btn);
            topFrame.setContentPane(new GameScreen());
            topFrame.validate();
            topFrame.repaint();
        });

        Imp4btn.setBounds(1022, 427, 746, 93);
        Imp4btn.addActionListener(e -> {
           GameState.setLevel("Imp4");
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(Prod5btn);
            topFrame.setContentPane(new GameScreen());
            topFrame.validate();
            topFrame.repaint();
        });

        Imp3btn.setBounds(151, 577, 746, 93);
        Imp3btn.addActionListener(e -> {
            GameState.setLevel("Imp3");
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(Prod5btn);
            topFrame.setContentPane(new GameScreen());
            topFrame.validate();
            topFrame.repaint();
        });

        Dec3btn.setBounds(1022, 577, 746, 93);
        Dec3btn.addActionListener(e -> {
            GameState.setLevel("Dec3");
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(Prod5btn);
            topFrame.setContentPane(new GameScreen());
            topFrame.validate();
            topFrame.repaint();
        });

        Evdr2btn.setBounds(151, 727, 746, 93);
        Evdr2btn.addActionListener(e -> {
            GameState.setLevel("Evdr2");
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(Prod5btn);
            topFrame.setContentPane(new GameScreen());
            topFrame.validate();
            topFrame.repaint();
        });

        OOP2btn.setBounds(1022, 727, 746, 93);
        OOP2btn.addActionListener(e -> {
            GameState.setLevel("OOP2");
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(Prod5btn);
            topFrame.setContentPane(new GameScreen());
            topFrame.validate();
            topFrame.repaint();
        });

        Prod1btn.setBounds(151, 877, 746, 93);
        Prod1btn.addActionListener(e -> {
            GameState.setLevel("Prod1");
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(Prod5btn);
            topFrame.setContentPane(new GameScreen());
            topFrame.validate();
            topFrame.repaint();
        });

        Func1btn.setBounds(1022, 877, 746, 93);
        Func1btn.addActionListener(e -> {
            GameState.setLevel("Func1");
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(Prod5btn);
            topFrame.setContentPane(new GameScreen());
            topFrame.validate();
            topFrame.repaint();
        });

        add(Prod5btn);
        add(Func5btn);
        add(OOP4btn);
        add(Imp4btn);
        add(Imp3btn);
        add(Dec3btn);
        add(Evdr2btn);
        add(OOP2btn);
        add(Prod1btn);
        add(Func1btn);
        add(backgroundPanel);
        validate();
        repaint();
    }
}
