package src;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean leftPressed, rightPressed, upPressed, downPressed;
    public boolean jumpJustPressed = false;
    public boolean doubleTapJump = false;
    public boolean isPaused = false;

    private long lastJumpTime = 0;
    private final long DOUBLE_TAP_THRESHOLD = 250; // 250 milliseconds

    
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) {
            rightPressed = true;
        }

        if (code == KeyEvent.VK_UP || code == KeyEvent.VK_SPACE || code == KeyEvent.VK_W) {
            GameFrame.music.jump();
            upPressed = true;

            long now = System.currentTimeMillis();

            // Double tap detection
            if (now - lastJumpTime < DOUBLE_TAP_THRESHOLD) {
                doubleTapJump = true;
            } else {
                doubleTapJump = false;
            }

            lastJumpTime = now;
            jumpJustPressed = true; // Used so we only trigger on new key press
        }

        if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
            downPressed = true;
        }

        if (code == KeyEvent.VK_ESCAPE) {
            GameFrame.menu.toggleMenu();  // Toggle the pause menu

            if (GameFrame.isGameFrozen) { //this ensure than whenever esc is triggered and the character was still moving
                leftPressed = false;        // the character must stop moving immediately.
                rightPressed = false;
                upPressed = false;
                downPressed = false;
                jumpJustPressed = false;
                doubleTapJump = false;
            }
        }
    }   

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W || code == KeyEvent.VK_SPACE) {
            upPressed = false;
            jumpJustPressed = false;
        }
        if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
            downPressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }
}
