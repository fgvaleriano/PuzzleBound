package src;

import java.awt.*;
import javax.swing.*;

public class GameOverPanel extends JPanel {
    private static final String SPRITESHEET = "/resource/Assets/Wooden Pixel Art GUI 32x32.png";    
    private static final int BUTTON_WIDTH = 32;
    private static final int BUTTON_HEIGHT = 32;

    public JPanel losePanel;

    public GameOverPanel() {
        setLayout(new BorderLayout());

        // Main container
        losePanel = createTransparentPanel(new BorderLayout());
        losePanel.setPreferredSize(new Dimension(500, 800));
        losePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        losePanel.setVisible(false);
        add(losePanel, BorderLayout.CENTER);

        // Center text
        JPanel centerContainer = createTransparentPanel(new BorderLayout());
        JLabel gameOver = new JLabel("Game Over!", SwingConstants.CENTER);
        gameOver.setForeground(Color.RED);
        gameOver.setFont(PixelFont.getPixelFont(72));
        centerContainer.add(gameOver, BorderLayout.CENTER);
        losePanel.add(centerContainer, BorderLayout.CENTER);

        // Buttons (South)
        CustomPanel buttonPanel = new CustomPanel(SPRITESHEET, 14, 20, 96, 96);
        buttonPanel.setOpaque(false);
        buttonPanel.setPreferredSize(new Dimension(150, 150));
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 0, 50));
        losePanel.add(buttonPanel, BorderLayout.SOUTH);

        buttonPanel.add(createActionButton(10, 26, _ -> {
            GameFrame.music.clicked();
            GameFrame.hideAllGameOverlays(); 
            GameFrame.isGameFrozen = false;
        })); // Retry

        buttonPanel.add(createActionButton(10, 20, _ -> {
            GameFrame.music.clicked();
            GameFrame.hideAllGameOverlays();
            GameFrame.isGameFrozen = false;
            GameFrame.cards.show(GameFrame.screens, "LevelSelect");
        })); // Back to Level Select

        buttonPanel.add(createActionButton(9, 20, _ -> {
            GameFrame.music.clicked();
            GameFrame.hideAllGameOverlays();
            GameFrame.isGameFrozen = false;
            GameFrame.cards.show(GameFrame.screens, "MainMenu");
        })); // Back to Main Menu
    }

    private JPanel createTransparentPanel(LayoutManager layout) {
        JPanel panel = new JPanel(layout);
        panel.setOpaque(false);
        return panel;
    }

    private CustomButton createActionButton(int spriteY, int spriteHoverY, java.awt.event.ActionListener action) {
        CustomButton btn = new CustomButton("", SPRITESHEET, spriteY, spriteHoverY, spriteY, spriteHoverY + 1, BUTTON_WIDTH, BUTTON_HEIGHT);
        btn.setPreferredSize(new Dimension(100, 100));
        btn.addActionListener(action);
        return btn;
    }

    public void showPanel(boolean visibility){
        losePanel.setVisible(visibility);
        GameFrame.isGameFrozen = visibility;
    }
    
}
