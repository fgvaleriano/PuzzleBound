package src;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import javax.swing.*;

public class Player {
    private float x, y;
    private float speed = 4;
    private int width = 64, height = 64;
    private int velocityY = 0;

    //private int jumpCount = 0;
    public static final int MAX_JUMPS = 2;

    private int animationIndex = 0;
    private long lastFrameTime;
    private int animationSpeed = 200; // ms

    private HashMap<String, Image[]> animations;
    private String currentState = "idle";
    
    private Rectangle2D.Float hitbox;
    private int[][] levelData;
    private float xDrawOffset = 8;
    private float yDrawOffset = 9;
    private Level currentLevel;
    public HelperMethod method = new HelperMethod();
    private boolean actuallyMoved = false;

    public Player(float startX, float startY, Level level) {
        this.x = (float) startX;
        this.y = (float) startY;
        this.currentLevel = level;
        this.levelData = level.GetLevelData();
        initHitbox(x, y, width, height);

        animations = new HashMap<>();
        loadAnimations();

        lastFrameTime = System.currentTimeMillis();
    }

    //for debugging purposes for checking the hitbox
    private void drawHitbox(Graphics g){
        g.setColor(Color.BLACK);
        g.drawRect((int) hitbox.x, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height); //shows the hitbox of the player
    }

    private void initHitbox(float x, float y, float width, float height){
        float hbWidth = 40;
        float hbHeight = 50;
        float hbX = x + (width - hbWidth) / 2;
        float hbY = y + (height - hbHeight); // bottom-aligned

        hitbox = new Rectangle2D.Float(hbX, hbY, hbWidth, hbHeight);
    }

    private void updateHitbox(){
        hitbox.x = x;
        hitbox.y = y;
    }

    public Rectangle2D.Float getHitbox(){
        return hitbox;
    }

   private void loadAnimations() {
    animations.put("walkRight", new Image[]{
        new ImageIcon(getClass().getResource("/resource/Entity/RunRight1.png")).getImage(),
        new ImageIcon(getClass().getResource("/resource/Entity/RunRight2.png")).getImage()
    });
    animations.put("walkLeft", new Image[]{
        new ImageIcon(getClass().getResource("/resource/Entity/RunLeft1.png")).getImage(),
        new ImageIcon(getClass().getResource("/resource/Entity/RunLeft2.png")).getImage()
    });
    animations.put("jump", new Image[]{
        new ImageIcon(getClass().getResource("/resource/Entity/Jump.png")).getImage()
    });
    animations.put("doubleJump", new Image[]{
        new ImageIcon(getClass().getResource("/resource/Entity/Jump.png")).getImage(),
        new ImageIcon(getClass().getResource("/resource/Entity/DoubleJump.png")).getImage()
    });
    animations.put("fall", new Image[]{
        new ImageIcon(getClass().getResource("/resource/Entity/Fall.png")).getImage()
    });
    animations.put("idle", new Image[]{
        new ImageIcon(getClass().getResource("/resource/Entity/Idle.png")).getImage()
    });
    }

    public void update(KeyHandler keyH){
        method.setMoved(false); // intent
        setActuallyMoved(false); // reset actual move state used for logic in level3;

        method.move(this, currentLevel, speed, MAX_JUMPS, keyH);

        //Check for collision vertically

        if (currentLevel.CanMoveHere(hitbox.x, hitbox.y + method.getySpeed(), hitbox.width, hitbox.height, levelData)) {
            hitbox.y += method.getySpeed();
            y = hitbox.y;
        } else {
        
            // Collision with ground or ceiling
            if (method.getVelocityY() > 0) {
                method.setJumpCount(0);// Reset jump count only when falling and hitting ground
            }
            velocityY = 0;
        }

        if (currentLevel.CanMoveHere(hitbox.x + method.getxSpeed(), hitbox.y, hitbox.width, hitbox.height, levelData)) {
            hitbox.x += method.getxSpeed();
            x = hitbox.x;
        }

       if (velocityY < 0) {
            currentState = (method.getJumpCount() == 1) ? "jump" : "doubleJump";
        } else if (velocityY > 0 && method.getJumpCount() > 0) {
            currentState = "fall";
        } else if (keyH.leftPressed) {
            currentState = "walkLeft";
        } else if (keyH.rightPressed) {
            currentState = "walkRight";
        } else {
            currentState = "idle";
        }


        if (method.getMoved()) {
            setActuallyMoved(true);
        }

        currentLevel.checkLose(this);
        currentLevel.checkWin(this);

        // Animation timing
        long currentTime = System.currentTimeMillis();
        if ((currentState.equals("walkRight") || currentState.equals("walkLeft") || currentState.equals("doubleJump"))
                && currentTime - lastFrameTime > animationSpeed) {
            animationIndex = (animationIndex + 1) % animations.get(currentState).length;
            lastFrameTime = currentTime;
        } else if (!currentState.equals("walkRight") &&
                    !currentState.equals("walkLeft") &&
                    !currentState.equals("doubleJump") &&
                    !currentState.equals("jump") &&
                    !currentState.equals("fall")) {
                        animationIndex = 0;
        }

        updateHitbox();
    }
    


    public void respawn(){
        this.x = currentLevel.getXSpawn();
        this.y = currentLevel.getYSpawn();
        updateHitbox();
    }

    public void reset(Level currentLevel){
        //this part ensure that eveyrtime a player plays a level
        //it is a new fresh start nga level

        this.currentLevel = currentLevel;             //Reset Level
        this.levelData = currentLevel.GetLevelData();  //Reload level data
        this.x = currentLevel.getXSpawn();             // Reset to spawn
        this.y = currentLevel.getYSpawn();
        updateHitbox();                                // Refresh position                        
        currentState = "idle";                         // Reset animation state
    }



    public void teleport(int x, int y){
        //this teleport method is used for level 2
        this.x = x;
        this.y = y;
        updateHitbox();
    }

    public void loadLevelData(int [][] levelData){
        this.levelData = levelData;
    }

    public void draw(Graphics g) {
        Image[] frames = animations.getOrDefault(currentState, animations.get("idle"));
        animationIndex = Math.min(animationIndex, frames.length - 1); //prevent crash
        g.drawImage(frames[animationIndex], (int) (hitbox.x - xDrawOffset), (int) (hitbox.y - yDrawOffset), width, height, null);
       // drawHitbox(g); 
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public boolean hasActuallyMoved() {
        return actuallyMoved;
    }

    public void setActuallyMoved(boolean moved) {
        this.actuallyMoved = moved;
    }

    public boolean isJumping(){
        if( method.getJumpCount() > 0) return true;
        return false;
    }

}
