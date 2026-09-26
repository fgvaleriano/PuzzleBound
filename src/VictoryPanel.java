package src;

import java.awt.*;
import javax.swing.*;

public class VictoryPanel extends JPanel{   
    public JPanel win;
    private static final String SPRITESHEET = "/resource/Assets/Wooden Pixel Art GUI 32x32.png";

    public VictoryPanel(){
        setLayout(new BorderLayout());
        
        win = new JPanel();
        win.setOpaque(false);
        win.setLayout(new BorderLayout());
        win.setPreferredSize(new Dimension(500,800));
        win.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));;
        win.setVisible(false);
        add(win, BorderLayout.CENTER);

        JPanel container = new JPanel();
        container.setOpaque(false);
        container.setLayout(new BorderLayout());
        container.setVisible(true);
        win.add(container, BorderLayout.CENTER);

        JLabel victory = new JLabel("Victory", SwingConstants.CENTER);
        victory.setForeground(Color.GREEN);
        victory.setFont(PixelFont.getPixelFont(72));
        victory.setVisible(true);
        container.add(victory, BorderLayout.CENTER);

        CustomPanel southContainer = new CustomPanel(SPRITESHEET, 14, 20, 96, 96);
        southContainer.setOpaque(false);
        southContainer.setLayout(new FlowLayout());
        southContainer.setPreferredSize(new Dimension(150, 150));
        southContainer.setBorder(BorderFactory.createEmptyBorder(20, 50, 0, 50));
        southContainer.setVisible(true);
        win.add(southContainer, BorderLayout.SOUTH);

        CustomButton nextLevel = new CustomButton("",SPRITESHEET, 13, 0,13,1, 32, 32);
        nextLevel.addActionListener(_ -> {
            GameFrame.music.clicked();
            win.setVisible(false);

            String currentKey = GameFrame.manager.getCurrentLevelKey();
            int currentIndex = GameFrame.manager.getLevelIndex(currentKey);
            int nextIndex = currentIndex + 1;

            if (nextIndex < 4) {
                String nextLevelKey = GameFrame.manager.getLevelKeyByIndex(nextIndex);
                GameFrame.manager.setLevelKey(nextLevelKey); // ✅ use this!
                GameFrame.levelPanel.setLevel(nextLevelKey);
                GameFrame.isGameFrozen = false;
                GameFrame.cards.show(GameFrame.screens, nextLevelKey);
                GameFrame.hideAllGameOverlays();
                // Proceed with loading the next level

            } else {
            // No more levels, show the level select screen
            GameFrame.levelSelect.refreshButtons();
            GameFrame.isGameFrozen = false;
            GameFrame.cards.show(GameFrame.screens, "LevelSelect");
            GameFrame.hideAllGameOverlays();
            }

        });

        nextLevel.setPreferredSize(new Dimension(100, 100));
        nextLevel.setVisible(true);
        southContainer.add(nextLevel);
        
        CustomButton backSelect = new CustomButton("",SPRITESHEET, 10, 20,10,21, 32, 32);
        backSelect.addActionListener(_ ->{

            GameFrame.music.clicked();
            String currentKey = GameFrame.manager.getCurrentLevelKey();
            int currentIndex = GameFrame.manager.getLevelIndex(currentKey);
            int nextIndex = currentIndex + 1;

            //this updates the level key to the next level
            //even though its an exit way, this just updates the level progress of the player
            //if they plan to exit the level after winning
            //same to back to main menu
            if (nextIndex < 4) {
                String nextLevelKey = GameFrame.manager.getLevelKeyByIndex(nextIndex);
                GameFrame.manager.setLevelKey(nextLevelKey); // use this!
                GameFrame.levelPanel.setLevel(nextLevelKey);
                GameFrame.hideAllGameOverlays();
                // Proceed with loading the next level
            } 

            GameFrame.isNormalPlay = false;
            GameFrame.levelSelect.refreshButtons(); // Call this before showing
            GameFrame.isGameFrozen = false;
            GameFrame.cards.show(GameFrame.screens, "LevelSelect");
            GameFrame.hideAllGameOverlays();
        });

        backSelect.setPreferredSize(new Dimension(100, 100));
        backSelect.setVisible(true);
        southContainer.add(backSelect);

        CustomButton backMain = new CustomButton("",SPRITESHEET, 9, 20,9,21, 32, 32);
        backMain.addActionListener(_ ->{

            GameFrame.music.clicked();
            String currentKey = GameFrame.manager.getCurrentLevelKey();
            int currentIndex = GameFrame.manager.getLevelIndex(currentKey);
            int nextIndex = currentIndex + 1;

            if (nextIndex < 4) {
                String nextLevelKey = GameFrame.manager.getLevelKeyByIndex(nextIndex);
                GameFrame.manager.setLevelKey(nextLevelKey); 
                GameFrame.levelPanel.setLevel(nextLevelKey);
                GameFrame.hideAllGameOverlays();
            } 

            GameFrame.levelSelect.refreshButtons(); // Call this before showing
            GameFrame.isGameFrozen = false;
            GameFrame.cards.show(GameFrame.screens, "MainMenu");
            GameFrame.hideAllGameOverlays();

        });

        backMain.setPreferredSize(new Dimension(100, 100));
        backMain.setVisible(true);
        southContainer.add(backMain);
    }
    
    public void toggleVictoryPanel(){
        boolean show = !win.isVisible();
        win.setVisible(show);
        GameFrame.isGameFrozen = show;
    }

    public void showPanel(boolean visbility){
        win.setVisible(visbility);
        GameFrame.isGameFrozen = visbility; //this lets if visible game is frozen
    }
    
}