package src.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import src.logic.data.Record;
import java.util.List;

public class SchoolRecordPanel extends JPanel {
    private BackgroundPanel backgroundPanel;
    private Dimension screenSize;
    private Record recordManager = new Record();

    public SchoolRecordPanel() {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLayout(null);
        setBounds(0, 0, screenSize.width, screenSize.height);
        
        backgroundPanel = new BackgroundPanel("src/img/SchoolRecord.png");
        backgroundPanel.setBounds(0, 0, screenSize.width, screenSize.height);
        backgroundPanel.setLayout(null);
        
        createRecordsTable();
        
        // Back button
        JButton backButton = new JButton("Back to Menu");
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.setBounds(screenSize.width / 2 - 100, screenSize.height - 100, 200, 50);
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
        scrollPane.setBounds(200, 200, screenSize.width - 400, screenSize.height - 350);
        scrollPane.setBackground(Color.WHITE);
        
        backgroundPanel.add(scrollPane);
    }
}

