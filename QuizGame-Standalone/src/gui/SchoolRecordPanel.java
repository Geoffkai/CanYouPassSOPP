package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import logic.data.Record;
import java.util.List;

public class SchoolRecordPanel extends JPanel {
    private BackgroundPanel backgroundPanel;
    private Dimension screenSize;
    private Record recordManager = new Record();

    ImageIcon BackIcon = new ImageIcon(new ImageIcon("src/img/InitialImg/Back.png").getImage().getScaledInstance(469, 78, java.awt.Image.SCALE_SMOOTH));
    JButton backButton = new JButton(BackIcon);

    public SchoolRecordPanel() {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLayout(null);
        setBounds(0, 0, screenSize.width, screenSize.height);
        
        backgroundPanel = new BackgroundPanel("src/img/InitialImg/RecordBG.png");
        backgroundPanel.setBounds(0, 0, screenSize.width, screenSize.height);
        backgroundPanel.setLayout(null);
        
        createRecordsTable();
        
        // Back button

        backButton.setBounds(725, 893, 469, 78);
        backButton.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.setContentPane(new Menu());
            topFrame.validate();
            topFrame.repaint();
        });
        
        backgroundPanel.add(backButton);
        add(backgroundPanel);
        validate();
        repaint();
    }

    private void createRecordsTable() {
        List<String[]> records = recordManager.loadRecords();
        
        String[] columnNames = {"Rank", "Username", "Score", "Correct", "Date"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        int rank = 1;
        for (String[] record : records) {
            Object[] row = {rank++, record[0], record[1], record[2], record[3]};
            model.addRow(row);
        }
        
        JTable table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));
        table.setBackground(Color.WHITE);
        table.setForeground(Color.BLACK);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(200, 100, screenSize.width - 400, screenSize.height - 350);
        scrollPane.setBackground(Color.WHITE);
        
        backgroundPanel.add(scrollPane);
    }
}

