package src.gui;

import java.awt.*;
import javax.swing.*;

public class BackgroundPanel extends JPanel {
    private Image background;

    public BackgroundPanel(String imagePath) {
        background = new ImageIcon(imagePath).getImage();
    }

    public void setBackground(String imagePath) {
        background = new ImageIcon(imagePath).getImage();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (background != null) {
            // Maintain aspect ratio, scale to fit the screen
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

            int imgWidth = background.getWidth(this);
            int imgHeight = background.getHeight(this);

            double scaleX = screenSize.getWidth() / imgWidth;
            double scaleY = screenSize.getHeight() / imgHeight;
            double scale = Math.min(scaleX, scaleY); // keep aspect ratio

            int newWidth = (int) (imgWidth * scale);
            int newHeight = (int) (imgHeight * scale);

            // Center the image
            int x = (screenSize.width - newWidth) / 2;
            int y = (screenSize.height - newHeight) / 2;

            g.drawImage(background, x, y, newWidth, newHeight, this);
        }
    }
}
