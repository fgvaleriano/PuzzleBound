package src;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Background extends JPanel{
    private Image background;
    private BufferedImage tile;
    private boolean isTile;
    private boolean isSheet;

    public Background(String image, boolean isTile){
        String path = image.replace("\\", "/");
        if (!path.startsWith("/")) path = "/" + path;
        
        java.net.URL imgURL = Background.class.getResource(path);
        if (imgURL != null) {
            this.background = new ImageIcon(imgURL).getImage();
        } else {
            System.err.println("Could not find image resource: " + path);
        }
        
        this.isTile = isTile;
        this.isSheet = false;
    }

    public Background(String image, int x, int y, int width, int height){
        this.isSheet = true;
        try {
            String path = image.replace("\\", "/");
            if (!path.startsWith("/")) path = "/" + path;

            java.io.InputStream is = Background.class.getResourceAsStream(path);
            if (is != null) {
                BufferedImage sheet = ImageIO.read(is);
                this.tile = sheet.getSubimage(x * 32, y * 32, width, height);
            } else {
                System.err.println("Could not find spritesheet resource: " + path);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    protected void paintBackground(Graphics g, Component c){
        if (background!=null||tile!=null) {
            if(isTile){
                int w = background.getWidth(this);
                int h = background.getHeight(this);
                if (w > 0 && h > 0) {
                    for (int x = 0; x < c.getWidth(); x += w) {
                        for (int y = 0; y < c.getHeight(); y += h) {
                            g.drawImage(background, x, y, this);
                        }
                    }
                }
            }else if(isSheet){
                g.drawImage(tile, 0, 0, c.getWidth(), c.getHeight(), this);
            }else{
                g.drawImage(background, 0, 0, c.getWidth(), c.getHeight(), this);
            }
        }
    }
}
