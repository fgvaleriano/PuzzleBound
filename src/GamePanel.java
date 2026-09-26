package src;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {
    private BufferedImage[] textureimg;
    private int backgroundXOffset = 0;
    private int backgroundYOffset = 0;

    private LevelManager manager = new LevelManager();
    private Level currentLevel;
    private Player player;

    private Thread gameThread;
    private KeyHandler keyH;


    public GamePanel(){
        //this initializes everything to avoid errors
        currentLevel = manager.getCurrentLevel();
        setPreferredSize(new Dimension(GameFrame.GAME_WIDTH, GameFrame.GAME_HEIGHT));

        
        importOutsideSprite();
        keyH = new KeyHandler();
        addKeyListener(keyH);

        player = new Player(currentLevel.getXSpawn(), currentLevel.getYSpawn(), currentLevel);
        startGameThread();
        
        new Timer(40, e -> {
            backgroundYOffset = (backgroundYOffset + 1) % 64;
            repaint();
        }).start();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(currentLevel.getWidth() * GameFrame.TILE_SIZE, currentLevel.getHeight() * GameFrame.TILE_SIZE);
    }

    private void importOutsideSprite() {
        BufferedImage img = null;
        BufferedImage startFlag = null;
        BufferedImage endFlag = null;

        try {
            img = ImageIO.read(GamePanel.class.getResource("/resource/Other/Terrain (16x16).png"));
            startFlag = ImageIO.read(GamePanel.class.getResource("/resource/Other/Start (Idle).png"));
            endFlag = ImageIO.read(GamePanel.class.getResource("/resource/Other/End (Idle).png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        textureimg = new BufferedImage[244]; //0 - 241 (tile texture) 242 - 243(objects texture)
        for (int j = 0; j < 11; j++) {
            for (int i = 0; i < 22; i++) {
                int index = j * 22 + i;
                textureimg[index] = img.getSubimage(i * 16, j * 16, 16, 16);
            }
        }
        textureimg[242] = startFlag;
        textureimg[243] = endFlag;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw moving tiled background
        BufferedImage bg = currentLevel.getBackgroundTexture(); //holds the background image of the current level
        for (int y = -bg.getHeight(); y < getHeight(); y += bg.getHeight()) {
            for (int x = -bg.getWidth(); x < getWidth(); x += bg.getWidth()) {
                g.drawImage(bg, 
                x + backgroundXOffset, 
                y + backgroundYOffset, 
                null);
            }
        }


        for (int j = 0; j < currentLevel.getHeight(); j++) {
            for (int i = 0; i < currentLevel.getWidth(); i++) {
                int index = currentLevel.getSpriteIndex(i, j);

                if (currentLevel.useReveal() && (index == 39 || index == 40 || index == 41) &&
                    !player.isJumping()) {

                    continue; //skips to draw these tiles on these specific indexes
                }
            
                if (index == 242 || index == 243) {
                    // 64x64 object (flag) sits on top of 32x32 tile
                    //this allows for the flags to sit properly on the tiles
                    int drawX = i * GameFrame.TILE_SIZE;
                    int drawY = j * GameFrame.TILE_SIZE - (64 - GameFrame.TILE_SIZE);

                    g.drawImage(textureimg[index], drawX, drawY, 64, 64, null);

                } else if (index >= 0 && index < textureimg.length) {
                    //draws the rest of the things needed to be draw
                    g.drawImage(textureimg[index],
                    i * GameFrame.TILE_SIZE,
                    j * GameFrame.TILE_SIZE,
                    GameFrame.TILE_SIZE,
                    GameFrame.TILE_SIZE,
                    null);
                }
            }
        }
        player.draw(g); //Draw player on the screem - last to be drawn
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (gameThread != null) {
            update();
            repaint();

            try {
                Thread.sleep(16); // ~60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        if (GameFrame.isGameFrozen){
            return;
        } else if (!GameFrame.isGameFrozen){
            player.update(keyH); 
        }
    }

    public void setLevel(String levelKey) {
        //this part ensures that before a player plays a level it is freshly started
        //meaning if cleared before and play again, it would start as a new thang

        if (manager.getLevel(levelKey) != null) {
            manager.setLevelKey(levelKey);  // ensures to set key
            currentLevel = manager.getLevel(levelKey);

            currentLevel.reset();
            player.reset(currentLevel);
            
            GameFrame.menu.updateHint();
            repaint();
        }
    }

    public String getHint(){
        return manager.getLevelHint(currentLevel);
    }

}
