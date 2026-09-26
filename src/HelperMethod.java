package src;
public class HelperMethod {

    private boolean moved;
    private float xSpeed, ySpeed;
    private int jumpCount = 0;
    private boolean isJumping;
    private int velocityY = 0;
    private int gravity = 1;
    private boolean didMoved; 

    
    public void move(Player player, Level currentLevel, Float speed, int MAX_JUMPS, KeyHandler keyH){

        xSpeed = 0;
        ySpeed = 0;
        didMoved = false;
        isJumping = false; 
        
        if(currentLevel.checkReverse() == false){
            // Horizontal movement
            if (keyH.leftPressed) {
                xSpeed -= speed;
                didMoved = true;
                if(velocityY == 0) player.setCurrentState("walkLeft");
            
            } else if (keyH.rightPressed) {
                xSpeed += speed;
                didMoved = true;
                if(velocityY == 0) player.setCurrentState("walkRight");
            }
        } else if(currentLevel.checkReverse() == true){
            if (keyH.rightPressed) {
                xSpeed -= speed;
                if(velocityY == 0) player.setCurrentState("walkLeft");
            
            } else if (keyH.leftPressed) {
                xSpeed += speed;
                if(velocityY == 0) player.setCurrentState("walkRight");
            }
        }

        // Jump / double jump
            
        if (keyH.jumpJustPressed) {
            if (jumpCount < MAX_JUMPS) {
                if (jumpCount == 0) {
                    velocityY = -15; // First jump
                    didMoved = true;
                    player.setCurrentState("jump");
            } else if (jumpCount == 1) {
                velocityY = -13; // Double jump
                didMoved = true;
                player.setCurrentState("doublejump");
            }
            jumpCount++;
        }
            keyH.jumpJustPressed = false; // Consume the jump press
        }

        velocityY += gravity;
        if (velocityY > 10) velocityY = 10;
        
        ySpeed = velocityY;
        moved = didMoved; //ensures that moved is only true if the player really moved;

    }

    public float getxSpeed(){
        return xSpeed;
    }

    public float getySpeed(){
        return ySpeed;
    }

    public void setJumpCount(int jumpCount){
        this.jumpCount = jumpCount;
    }

    public int getJumpCount(){
        return jumpCount;
    }

    public int getVelocityY(){
        return velocityY;
    }

    public void setMoved(boolean moved){
        this.moved = moved;
    }

    public boolean getMoved(){
        return moved;
    }

    public boolean Isjumping(){
        return isJumping;
    }
}
