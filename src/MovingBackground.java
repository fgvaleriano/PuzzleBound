package src;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class MovingBackground extends JPanel {
    private BufferedImage[] layers = new BufferedImage[5];
    private int[] xOffsets = new int[5];
    private double[] scrollSpeeds = { 0, 0.2, 0.5, 1.0, 2.0 }; // Fine-grained parallax
    private int[] scaledWidths = new int[5]; // stores calculated widths

    public MovingBackground() {
        setDoubleBuffered(true);
        loadImages();
        startAnimation();
    }

    private void loadImages() {
        try {
            layers[0] = ImageIO.read(MovingBackground.class.getResource("/resource/Clouds/Clouds 5/1.png"));
            layers[1] = ImageIO.read(MovingBackground.class.getResource("/resource/Clouds/Clouds 5/2.png"));
            layers[2] = ImageIO.read(MovingBackground.class.getResource("/resource/Clouds/Clouds 5/3.png"));
            layers[3] = ImageIO.read(MovingBackground.class.getResource("/resource/Clouds/Clouds 5/4.png"));
            layers[4] = ImageIO.read(MovingBackground.class.getResource("/resource/Clouds/Clouds 5/5.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startAnimation() {
        new Timer(16, _ -> {
            for (int i = 1; i < layers.length; i++) {
                // Only scroll if layer exists and its width was computed
                if (layers[i] != null && scaledWidths[i] > 0) {
                    xOffsets[i] = (int) ((xOffsets[i] - scrollSpeeds[i]) % scaledWidths[i]);
                    if (xOffsets[i] > 0) xOffsets[i] -= scaledWidths[i]; // keep negative for clean loop
                }
            }
            repaint();
        }).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < layers.length; i++) {
            if (layers[i] != null) {
                drawLayer(g, layers[i], xOffsets[i], i);
            }
        }
    }

    private void drawLayer(Graphics g, BufferedImage img, int offset, int index) {
        int panelHeight = getHeight();
        double scale = (double) panelHeight / img.getHeight();
        int scaledWidth = (int) (img.getWidth() * scale);
        int scaledHeight = panelHeight;

        scaledWidths[index] = scaledWidth; // store for offset wrap logic

        int startX = offset % scaledWidth;
        if (startX > 0) startX -= scaledWidth;

        for (int x = startX; x < getWidth(); x += scaledWidth) {
            g.drawImage(img, x, 0, scaledWidth, scaledHeight, null);
        }
    }
}
