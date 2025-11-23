package gui;
import javax.swing.*;
import java.awt.*;

public class ScorePanels extends JPanel {
    BackgroundPanel backgroundPanel = new BackgroundPanel("src/img/ScorePanel/CorrectAnswer.png");
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    JLabel score = new JLabel("1000 "); // get score
    JLabel username = new JLabel("Merry"); //Temp, get username

    ImageIcon MBlue = new ImageIcon(new ImageIcon("src/img/ScorePanel/MainBlue.png").getImage().getScaledInstance(595, 97, java.awt.Image.SCALE_SMOOTH));
    ImageIcon PBlue = new ImageIcon(new ImageIcon("src/img/ScorePanel/PlayBlue.png").getImage().getScaledInstance(595, 97, java.awt.Image.SCALE_SMOOTH));
    ImageIcon MRed = new ImageIcon(new ImageIcon("src/img/ScorePanel/MainRed.png").getImage().getScaledInstance(595, 97, java.awt.Image.SCALE_SMOOTH));
    ImageIcon PRed = new ImageIcon(new ImageIcon("src/img/ScorePanel/PlayRed.png").getImage().getScaledInstance(595, 97, java.awt.Image.SCALE_SMOOTH));
    JButton MainMenu = new JButton();
    JButton PlayAgain = new JButton();
    Boolean Win = false;

    public ScorePanels() {
        setLayout(null);
        setBounds(0, 0, screenSize.width, screenSize.height);

        backgroundPanel.setBounds(0, 0, screenSize.width, screenSize.height);
        backgroundPanel.setLayout(null);

        score.setBounds(620, 845, 595, 57);
        score.setFont(new Font("Arial", Font.BOLD, 36));
        score.setForeground(Color.WHITE);
        score.setHorizontalAlignment(SwingConstants.CENTER);
        score.setVisible(true);

        username.setBounds(730, 707, 387, 64);
        username.setFont(new Font("Arial", Font.BOLD, 60));
        username.setForeground(new Color(255, 230, 66));
        username.setHorizontalAlignment(SwingConstants.CENTER);
        username.setVisible(true);

        MainMenu.setBounds(662, 758, 595, 97);
        PlayAgain.setBounds(662, 921, 595, 97);

        backgroundPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backgroundPanel.setBackground("src/img/ScorePanel/CurrentScore.png");
                backgroundPanel.add(score);
                backgroundPanel.add(username);
                backgroundPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(backgroundPanel);
                        topFrame.setContentPane(new TopicsPanel());
                        topFrame.validate();
                        topFrame.repaint();
                    }
                });
                revalidate();
                repaint();
            }
        });

        add(backgroundPanel);
        validate();
        repaint();
    }

    public void displayFinalScore() {
        username.setBounds(659, 540, 595, 64);
        score.setBounds(659, 627, 596, 64);
        score.setFont(new Font("Arial", Font.BOLD, 50));
        //put if-else here for win or lose
        if (Win){
            backgroundPanel.setBackground("src/img/ScorePanel/YouPass.png");
            MainMenu.setIcon(MBlue);
            PlayAgain.setIcon(PBlue);
        }else{
            backgroundPanel.setBackground("src/img/ScorePanel/YouFail.png");
            MainMenu.setIcon(MRed);
            PlayAgain.setIcon(PRed);
        }
        backgroundPanel.add(MainMenu);
        backgroundPanel.add(PlayAgain);
        revalidate();
        repaint();
    }
}
