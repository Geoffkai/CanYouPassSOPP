package gui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import logic.GameManager;
import logic.Question;
import logic.QuestionBank;
import logic.Player;

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

        // If a GameManager already exists (from previous screens), reuse it; otherwise create one
        logic.GameManager gm = GameState.getGameManager();
        if (gm == null) {
            // Create a temporary GameManager so we can derive the topics for TopicsPanel
            logic.QuestionBank bank = new logic.QuestionBank();
            logic.Player player = GameState.getPlayer();
            gm = new logic.GameManager(bank, player);
            // initialize using selected category if present
            logic.QuestionBank.Category cat = GameState.getCategory();
            if (cat != null) {
                gm.initializeGame(cat);
            } else {
                gm.initializeGame(logic.QuestionBank.Category.Theoretical);
            }
            // store so GameScreen reuses the same question selection
            GameState.setGameManager(gm);
        }

        // Get the available questions (should be 10: 2 per level L1-L5)
        java.util.List<logic.Question> selected = gm.getAvailableQuestions();

        // Group questions by level
        java.util.Map<String, java.util.List<logic.Question>> byLevel = new java.util.HashMap<>();
        for (logic.Question q : selected) {
            byLevel.computeIfAbsent(q.getDifficulty(), k -> new java.util.ArrayList<>()).add(q);
        }

        // For each level, pick up to two topics from selected questions; fallback to random if missing
        assignTopicsFromQuestions(Level5a, Level5b, 5, byLevel);
        assignTopicsFromQuestions(Level4a, Level4b, 4, byLevel);
        assignTopicsFromQuestions(Level3a, Level3b, 3, byLevel);
        assignTopicsFromQuestions(Level2a, Level2b, 2, byLevel);
        assignTopicsFromQuestions(Level1a, Level1b, 1, byLevel);

        // Set button bounds
        setButtonPositions();

        // Button listeners
        addListeners();

        // Add components
        add(Level5a);
        add(Level5b);
        add(Level4a);
        add(Level4b);
        add(Level3a);
        add(Level3b);
        add(Level2a);
        add(Level2b);
        add(Level1a);
        add(Level1b);

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
                level5aTopic = topicA;
                level5bTopic = topicB;
                break;
            }
            case 4: {
                level4aTopic = topicA;
                level4bTopic = topicB;
                break;
            }
            case 3: {
                level3aTopic = topicA;
                level3bTopic = topicB;
                break;
            }
            case 2: {
                level2aTopic = topicA;
                level2bTopic = topicB;
                break;
            }
            case 1: {
                level1aTopic = topicA;
                level1bTopic = topicB;
                break;
            }
            default:
                break;
        }
    }

    private void assignTopicsFromQuestions(JButton a, JButton b, int level,
            java.util.Map<String, java.util.List<Question>> byLevel) {
        String levelKey = "L" + level;
        java.util.List<Question> list = byLevel.getOrDefault(levelKey, new java.util.ArrayList<>());

        if (list.size() >= 2) {
            Question q1 = list.get(0);
            Question q2 = list.get(1);
            String abbr1 = mapTopicNameToAbbrev(q1.getTopic());
            String abbr2 = mapTopicNameToAbbrev(q2.getTopic());
            String key1 = abbr1 + level;
            String key2 = abbr2 + level;
            a.setIcon(icons.getOrDefault(key1, icons.get(randomKeysForLevel(level).get(0))));
            b.setIcon(icons.getOrDefault(key2, icons.get(randomKeysForLevel(level).get(1))));
            switch (level) {
            case 5:
                level5aTopic = key1;
                level5bTopic = key2;
                break;
            case 4:
                level4aTopic = key1;
                level4bTopic = key2;
                break;
            case 3:
                level3aTopic = key1;
                level3bTopic = key2;
                break;
            case 2:
                level2aTopic = key1;
                level2bTopic = key2;
                break;
            case 1:
                level1aTopic = key1;
                level1bTopic = key2;
                break;
            }
        } else if (list.size() == 1) {
            Question q1 = list.get(0);
            String abbr1 = mapTopicNameToAbbrev(q1.getTopic());
            String key1 = abbr1 + level;
            a.setIcon(icons.getOrDefault(key1, icons.get(randomKeysForLevel(level).get(0))));
            // pick a random other key for the second button
            List<String> keys = randomKeysForLevel(level);
            String key2 = keys.get(0).equals(key1) ? keys.get(1) : keys.get(0);
            b.setIcon(icons.get(key2));
            switch (level) {
            case 5:
                level5aTopic = key1;
                level5bTopic = key2;
                break;
            case 4:
                level4aTopic = key1;
                level4bTopic = key2;
                break;
            case 3:
                level3aTopic = key1;
                level3bTopic = key2;
                break;
            case 2:
                level2aTopic = key1;
                level2bTopic = key2;
                break;
            case 1:
                level1aTopic = key1;
                level1bTopic = key2;
                break;
            }
        } else {
            // no selected questions for this level — fallback to random
            assignRandomIcons(a, b, level);
        }
    }

    private String mapTopicNameToAbbrev(String topicName) {
        if (topicName == null) {
            return "Prod"; // default
        }
        String t = topicName.toLowerCase();
        if (t.contains("procedur") || t.contains("procedural")) {
            return "Prod";
        }
        if (t.contains("functional") || t.contains("func")) {
            return "Func";
        }
        if (t.contains("object") || t.contains("object-oriented")) {
            return "OOP";
        }
        if (t.contains("introduction") || t.contains("intro")) {
            return "Intro";
        }
        if (t.contains("event") || t.contains("evdr") || t.contains("event-driven")) {
            return "EVDR";
        }
        if (t.contains("imperative") || t.contains("declarative") || t.contains("ivd")) {
            return "IVD";
        }
        if (t.contains("component") || t.contains("mapping") || t.contains("map")) {
            return "MAP";
        }
        // fallback
        return "Prod";
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