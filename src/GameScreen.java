package src;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GameScreen extends JPanel {
    BackgroundPanel backgroundPanel = new BackgroundPanel("src/img/InitialImages/GameScreen.png");
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

    //Image for Characters and Levels
    private final Map<String, ImageIcon> characters = new HashMap<>();
    private final Map<String, ImageIcon> levels = new HashMap<>();
    JLabel character = new JLabel();
    JLabel labels = new JLabel();
    JLabel score = new JLabel();

    public GameScreen() {
        setLayout(null);
        setBounds(0, 0, screenSize.width, screenSize.height);
        backgroundPanel.setBounds(0, 0, screenSize.width, screenSize.height);
        backgroundPanel.setLayout(null);

        displayChoiceButtons();
        displayDebugTools();
        displayTopButtons();
        loadCharacters();
        loadLevels();
        displayLevel();
        displayCharacter();
        displayScore();

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
        add(labels);
        add(score);
        add(backgroundPanel);
        validate();
        repaint();
    }

    public void displayChoiceButtons(){
        //Choices Buttons
        Abtn.setBounds(137, 859, 591, 84);
        Abtn.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.setContentPane(new ScorePanels());
            topFrame.validate();
            topFrame.repaint();
            topFrame.setVisible(true);
        });

        Bbtn.setBounds(774, 859, 591, 84);
        Cbtn.setBounds(137, 960, 591, 84);
        Dbtn.setBounds(774, 960, 591, 84);
    }

    public void displayDebugTools() {
        //Debug Tools
        Refactor.setBounds(1531, 671, 324, 50);
        Console.setBounds(1531, 745, 324, 50);
        CtrlC.setBounds(1531, 818, 324, 50);
        AutoDebug.setBounds(1531, 892, 324, 50);
        Return.setBounds(1531, 966, 324, 50);
    }

    public void displayTopButtons(){
        //Other Buttons
        RetryBtn.setBounds(44, 64, 93, 93);
        RetryBtn.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.setContentPane(new PlayPanel());
            topFrame.validate();
            topFrame.repaint();
            topFrame.setVisible(true);
        });

        MenuBtn.setBounds(172, 64, 93, 93);
        MenuBtn.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.setContentPane(new Menu());
            topFrame.validate();
            topFrame.repaint();
            topFrame.setVisible(true);
        });
        MuteBtn.setBounds(300, 64, 93, 93);
    }

    public void loadCharacters() {
        characters.put("Geoff", new ImageIcon(new ImageIcon("src/img/Character/Geoff.png").getImage().getScaledInstance(411, 426, Image.SCALE_SMOOTH)));
        characters.put("Yvonne", new ImageIcon(new ImageIcon("src/img/Character/Yvonne.png").getImage().getScaledInstance(411, 426, Image.SCALE_SMOOTH)));
        characters.put("Anon",   new ImageIcon(new ImageIcon("src/img/Character/Anon.png").getImage().getScaledInstance(411, 426, Image.SCALE_SMOOTH)));
        characters.put("Elmer",  new ImageIcon(new ImageIcon("src/img/Character/Elmer.png").getImage().getScaledInstance(411, 426, Image.SCALE_SMOOTH)));
        characters.put("Merry",  new ImageIcon(new ImageIcon("src/img/Character/Merry.png").getImage().getScaledInstance(411, 426, Image.SCALE_SMOOTH)));
    }

    public void displayCharacter() {
        character.setBounds(1501, 213, 411, 426);
        character.setOpaque(true);

        ImageIcon icon = characters.get(GameState.getCharacter());
        character.setIcon(icon);
    }

    public void loadLevels() {
        levels.put("Prod5", new ImageIcon(new ImageIcon("src/img/topics/Prod5.png")
                .getImage().getScaledInstance(746, 93, Image.SCALE_SMOOTH)));

        levels.put("Func5", new ImageIcon(new ImageIcon("src/img/topics/Func5.png")
                .getImage().getScaledInstance(746, 93, Image.SCALE_SMOOTH)));

        levels.put("OOP4", new ImageIcon(new ImageIcon("src/img/topics/OOP4.png")
                .getImage().getScaledInstance(746, 93, Image.SCALE_SMOOTH)));

        levels.put("Imp4", new ImageIcon(new ImageIcon("src/img/topics/Imp4.png")
                .getImage().getScaledInstance(746, 93, Image.SCALE_SMOOTH)));

        levels.put("Imp3", new ImageIcon(new ImageIcon("src/img/topics/Imp3.png")
                .getImage().getScaledInstance(746, 93, Image.SCALE_SMOOTH)));

        levels.put("Dec3", new ImageIcon(new ImageIcon("src/img/topics/Dec3.png")
                .getImage().getScaledInstance(746, 93, Image.SCALE_SMOOTH)));

        levels.put("Evdr2", new ImageIcon(new ImageIcon("src/img/topics/Evdr2.png")
                .getImage().getScaledInstance(746, 93, Image.SCALE_SMOOTH)));

        levels.put("OOP2", new ImageIcon(new ImageIcon("src/img/topics/OOP2.png")
                .getImage().getScaledInstance(746, 93, Image.SCALE_SMOOTH)));

        levels.put("Prod1", new ImageIcon(new ImageIcon("src/img/topics/Prod1.png")
                .getImage().getScaledInstance(746, 93, Image.SCALE_SMOOTH)));

        levels.put("Func1", new ImageIcon(new ImageIcon("src/img/topics/Func1.png")
                .getImage().getScaledInstance(746, 93, Image.SCALE_SMOOTH)));
    }

    public void displayLevel() {
        labels.setBounds(556, 64, 746, 87);
        labels.setOpaque(true);

        ImageIcon levelIcon = levels.get(GameState.getLevel());
        if (levelIcon != null) {
            labels.setIcon(levelIcon);
        } else {
            labels.setIcon(null);
        }
    }

    public void displayScore(){
        score.setText("SCORE: 1000"); // add nalang score getter dito jop
        score.setBounds(1460, 80, 422, 65);
        score.setFont(new Font("Arial", Font.BOLD, 50));
        score.setForeground(Color.WHITE);
        score.setHorizontalAlignment(SwingConstants.CENTER);
    }
}
