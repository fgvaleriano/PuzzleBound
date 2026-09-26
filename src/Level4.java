package src;

import java.awt.geom.Rectangle2D;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Level4 extends Level {
    private boolean musicPlayed = false;
    
    public Level4(){
        super(null, false, 90,428); 
        this.useReveal = true;
        importLevelData();
        importBackgroundTexture();

        this.hint = "Even the gods were fooled by beauty - trust your gut, not your gaze.";

        mapWidth = lvlImage.getWidth();
        mapHeight = lvlImage.getHeight();

        this.LevelData = new int[mapHeight][mapWidth];
        loadLevelData(mapWidth, mapHeight, LevelData);
    }

    @Override
    public boolean isSolid(float x, float y, int[][] levelData){
        //override an isSolid logic from the mother class
        //since there things nga are not solid in this level but other levels are solid

        if (x < 0 || x >= GameFrame.GAME_WIDTH) {
            return true;
        }
        if (y < 0 || y >= GameFrame.GAME_HEIGHT) {
            return true;
        }

        float xIndex = x / GameFrame.TILE_SIZE;
        float yIndex = y / GameFrame.TILE_SIZE;

        int value = levelData[(int) yIndex][(int) xIndex];

        // THis following numbers are air tiles
        if (value == -1 || value == 23 || value == 242 || value == 243 ||
            value == 12 || value == 13 || value == 14 || value == 15 || value == 37 || value == 59) {
            return false;
        }

        return true; // Everything else is solid
    }


    @Override
    public void checkLose(Player player) {
        Rectangle2D.Float hb = player.getHitbox();

        if (isOnDeathTile(hb.x, hb.y, hb.width, hb.height, LevelData)) {
            GameFrame.lose.setVisible(true);       
            GameFrame.lose.showPanel(true);

            if (!musicPlayed) {
                musicLow();  // Play winning music
                musicPlayed = true;  // Mark that the music has been played
            }
            player.respawn(); 
            musicPlayed = false;
        }
    }

    public void checkWin(Player player) {
        Rectangle2D.Float hb = player.getHitbox();

        if (isOnWinningTile(hb.x, hb.y, hb.width, hb.height, LevelData)) {
            GameFrame.win.setVisible(true); //just added
            GameFrame.win.showPanel(true);
            if (!musicPlayed) {
                    musicWon();  // Play winning music
                    musicPlayed = true;  // Mark that the music has been played
            }

        }
        
    }

    protected void importLevelData(){
        try {
            lvlImage = ImageIO.read(getClass().getResource("/resource/Level/Level4.png")); // ok
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void importBackgroundTexture(){
        // importBackgroundTexture:
        try {
            backgroundTexture = ImageIO.read(getClass().getResource("/resource/Background/Green.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected boolean checkReverse(){
        return true;
    }

    @Override
    protected String getLevelHint() {
        return hint;
    }

    @Override
    protected void reset() {
        musicPlayed = false; 
    }

    
}
