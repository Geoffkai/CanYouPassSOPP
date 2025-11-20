package gui;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class TopicsPanel extends JPanel {

    BackgroundPanel backgroundPanel = new BackgroundPanel("src/img/InitialImg/Topics.png");
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    // Topic names
    String[] names = { "EVDR", "Func", "Intro", "IVD", "MAP", "OOP", "Prod" };
    int maxLevel = 5;

    // Storage for icons
    Map<String, ImageIcon> icons = new HashMap<>();


    // Storage for selected topics for buttons
    String level5aTopic, level5bTopic;
    String level4aTopic, level4bTopic;
    String level3aTopic, level3bTopic;
    String level2aTopic, level2bTopic;
    String level1aTopic, level1bTopic;

    // Buttons
    JButton Level5a = new JButton();
    JButton Level5b = new JButton();
    JButton Level4a = new JButton();
    JButton Level4b = new JButton();
    JButton Level3a = new JButton();
    JButton Level3b = new JButton();
    JButton Level2a = new JButton();
    JButton Level2b = new JButton();
    JButton Level1a = new JButton();
    JButton Level1b = new JButton();

    public TopicsPanel() {

        setLayout(null);
        setBounds(0, 0, screenSize.width, screenSize.height);

        backgroundPanel.setBounds(0, 0, screenSize.width, screenSize.height);
        backgroundPanel.setLayout(null);

        // Load all icons
        for (int level = 1; level <= maxLevel; level++) {
            for (String name : names) {
                String key = name + level; // e.g. EVDR5
                String path = "src/img/Level " + level + "/" + name + " " + level + ".png";

                Image scaled = new ImageIcon(path)
                        .getImage()
                        .getScaledInstance(746, 93, Image.SCALE_SMOOTH);

                icons.put(key, new ImageIcon(scaled));
            }
        }

        // Assign random icons + topics
        assignRandomIcons(Level5a, Level5b, 5);
        assignRandomIcons(Level4a, Level4b, 4);
        assignRandomIcons(Level3a, Level3b, 3);
        assignRandomIcons(Level2a, Level2b, 2);
        assignRandomIcons(Level1a, Level1b, 1);

        // Set button bounds
        setButtonPositions();

        // Button listeners
        addListeners();

        // Add components
        add(Level5a); add(Level5b);
        add(Level4a); add(Level4b);
        add(Level3a); add(Level3b);
        add(Level2a); add(Level2b);
        add(Level1a); add(Level1b);

        add(backgroundPanel);
        validate();
        repaint();
    }

    // Returns an array of two random keys for a given level
    private List<String> randomKeysForLevel(int level) {
        List<String> keys = new ArrayList<>();

        for (String name : names) {
            keys.add(name + level); // EVDR5, MAP5, Intro5...
        }

        Collections.shuffle(keys);
        return keys;
    }

    private void assignRandomIcons(JButton a, JButton b, int level) {
        List<String> keys = randomKeysForLevel(level);

        String topicA = keys.get(0);
        String topicB = keys.get(1);

        ImageIcon iconA = icons.get(topicA);
        ImageIcon iconB = icons.get(topicB);

        a.setIcon(iconA);
        b.setIcon(iconB);

        // Save topics into variables
        switch (level) {
            case 5: {
                level5aTopic = topicA; level5bTopic = topicB;
                break;
            }
            case 4: {
                level4aTopic = topicA; level4bTopic = topicB;
                break;
            }
            case 3: {
                level3aTopic = topicA; level3bTopic = topicB;
                break;
            }
            case 2: {
                level2aTopic = topicA; level2bTopic = topicB;
                break;
            }
            case 1: {
                level1aTopic = topicA; level1bTopic = topicB;
                break;
            }
            default:
                break;
        }
    }

    private void openGameScreen(JButton btn) {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(btn);
        topFrame.setContentPane(new GameScreen());
        topFrame.validate();
        topFrame.repaint();
    }

    // Layout positions
    private void setButtonPositions() {
        Level5a.setBounds(151, 275, 746, 93);
        Level5b.setBounds(1022, 275, 746, 93);

        Level4a.setBounds(151, 427, 746, 93);
        Level4b.setBounds(1022, 427, 746, 93);

        Level3a.setBounds(151, 577, 746, 93);
        Level3b.setBounds(1022, 577, 746, 93);

        Level2a.setBounds(151, 727, 746, 93);
        Level2b.setBounds(1022, 727, 746, 93);

        Level1a.setBounds(151, 877, 746, 93);
        Level1b.setBounds(1022, 877, 746, 93);
    }

    private void addListeners() {
        Level5a.addActionListener(e -> {
            GameState.setLevel("Level 5");
            GameState.setTopic(level5aTopic);
            GameState.setTopicIcon(icons.get(level5aTopic));
            openGameScreen(Level5a);
        });

        Level5b.addActionListener(e -> {
            GameState.setLevel("Level 5");
            GameState.setTopic(level5bTopic);
            GameState.setTopicIcon(icons.get(level5bTopic));
            openGameScreen(Level5b);
        });

        Level4a.addActionListener(e -> {
            GameState.setLevel("Level 4");
            GameState.setTopic(level4aTopic);
            GameState.setTopicIcon(icons.get(level4aTopic));
            openGameScreen(Level4a);
        });

        Level4b.addActionListener(e -> {
            GameState.setLevel("Level 4");
            GameState.setTopic(level4bTopic);
            GameState.setTopicIcon(icons.get(level4bTopic));
            openGameScreen(Level4b);
        });

        Level3a.addActionListener(e -> {
            GameState.setLevel("Level 3");
            GameState.setTopic(level3aTopic);
            GameState.setTopicIcon(icons.get(level3aTopic));
            openGameScreen(Level3a);
        });

        Level3b.addActionListener(e -> {
            GameState.setLevel("Level 3");
            GameState.setTopic(level3bTopic);
            GameState.setTopicIcon(icons.get(level3bTopic));
            openGameScreen(Level3b);
        });

        Level2a.addActionListener(e -> {
            GameState.setLevel("Level 2");
            GameState.setTopic(level2aTopic);
            GameState.setTopicIcon(icons.get(level2aTopic));
            openGameScreen(Level2a);
        });

        Level2b.addActionListener(e -> {
            GameState.setLevel("Level 2");
            GameState.setTopic(level2bTopic);
            GameState.setTopicIcon(icons.get(level2bTopic));
            openGameScreen(Level2b);
        });

        Level1a.addActionListener(e -> {
            GameState.setLevel("Level 1");
            GameState.setTopic(level1aTopic);
            GameState.setTopicIcon(icons.get(level1aTopic));
            openGameScreen(Level1a);
        });

        Level1b.addActionListener(e -> {
            GameState.setLevel("Level 1");
            GameState.setTopic(level1bTopic);
            GameState.setTopicIcon(icons.get(level1bTopic));
            openGameScreen(Level1b);
        });
    }
}