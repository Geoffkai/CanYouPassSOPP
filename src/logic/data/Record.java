package src.logic.data;

import src.logic.Player;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Record {
    private static final String RECORDS_FILE = "school_records.txt";
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void saveRecord(Player player) {
        if (player == null) {
            return;
        }

        try (FileWriter fw = new FileWriter(RECORDS_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            
            String date = dateFormat.format(new Date());
            out.println(player.getUsername() + "," + player.getScore() + "," + 
                        player.getCorrectCount() + "," + date);
        } catch (IOException e) {
            System.err.println("Error saving record: " + e.getMessage());
        }
    }

    public List<String[]> loadRecords() {
        List<String[]> records = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(RECORDS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    records.add(parts);
                }
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist yet, return empty list
            return records;
        } catch (IOException e) {
            System.err.println("Error loading records: " + e.getMessage());
        }
        
        // Sort by score (descending)
        records.sort((a, b) -> {
            try {
                int scoreA = Integer.parseInt(a[1]);
                int scoreB = Integer.parseInt(b[1]);
                return Integer.compare(scoreB, scoreA);
            } catch (NumberFormatException e) {
                return 0;
            }
        });
        
        return records;
    }
}
