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

import static gui.GameState.selectedCategory;
import logic.GameManager;
import logic.Question;
import logic.QuestionBank;

public class TopicsPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    BackgroundPanel backgroundPanel = new BackgroundPanel("src/img/InitialImg/Topics.png");
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    // Topic names — must match your image naming conventions
    String[] theoNames = { "EVDR", "Func", "Intro", "IVD", "MAP", "OOP", "Prod" };
    String[] progNames = { "CTO", "OTC", "ML" };
    int maxLevel = 5;

    // Storage for icons
    Map<String, ImageIcon> icons = new HashMap<>();

    // Storage for selected topics for buttons (topic codes like "Prod2", "Func2")
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

    // the GameManager for this screen
    private GameManager gm;
    private String[] currentNames;

    public TopicsPanel() {
        setLayout(null);
        setBounds(0, 0, screenSize.width, screenSize.height);

        backgroundPanel.setBounds(0, 0, screenSize.width, screenSize.height);
        backgroundPanel.setLayout(null);

        // Load all icons once
        currentNames = (selectedCategory == QuestionBank.Category.Programming) ? progNames : theoNames;
        loadAllIcons();

        // Ensure there is a GameManager available in GameState for the chosen category
        gm = GameState.getGameManager();
        if (gm == null) {
            logic.QuestionBank bank = new logic.QuestionBank();
            logic.Player player = GameState.getPlayer();
            gm = new logic.GameManager(bank, player);
            // initialize using selected category if present
            QuestionBank.Category cat = GameState.getCategory();
            if (cat != null) {
                gm.initializeGame(cat);
            } else {
                gm.initializeGame(QuestionBank.Category.Theoretical);
            }
            GameState.setGameManager(gm);
        }

        // Group questions by level from GameManager (questions are already loaded for
        // the chosen category)
        List<Question> selected = gm.getAvailableQuestions();
        Map<String, List<Question>> byLevel = new HashMap<>();
        for (Question q : selected) {
            byLevel.computeIfAbsent(q.getDifficulty(), k -> new ArrayList<>()).add(q);
        }

        // Decide whether to restore saved topics for the current category or
        // generate-and-save
        String categoryKey = GameState.getCategoryKey(); // e.g., "Theoretical" or "Programming"
        if (GameState.hasSavedTopicsForCategory(categoryKey)) {
            restoreSavedTopics(categoryKey);
        } else {
            // Generate topics from questions (or random fallback) and save them mapped to
            // this category
            generateAndSaveTopics(byLevel, categoryKey);
        }

        // Set button bounds and listeners
        setButtonPositions();
        addListeners();

        // Add components (buttons then background so background is last? original code
        // added background last)
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

        // After creation, disable buttons for already-used topics
        disableUsedButtons(categoryKey);

        add(backgroundPanel);
        validate();
        repaint();
    }

    private void loadAllIcons() {
        if (selectedCategory == QuestionBank.Category.Programming) {
            progNames = new String[] { "CTO", "OTC", "ML" };
            // Load programming icons
            loadProgrammingIcons();
        } else {
            theoNames = new String[] { "EVDR", "Func", "Intro", "IVD", "MAP", "OOP", "Prod" };
            // Load theoretical icons
            loadTheoreticalIcons();
        }
    }

    private void loadProgrammingIcons() {
        // Loop through levels and load programming icons
        for (int level = 1; level <= maxLevel; level++) {
            for (String name : progNames) {
                String key = name + level; // e.g., CTO1, OTC1, ML1
                String path = "src/img/Level " + level + "/" + name + " " + level + ".png";

                // Load the image, scale it, and store it in the icons map
                Image scaled = new ImageIcon(path)
                        .getImage()
                        .getScaledInstance(746, 93, Image.SCALE_SMOOTH);

                icons.put(key, new ImageIcon(scaled));
            }
        }
    }

    private void loadTheoreticalIcons() {
        // Loop through levels and load theoretical icons
        for (int level = 1; level <= maxLevel; level++) {
            for (String name : theoNames) {
                String key = name + level; // e.g., EVDR1, Func1, Intro1
                String path = "src/img/Level " + level + "/" + name + " " + level + ".png";

                // Load the image, scale it, and store it in the icons map
                Image scaled = new ImageIcon(path)
                        .getImage()
                        .getScaledInstance(746, 93, Image.SCALE_SMOOTH);

                icons.put(key, new ImageIcon(scaled));
            }
        }
    }

    // Returns an array of two random keys for a given level
    private List<String> randomKeysForLevel(int level) {

        List<String> keys = new ArrayList<>();
        for (String name : currentNames) {
            keys.add(name + level);
        }
        Collections.shuffle(keys);
        return keys;
    }

    private void assignRandomIcons(JButton a, JButton b, int level) {
        List<String> keys = randomKeysForLevel(level);

        String topicA = keys.get(0);
        String topicB = keys.get(1);

        a.setIcon(icons.get(topicA));
        b.setIcon(icons.get(topicB));

        // Save topics into variables
        switch (level) {
            case 5 -> {
                level5aTopic = topicA;
                level5bTopic = topicB;
            }
            case 4 -> {
                level4aTopic = topicA;
                level4bTopic = topicB;
            }
            case 3 -> {
                level3aTopic = topicA;
                level3bTopic = topicB;
            }
            case 2 -> {
                level2aTopic = topicA;
                level2bTopic = topicB;
            }
            case 1 -> {
                level1aTopic = topicA;
                level1bTopic = topicB;
            }
        }
    }

    /**
     * FINAL assignTopicsFromQuestions used internally by generateAndSaveTopics.
     * This version assigns icons, stores topics into the proper level variables,
     * but DOES NOT save to GameState here (saving is done in
     * generateAndSaveTopics).
     */
    private void assignTopicsFromQuestions(JButton a, JButton b, int level,
            Map<String, List<Question>> byLevel) {
        String levelKey = "L" + level;
        List<Question> list = byLevel.getOrDefault(levelKey, new ArrayList<>());

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
                case 5 -> {
                    level5aTopic = key1;
                    level5bTopic = key2;
                }
                case 4 -> {
                    level4aTopic = key1;
                    level4bTopic = key2;
                }
                case 3 -> {
                    level3aTopic = key1;
                    level3bTopic = key2;
                }
                case 2 -> {
                    level2aTopic = key1;
                    level2bTopic = key2;
                }
                case 1 -> {
                    level1aTopic = key1;
                    level1bTopic = key2;
                }
            }
        } else if (list.size() == 1) {
            Question q1 = list.get(0);
            String abbr1 = mapTopicNameToAbbrev(q1.getTopic());
            String key1 = abbr1 + level;
            a.setIcon(icons.getOrDefault(key1, icons.get(randomKeysForLevel(level).get(0))));

            List<String> keys = randomKeysForLevel(level);
            String key2 = keys.get(0).equals(key1) ? keys.get(1) : keys.get(0);
            b.setIcon(icons.get(key2));

            switch (level) {
                case 5 -> {
                    level5aTopic = key1;
                    level5bTopic = key2;
                }
                case 4 -> {
                    level4aTopic = key1;
                    level4bTopic = key2;
                }
                case 3 -> {
                    level3aTopic = key1;
                    level3bTopic = key2;
                }
                case 2 -> {
                    level2aTopic = key1;
                    level2bTopic = key2;
                }
                case 1 -> {
                    level1aTopic = key1;
                    level1bTopic = key2;
                }
            }
        } else {
            assignRandomIcons(a, b, level);
        }
    }

    private String mapTopicNameToAbbrev(String topicName) {
        if (topicName == null) {
            return "Prod"; // default
        }
        String t = topicName.toLowerCase();
        // Programming topic mappings (non-intrusive to theoretical)
        if (t.contains("code_to_output")) {
            return "CTO";
        }
        if (t.contains("output_to_code")) {
            return "OTC";
        }
        if (t.contains("fill_in_blank") || t.contains("missing_line")) {
            return "ML";
        }
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

    /**
     * Generate topic layout (assign topics/icons), then save the mapping into
     * GameState
     * under the provided categoryKey so it can be restored later.
     */
    private void generateAndSaveTopics(Map<String, List<Question>> byLevel, String categoryKey) {
        assignTopicsFromQuestions(Level5a, Level5b, 5, byLevel);
        GameState.saveTopicForCategory(categoryKey, "5a", level5aTopic);
        GameState.saveTopicForCategory(categoryKey, "5b", level5bTopic);

        assignTopicsFromQuestions(Level4a, Level4b, 4, byLevel);
        GameState.saveTopicForCategory(categoryKey, "4a", level4aTopic);
        GameState.saveTopicForCategory(categoryKey, "4b", level4bTopic);

        assignTopicsFromQuestions(Level3a, Level3b, 3, byLevel);
        GameState.saveTopicForCategory(categoryKey, "3a", level3aTopic);
        GameState.saveTopicForCategory(categoryKey, "3b", level3bTopic);

        assignTopicsFromQuestions(Level2a, Level2b, 2, byLevel);
        GameState.saveTopicForCategory(categoryKey, "2a", level2aTopic);
        GameState.saveTopicForCategory(categoryKey, "2b", level2bTopic);

        assignTopicsFromQuestions(Level1a, Level1b, 1, byLevel);
        GameState.saveTopicForCategory(categoryKey, "1a", level1aTopic);
        GameState.saveTopicForCategory(categoryKey, "1b", level1bTopic);
    }

    /**
     * Restore saved topic layout for the category (load topic codes and icons,
     * and store them locally into levelX variables).
     */
    private void restoreSavedTopics(String categoryKey) {
        level5aTopic = GameState.getSavedTopicForCategory(categoryKey, "5a");
        level5bTopic = GameState.getSavedTopicForCategory(categoryKey, "5b");
        level4aTopic = GameState.getSavedTopicForCategory(categoryKey, "4a");
        level4bTopic = GameState.getSavedTopicForCategory(categoryKey, "4b");
        level3aTopic = GameState.getSavedTopicForCategory(categoryKey, "3a");
        level3bTopic = GameState.getSavedTopicForCategory(categoryKey, "3b");
        level2aTopic = GameState.getSavedTopicForCategory(categoryKey, "2a");
        level2bTopic = GameState.getSavedTopicForCategory(categoryKey, "2b");
        level1aTopic = GameState.getSavedTopicForCategory(categoryKey, "1a");
        level1bTopic = GameState.getSavedTopicForCategory(categoryKey, "1b");

        // Set icons (safely, fallback to a random)
        if (level5aTopic != null)
            Level5a.setIcon(icons.getOrDefault(level5aTopic, icons.get(randomKeysForLevel(5).get(0))));
        if (level5bTopic != null)
            Level5b.setIcon(icons.getOrDefault(level5bTopic, icons.get(randomKeysForLevel(5).get(1))));
        if (level4aTopic != null)
            Level4a.setIcon(icons.getOrDefault(level4aTopic, icons.get(randomKeysForLevel(4).get(0))));
        if (level4bTopic != null)
            Level4b.setIcon(icons.getOrDefault(level4bTopic, icons.get(randomKeysForLevel(4).get(1))));
        if (level3aTopic != null)
            Level3a.setIcon(icons.getOrDefault(level3aTopic, icons.get(randomKeysForLevel(3).get(0))));
        if (level3bTopic != null)
            Level3b.setIcon(icons.getOrDefault(level3bTopic, icons.get(randomKeysForLevel(3).get(1))));
        if (level2aTopic != null)
            Level2a.setIcon(icons.getOrDefault(level2aTopic, icons.get(randomKeysForLevel(2).get(0))));
        if (level2bTopic != null)
            Level2b.setIcon(icons.getOrDefault(level2bTopic, icons.get(randomKeysForLevel(2).get(1))));
        if (level1aTopic != null)
            Level1a.setIcon(icons.getOrDefault(level1aTopic, icons.get(randomKeysForLevel(1).get(0))));
        if (level1bTopic != null)
            Level1b.setIcon(icons.getOrDefault(level1bTopic, icons.get(randomKeysForLevel(1).get(1))));
    }

    private void disableUsedButtons(String categoryKey) {
        // Use slot-based used check. This prevents disabling both buttons when both
        // map to the same topic code but only one slot was used.
        if (level5aTopic != null && GameState.isSlotUsedForCategory(categoryKey, "5a"))
            Level5a.setEnabled(false);
        if (level5bTopic != null && GameState.isSlotUsedForCategory(categoryKey, "5b"))
            Level5b.setEnabled(false);

        if (level4aTopic != null && GameState.isSlotUsedForCategory(categoryKey, "4a"))
            Level4a.setEnabled(false);
        if (level4bTopic != null && GameState.isSlotUsedForCategory(categoryKey, "4b"))
            Level4b.setEnabled(false);

        if (level3aTopic != null && GameState.isSlotUsedForCategory(categoryKey, "3a"))
            Level3a.setEnabled(false);
        if (level3bTopic != null && GameState.isSlotUsedForCategory(categoryKey, "3b"))
            Level3b.setEnabled(false);

        if (level2aTopic != null && GameState.isSlotUsedForCategory(categoryKey, "2a"))
            Level2a.setEnabled(false);
        if (level2bTopic != null && GameState.isSlotUsedForCategory(categoryKey, "2b"))
            Level2b.setEnabled(false);

        if (level1aTopic != null && GameState.isSlotUsedForCategory(categoryKey, "1a"))
            Level1a.setEnabled(false);
        if (level1bTopic != null && GameState.isSlotUsedForCategory(categoryKey, "1b"))
            Level1b.setEnabled(false);
    }

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
        Level5a.addActionListener(e -> openTopic(level5aTopic, Level5a, "5a"));
        Level5b.addActionListener(e -> openTopic(level5bTopic, Level5b, "5b"));
        Level4a.addActionListener(e -> openTopic(level4aTopic, Level4a, "4a"));
        Level4b.addActionListener(e -> openTopic(level4bTopic, Level4b, "4b"));
        Level3a.addActionListener(e -> openTopic(level3aTopic, Level3a, "3a"));
        Level3b.addActionListener(e -> openTopic(level3bTopic, Level3b, "3b"));
        Level2a.addActionListener(e -> openTopic(level2aTopic, Level2a, "2a"));
        Level2b.addActionListener(e -> openTopic(level2bTopic, Level2b, "2b"));
        Level1a.addActionListener(e -> openTopic(level1aTopic, Level1a, "1a"));
        Level1b.addActionListener(e -> openTopic(level1bTopic, Level1b, "1b"));
    }

    private void openTopic(String topicCode, JButton btn, String slot) {
        if (topicCode == null)
            return;
        // Save currently-selected topic into GameState (scoped by category)
        String catKey = GameState.getCategoryKey();
        GameState.setTopic(topicCode);
        GameState.setTopicIconForCategory(catKey, icons.get(topicCode)); // optional store
        // Remember which slot (e.g. "2a") the player opened so we can mark that
        // specific slot as used after answering.
        GameState.setSelectedSlotForCategory(catKey, slot);
        // Open GameScreen
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(btn);
        topFrame.setContentPane(new GameScreen());
        topFrame.validate();
        topFrame.repaint();
    }
}
