package src;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Level3 extends Level {

    private final int timeLimit = 10;
    private int remainingTime = timeLimit;
    private Thread timerThread;
    private boolean timerRunning = false;
    private boolean wasIdle = false;
    private boolean musicPlayed = false; // Flag to track if music has been played
    private boolean isIdle;
    
    public Level3(){
        super(null, false, 454,202); 
        importLevelData();
        importBackgroundTexture();

        mapWidth = lvlImage.getWidth();
        mapHeight = lvlImage.getHeight();

        this.hint = "Even Odysseus waited ten years to see home - let your patience be your oar.";
        this.LevelData = new int[mapHeight][mapWidth];
        loadLevelData(mapWidth, mapHeight, LevelData);
    }

    @Override
    public void checkLose(Player player) {
        if (timerRunning) return;  // Only check for loss if the timer is running 
        //here it would return if the timer is running since movement is checked in this level

        Rectangle2D.Float hb = player.getHitbox();
    
        // Check if player is on a death tile (this part should stay the same)
        if (isOnDeathTile(hb.x, hb.y, hb.width, hb.height, LevelData)) {
            GameFrame.lose.setVisible(true);       
            GameFrame.lose.showPanel(true);       
        
            if (!musicPlayed) {
                musicLow();  // Play losing music
                musicPlayed = true;  // Mark that the music has been played
            }
        
            player.respawn();  // Respawn the player or reset the level
            reset();  // Reset the level state
        }
    }



    @Override
    public void checkWin(Player player) {
        isIdle = !player.method.getMoved(); // true if not moving

        //System.out.println("Player moved? " + player.method.getMoved());
        //System.out.println("isIdle: " + isIdle + " | wasIdle: " + wasIdle + " | TimerRunning: " + timerRunning);

        if (GameFrame.manager.getCurrentLevelKey().equals("Level3")) {
        
            // Player just became idle then start timer
            if (isIdle && !wasIdle) {
                startTimer();
            }

            // Player started moving again while timer is running, then they lose
            else if (!isIdle && wasIdle && timerRunning) {
                triggerLossFromMovement(player);
                return; // prevent win condition from running in same frame
            }

            // Update last state
            wasIdle = isIdle;

            // If timer finished, they win
            if (remainingTime <= 0) {
                GameFrame.win.setVisible(true); //just added
                GameFrame.win.showPanel(true);
                if (!musicPlayed) {
                    musicWon();  // Play winning music
                    musicPlayed = true;  // Mark that the music has been played
                }
            }
        }
    }

    private void triggerLossFromMovement(Player player) {
        // If the player moves while the timer is running, they lose
        //System.out.println("Player moved. You lose.");
        GameFrame.music.lose();
        GameFrame.lose.setVisible(true);       
        GameFrame.lose.showPanel(true);       
        reset();
    }




    public void startTimer() {
        if (timerRunning) return;   // Prevent multiple timers from running at once

        timerRunning = true;
        remainingTime = timeLimit;  // Reset timer

        //System.out.println("Timer started.");
        
        // Start a new thread to count down the timer
        timerThread = new Thread(() -> {
            try {
                while (remainingTime > 0) {
                    while(GameFrame.isGameFrozen){
                        //System.out.println("Game Paused.");
                        Thread.sleep(1000); //this ensures that timer will not run if the game is paused
                    }
                    //System.out.println("Game is being Played.");
                    //System.out.println("Time left: " + remainingTime + " seconds");
                    Thread.sleep(1000);  // Wait for 1 second
                    remainingTime--;
                }
            } catch (InterruptedException e) {
               // System.out.println("Timer cancelled.");
            } finally {
                timerRunning = false;  // Set the timer state to false once the countdown ends
            }
        });

        timerThread.start();
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
            lvlImage = ImageIO.read(getClass().getResource("/resource/Level/Level3.png")); // ok
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void importBackgroundTexture(){
        // importBackgroundTexture:
        try {
            backgroundTexture = ImageIO.read(getClass().getResource("/resource/Background/Purple.png"));
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
        remainingTime = timeLimit;
        timerRunning = false;
        cancelTimerIfRunning();  // stop any active timer thread
        wasIdle = false;
    

        /*System.out.println("\nThis level was reset.");
        System.out.println("Remaining time: " + remainingTime);
        System.out.println("Timer is: " + timerRunning);
        System.out.println("The player is currently isIdle? " + isIdle + "\n");*/
    }

}
