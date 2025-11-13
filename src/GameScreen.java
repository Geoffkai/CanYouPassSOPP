package src;
import javax.swing.*;
import java.awt.*;

public class GameScreen extends JPanel {
    BackgroundPanel backgroundPanel = new BackgroundPanel("src/img/GameScreen.png");
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    //Choices image
    ImageIcon A = new ImageIcon(new ImageIcon("src/img/Choices/A.png").getImage().getScaledInstance(591, 84, java.awt.Image.SCALE_SMOOTH));
    JButton Abtn = new JButton(A);
    ImageIcon B = new ImageIcon(new ImageIcon("src/img/Choices/B.png").getImage().getScaledInstance(591, 84, java.awt.Image.SCALE_SMOOTH));
    JButton Bbtn = new JButton(B);
    ImageIcon C = new ImageIcon(new ImageIcon("src/img/Choices/C.png").getImage().getScaledInstance(591, 84, java.awt.Image.SCALE_SMOOTH));
    JButton Cbtn = new JButton(C);
    ImageIcon D = new ImageIcon(new ImageIcon("src/img/Choices/D.png").getImage().getScaledInstance(591, 84, java.awt.Image.SCALE_SMOOTH));
    JButton Dbtn = new JButton(D);

    //Debug Tools image
    ImageIcon Ref = new ImageIcon(new ImageIcon("src/img/Debug Tools/Ref.png").getImage().getScaledInstance(324, 50, java.awt.Image.SCALE_SMOOTH));
    JButton Refactor = new JButton(Ref);
    ImageIcon Cons = new ImageIcon(new ImageIcon("src/img/Debug Tools/Cons.png").getImage().getScaledInstance(324, 50, java.awt.Image.SCALE_SMOOTH));
    JButton Console = new JButton(Cons);
    ImageIcon Copy = new ImageIcon(new ImageIcon("src/img/Debug Tools/CtrlC.png").getImage().getScaledInstance(324, 50, java.awt.Image.SCALE_SMOOTH));
    JButton CtrlC = new JButton(Copy);
    ImageIcon AuDeb = new ImageIcon(new ImageIcon("src/img/Debug Tools/AuDeb.png").getImage().getScaledInstance(324, 50, java.awt.Image.SCALE_SMOOTH));
    JButton AutoDebug = new JButton(AuDeb);
    ImageIcon Ret = new ImageIcon(new ImageIcon("src/img/Debug Tools/Ret.png").getImage().getScaledInstance(324, 50, java.awt.Image.SCALE_SMOOTH));
    JButton Return = new JButton(Ret);

    //Other Buttons
    ImageIcon Menu = new ImageIcon(new ImageIcon("src/img/Buttons/Menu.png").getImage().getScaledInstance(93, 93, java.awt.Image.SCALE_SMOOTH));
    JButton MenuBtn = new JButton(Menu);
    ImageIcon Retry = new ImageIcon(new ImageIcon("src/img/Buttons/Retry.png").getImage().getScaledInstance(93, 93, java.awt.Image.SCALE_SMOOTH));
    JButton RetryBtn = new JButton(Retry);
    ImageIcon Mute = new ImageIcon(new ImageIcon("src/img/Buttons/Mute.png").getImage().getScaledInstance(93, 93, java.awt.Image.SCALE_SMOOTH));
    JButton MuteBtn = new JButton(Mute);

    JPanel character = new JPanel();


    public GameScreen() {
        setLayout(null);
        setBounds(0, 0, screenSize.width, screenSize.height);
        backgroundPanel.setBounds(0, 0, screenSize.width, screenSize.height);
        backgroundPanel.setLayout(null);

        //Choices Buttons
        Abtn.setBounds(137, 859, 591, 84);
        Bbtn.setBounds(774, 859, 591, 84);
        Cbtn.setBounds(137, 960, 591, 84);
        Dbtn.setBounds(774, 960, 591, 84);

        //Debug Tools
        Refactor.setBounds(1531, 671, 324, 50);
        Console.setBounds(1531, 745, 324, 50);
        CtrlC.setBounds(1531, 818, 324, 50);
        AutoDebug.setBounds(1531, 892, 324, 50);
        Return.setBounds(1531, 966, 324, 50);

        //Other Buttons
        RetryBtn.setBounds(44, 64, 93, 93);
        MenuBtn.setBounds(172, 64, 93, 93);
        MuteBtn.setBounds(300, 64, 93, 93);

        display();

        add(Abtn);
        add(Bbtn);
        add(Cbtn);
        add(Dbtn);
        add(Refactor);
        add(Console);
        add(CtrlC);
        add(AutoDebug);
        add(Return);
        add(RetryBtn);
        add(MenuBtn);
        add(MuteBtn);
        add(character);
        add(backgroundPanel);
        validate();
        repaint();
    }

    public void display() {
        //Character Display
        character.setBounds(1501, 213, 411, 426);
        character.setOpaque(true);
    }
}
