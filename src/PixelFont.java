package src;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;

public class PixelFont {
    private static Font pixelFont;
    static {
        try {
            InputStream is = PixelFont.class.getResourceAsStream("/resource/PressStart2P.ttf");
            
            if (is != null) {
                pixelFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(14f);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(pixelFont);
            } else {
                System.err.println("Could not find font resource!");
                pixelFont = new Font("Monospaced", Font.PLAIN, 14);
            }
        } catch (Exception e) {
            e.printStackTrace();
            pixelFont = new Font("Monospaced", Font.PLAIN, 14);
        }
    }

    public static Font getPixelFont(float size) {
        return pixelFont.deriveFont(size);
    }
}