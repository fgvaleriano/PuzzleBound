//Level 1
package src;
import java.awt.geom.Rectangle2D;
import java.io.*;
import javax.imageio.ImageIO;

public class Level1 extends Level{
    private boolean musicPlayed = false;

    public Level1(){
        super(null, false, 78,394); //(78,394)
        importLevelData();
        importBackgroundTexture();
        this.hint = "Welcome to Puzzle Bounds";

        mapWidth = lvlImage.getWidth();
        mapHeight = lvlImage.getHeight();

        this.LevelData = new int[mapHeight][mapWidth];
        loadLevelData(mapWidth, mapHeight, LevelData);
    }

    @Override
    public void checkLose(Player player) {
        Rectangle2D.Float hb = player.getHitbox();
        if (isOnDeathTile(hb.x, hb.y, hb.width, hb.height, LevelData)) {
            GameFrame.lose.setVisible(true);       
            GameFrame.lose.showPanel(true);        

            GameFrame.isGameFrozen = true;
            if (!musicPlayed) {
                musicLow();  // Play winning music
                musicPlayed = true;  // Mark that the music has been played
            }
            musicPlayed = false; //this resets the music
            player.respawn(); // or whatever your logic is
        }
    }

    public void checkWin(Player player) {
        Rectangle2D.Float hb = player.getHitbox();

        if (isOnWinningTile(hb.x, hb.y, hb.width, hb.height, LevelData)) { 

            GameFrame.win.setVisible(true); 
            GameFrame.win.showPanel(true);
            GameFrame.isGameFrozen = true;

            if (!musicPlayed) {
                    musicWon();  // Play winning music
                    musicPlayed = true;  // Mark that the music has been played
            }
        }
        
    }

    protected void importLevelData(){
        try {
            lvlImage = ImageIO.read(getClass().getResource("/resource/Level/Level1.png")); //okay
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void importBackgroundTexture(){
        // importBackgroundTexture:
        try {
            backgroundTexture = ImageIO.read(getClass().getResource("/resource/Background/Pink.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected boolean checkReverse(){
        return false;
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
