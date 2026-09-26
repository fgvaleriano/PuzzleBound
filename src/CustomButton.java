package src;

import javax.swing.JButton;
import java.awt.Graphics;
public class CustomButton extends JButton{
    private Background background;
    private Background hoverBackground;
    private boolean isHovering = false;
    public CustomButton(String text, String image, boolean isTile){
        super(text);
        background = new Background(image, isTile);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
    }
    public CustomButton(String text, String image, int xx, int xy, int yx, int yy, int width, int length){
        super(text);
        background = new Background(image, xx, xy, width, length);
        hoverBackground = new Background(image, yx, yy, width, length);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                isHovering = true;
                repaint();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                isHovering = false;
                repaint();
            }
        });
    }
    @Override
    protected void paintComponent(Graphics g) {
        if (isHovering && hoverBackground != null) {
            hoverBackground.paintBackground(g, this);
        } else {
            background.paintBackground(g, this);
        }
        super.paintComponent(g);
    }
}