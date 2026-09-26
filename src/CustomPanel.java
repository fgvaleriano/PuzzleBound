package src;

import javax.swing.JPanel;
import java.awt.Graphics;
public class CustomPanel extends JPanel{
    private Background background;
    public CustomPanel(String image, boolean isTile){
        background = new Background(image, isTile);
    }
    public CustomPanel(String image, int x, int y, int width, int length){
        background = new Background(image, x, y, width, length);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        background.paintBackground(g, this);
    }
}
