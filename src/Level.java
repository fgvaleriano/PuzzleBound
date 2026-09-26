package src;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JLayeredPane;


public abstract class Level extends JLayeredPane {
    protected int[][] LevelData;
    protected boolean cleared = false;
    protected BufferedImage backgroundTexture;
    protected BufferedImage lvlImage = null;
    protected float xSpawn, ySpawn;
    protected int mapWidth, mapHeight;
    public boolean useReveal = false;
    protected String hint;

    public Level(int[][] LevelData, boolean cleared, int x, int y) {
        this.LevelData = LevelData;
        this.cleared = cleared;
        this.xSpawn = x;
        this.ySpawn = y;

    }

    public int getSpriteIndex(int x, int y) {
        return LevelData[y][x];
    }

    public int getWidth() {
        return LevelData[0].length;
    }

    public int getHeight() {
        return LevelData.length;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int [][] GetLevelData(){
        return LevelData;
    }

    public boolean isCleared(){ // check if level is cleared
        return cleared;
    }

    public float getXSpawn(){
        return xSpawn;
    }

    public float getYSpawn(){
        return ySpawn;
    }

    public void setCleared(boolean cleared){ // set Level cleared and called
        this.cleared = cleared;
    }

    public BufferedImage getBackgroundTexture(){
        return backgroundTexture;
    }

    public boolean useReveal(){ //like invisible platforms
        return useReveal;
    }

    protected void loadLevelData(int width, int height, int [][] lvlData) { // to array the level data, like load
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                Color color = new Color(lvlImage.getRGB(i, j));
                int red = color.getRed();
                int blue = color.getBlue();

                int value;

                if (red == 255) {
                    // Handle special object tiles
                    if (blue == 150) {
                        value = 242; // start flag
                    } else if (blue == 200) {
                        value = 243; // end flag
                    } else {
                        value = -1; // unknown object, treat as air
                    }
                } else if (red >= 242) {
                    value = -1; // air
                } else {
                    value = red; // regular terrain
                }

                lvlData[j][i] = value;
            }
        }
    }

    

    public boolean isReversedControls() { //allow for easy change of controls, at start normal controls
        return false; 
    }

    //Collision detection:
    public boolean CanMoveHere(float x, float y, float width, float height, int[][] levelData){

        int steps = 4;  //the box of the player is sorrounded by points to ensure more precise collision detection
        float xStep = width / steps;
        float yStep = height / steps;

        for(int i = 0; i <= steps; i++){
            for(int j = 0; j <= steps; j++){
                float xPos = x + i * xStep;
                float yPos = y + j * yStep;

                if(isSolid(xPos, yPos, levelData)){
                    return false; // collision detected
                }
            }
        }
        return true; // no collision detected

    }

    public boolean isSolid(float x, float y, int[][] levelData){
        if (x < 0 || x >= GameFrame.GAME_WIDTH) {
            return true;
        }
        if (y < 0 || y >= GameFrame.GAME_HEIGHT) {
            return true;
        }

        float xIndex = (int) x / GameFrame.TILE_SIZE;
        float yIndex = (int) y / GameFrame.TILE_SIZE;

        int value = levelData[(int) yIndex][(int) xIndex];

        // Air tiles: -1 and 23 is NOT solid
        if (value == -1 || value == 23 || value == 242 || value == 243) {
            return false;
        }

        return true; // Everything else is solid
    }

    public boolean isOnDeathTile(float x, float y, float width, float height, int[][] levelData) {
        //this uses four corners detection, if one of them is true, then there is a death tile
        return isDeathTile(x, y, levelData)
            || isDeathTile(x + width, y, levelData)
            || isDeathTile(x, y + height, levelData)
            || isDeathTile(x + width, y + height, levelData);
    }

    private boolean isDeathTile(float x, float y, int[][] levelData) {
        if (x < 0 || y < 0 || x >= GameFrame.GAME_WIDTH || y >= GameFrame.GAME_HEIGHT) return false;

        int xIndex = (int)(x / GameFrame.TILE_SIZE);
        int yIndex = (int)(y / GameFrame.TILE_SIZE);
        int value = levelData[yIndex][xIndex];

        if(value == 23){ // 23 tile is a death tile
            return true;
        }
        return false;
    }

    public boolean isOnWinningTile(float x, float y, float width, float height, int[][] levelData) {
        return isWinTile(x, y, levelData)
            || isWinTile(x + width, y, levelData)
            || isWinTile(x, y + height, levelData)
            || isWinTile(x + width, y + height, levelData);
    }

    private boolean isWinTile(float x, float y, int[][] levelData) {
        if (x < 0 || y < 0 || x >= GameFrame.GAME_WIDTH || y >= GameFrame.GAME_HEIGHT)
            return false;

        int xIndex = (int)(x / GameFrame.TILE_SIZE);
        int yIndex = (int)(y / GameFrame.TILE_SIZE);
        int value = levelData[yIndex][xIndex];
        if(value == 243){
            return true;
        }
        return false;
    }

    public void musicWon(){
        GameFrame.music.victory();
    }

    public void musicLow(){
        GameFrame.music.lose();
    }

    protected abstract void checkLose(Player player);; // logic for level;
    protected abstract void checkWin(Player player);
    protected abstract void importLevelData(); // import the level image;
    protected abstract void importBackgroundTexture(); // import the background image;
    protected abstract boolean checkReverse();
    protected abstract String getLevelHint();
    protected abstract void reset();

}

