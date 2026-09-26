package src;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Level2 extends Level{

    private int countJump = 0;
    private int xWin = 932, yWin = 428;
    private int timeLimit = 60; // 1 minute
    private int remainingTime = timeLimit;
    private boolean musicPlayed = false;
    private boolean timerRunning = false; 
    private Thread timerThread;

    public Level2(){
        super(null, false, 72,364); 
        importLevelData();
        importBackgroundTexture();

        mapWidth = lvlImage.getWidth();
        mapHeight = lvlImage.getHeight();

        this.hint = "Where wings once dared the skies, trust the wind - leap, as Icarus did";

        this.LevelData = new int[mapHeight][mapWidth];
        loadLevelData(mapWidth, mapHeight, LevelData);
    }

    @Override
    public void checkLose(Player player) {
        if(remainingTime <= 0){
            GameFrame.lose.setVisible(true);       
            GameFrame.lose.showPanel(true);        
            if (!musicPlayed) {
                    musicLow();  // Play winning music
                    musicPlayed = true;  // Mark that the music has been played
            }
            reset();
            musicPlayed = false;
        }
    }
    
    //TBA pa if level2 is  a bonus level, to crazy you lang..ahahahhaa
    //just added a losing condition

    public void startTimer() {
        if(timerRunning) return;

        timerRunning = true;
        remainingTime = timeLimit;

        timerThread = new Thread(() -> {
            try {
                while (remainingTime > 0) { //only have like time of 2 minutes to solve
                    while(GameFrame.isGameFrozen){
                        //System.out.println("Game Paused.");
                        Thread.sleep(1000); //this ensures that timer will not run if the game is paused
                    }
                    Thread.sleep(1000);  // Wait for 1 second
                    remainingTime--;
                }
            } catch (InterruptedException e) {
                //System.out.println("Timer cancelled.");
            } finally {
                timerRunning = false;  // Set the timer state to false once the countdown ends
            }
        });
        timerThread.start();
    }

    @Override
    public void checkWin(Player player) {
        Rectangle2D.Float hb = player.getHitbox();
        startTimer();
        
        if(countJump < 7){
            if (isOnDeathTile(hb.x, hb.y, hb.width, hb.height, LevelData)) {
                //System.out.println("Hit death tile! Count Jump: " + countJump);
                player.respawn();
                countJump++;
            }
        }
        if(countJump == 7){
            player.teleport(xWin, yWin);
            GameFrame.win.setVisible(true); //just added
            GameFrame.win.showPanel(true);

            if (!musicPlayed) {
                    musicWon();  // Play winning music
                    musicPlayed = true;  // Mark that the music has been played
            }

        }
        
    }

    public void cancelTimerIfRunning() {
        if (timerThread != null && timerThread.isAlive()) { //just check lang to make sure nga there is a thread nga moving
            timerThread.interrupt();  // Stop the timer thread
            //System.out.println("Timer cancelled.");
        }
        timerRunning = false;  // Ensure the timer is marked as not running
    }

    
    protected void importLevelData(){
        try {
            lvlImage = ImageIO.read(getClass().getResource("/resource/Level/Level2.png")); //ok
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void importBackgroundTexture(){
        // importBackgroundTexture:
        try {
            backgroundTexture = ImageIO.read(getClass().getResource("/resource/Background/Gray.png"));
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
        countJump = 0;
        remainingTime = timeLimit;
        timerRunning = false;
        cancelTimerIfRunning(); 
    }
}
