package src;

import java.awt.*;
import javax.swing.*;

public class LevelSelect extends JPanel {
    private static final String BUTTON_SPRITESHEET = "/resource/Assets/Wooden Pixel Art GUI 32x32.png";
    private static final int BUTTON_WIDTH = 96;
    private static final int BUTTON_HEIGHT = 32;
    private JPanel buttonGrid;


    public LevelSelect() {
        setLayout(new BorderLayout());

        // Moving background
        MovingBackground background = new MovingBackground();
        background.setLayout(new BorderLayout());
        add(background, BorderLayout.CENTER);

        // Center container with title + buttons
        JPanel containerPlus = createTransparentPanel(new BorderLayout());
        background.add(containerPlus, BorderLayout.CENTER);

        // Button grid
        buttonGrid = createTransparentPanel(new GridLayout(5, 1, 0, 25));
        buttonGrid.setBorder(BorderFactory.createEmptyBorder(100, 400, 100, 400));
        containerPlus.add(buttonGrid, BorderLayout.CENTER);

        // Add level buttons
        refreshButtons();

        // Top bar container
        JPanel northContainer = createTransparentPanel(new BorderLayout());
        containerPlus.add(northContainer, BorderLayout.NORTH);

        // Back button in northwest
        JPanel nw = createTransparentPanel();
        nw.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        northContainer.add(nw, BorderLayout.WEST);

        // Placeholder east
        JPanel ne = createTransparentPanel();
        ne.setPreferredSize(new Dimension(115, 0));
        northContainer.add(ne, BorderLayout.EAST);

        CustomButton back = new CustomButton(" ", BUTTON_SPRITESHEET, 0, 0, 0, 1, 32, 32);
        back.setPreferredSize(new Dimension(75, 75));

        back.addActionListener(_ -> {
            GameFrame.music.clicked();
            this.refreshButtons(); // Call refresh, pra if there any levels cleared the buttons would be clickable an level na nga cleared.
            GameFrame.cards.show(GameFrame.screens, "MainMenu"); // Switch screen
        });

        nw.setLayout(new BorderLayout()); // needed to center the button
        nw.add(back, BorderLayout.CENTER);

        // Title banner and label
        CustomPanel title = new CustomPanel(BUTTON_SPRITESHEET, 13, 30, 96, 48);
        title.setOpaque(false);
        title.setLayout(new BorderLayout());
        title.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
        northContainer.add(title, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel("Select Level");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(PixelFont.getPixelFont(42));
        title.add(titleLabel, BorderLayout.CENTER);
    }

    private JPanel createTransparentPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        return panel;
    }

    private JPanel createTransparentPanel(LayoutManager layout) {
        JPanel panel = createTransparentPanel();
        panel.setLayout(layout);
        return panel;
    }

    private CustomButton createLevelButton(String text, CardLayout cards, JPanel screens, String targetCard) {
        CustomButton btn = new CustomButton(text, BUTTON_SPRITESHEET, 9, 33, 9, 34, BUTTON_WIDTH, BUTTON_HEIGHT);
        btn.setPreferredSize(new Dimension(100, 100));
        btn.setFont(PixelFont.getPixelFont(35));

        
        if (targetCard != null && !targetCard.isEmpty()) {
            btn.setText(text);
            btn.setEnabled(true);

            btn.addActionListener(e -> {
                GameFrame.music.clicked();
                GameFrame.isNormalPlay = false;
                GameFrame.manager.setLevelKey(targetCard);
                GameFrame.levelPanel.setLevel(targetCard);
                GameFrame.cards.show(GameFrame.screens, "GamePanel");

                SwingUtilities.invokeLater(() -> {
                    GameFrame.menu.menu.setVisible(false);
                    GameFrame.hideAllGameOverlays();
                    GameFrame.levelPanel.setFocusable(true);
                    GameFrame.levelPanel.requestFocusInWindow();
                    GameFrame.isGameFrozen = false;
                });
            });

        } else {
            // Just in case the targetCard is invalid
            btn.setText(text + " (locked)");
            btn.setEnabled(false);
        }

        return btn;
    }

    
    public void refreshButtons() {
        buttonGrid.removeAll(); // Clear old buttons

        // Loop through levels from manager
        for (int i = 1; i <= 5; i++) {
            String key = "Level" + i;
            String label = "LEVEL " + i;

            // skip the button if the level doesn't exist (for safety)
            if (GameFrame.manager.getLevel(key) != null) {
                buttonGrid.add(createLevelButton(label, GameFrame.cards, GameFrame.screens, key));
            }
        }

        buttonGrid.revalidate();
        buttonGrid.repaint();
    }
}

/*
this is for safe keeping, orginal code for level select button creation
 private CustomButton createLevelButton(String text, CardLayout cards, JPanel screens, String targetCard) {
CustomButton btn = new CustomButton(text, BUTTON\_SPRITESHEET, 9, 33, 9, 34, BUTTON\_WIDTH, BUTTON\_HEIGHT);
btn.setPreferredSize(new Dimension(100, 100));
btn.setFont(PixelFont.getPixelFont(35));

```
    boolean unlocked = false;
   

    if (targetCard != null && !targetCard.isEmpty()) {
        unlocked = true; // All levels unlocked
    }
    
    // Update button text and enabled status //sa level select does not care if it was already cleared or not, tpos if 
    //player cleared a level in level select mode, it must not update that that level as cleared
    if (unlocked) {
        btn.setText(text);
        btn.setEnabled(true);
    } else {
        btn.setText(text + " (locked)");
        btn.setEnabled(false);
    }

    boolean unlocked1 = unlocked;

  
    btn.addActionListener(e -> {

        if(unlocked1){

            GameFrame.isNormalPlay = false;
            GameFrame.manager.setLevelKey(targetCard);
            GameFrame.levelPanel.setLevel(targetCard);
            GameFrame.cards.show(GameFrame.screens, "GamePanel");
            
            SwingUtilities.invokeLater(() -> {
                GameFrame.menu.menu.setVisible(false);
                GameFrame.hideAllGameOverlays();
                GameFrame.levelPanel.setFocusable(true);
                GameFrame.levelPanel.requestFocusInWindow();
                GameFrame.isGameFrozen = false;
            });
        }else{
            JOptionPane.showMessageDialog(null,
                    "You must complete the previous level to unlock this one.",
                    "Level Locked",
                    JOptionPane.WARNING_MESSAGE);
        }


       
    });

    return btn;
}
```
 */


