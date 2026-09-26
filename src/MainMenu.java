package src;

import java.awt.*;
import javax.swing.*;

public class MainMenu extends JPanel {
    private static final String BUTTON_SPRITESHEET = "/resource/Assets/Wooden Pixel Art GUI 32x32.png";
    private static final int BUTTON_WIDTH = 96;
    private static final int BUTTON_HEIGHT = 32;

    public MainMenu() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int w = 400;
        int h = 400;

        setLayout(new BorderLayout());

        // Background panel
        MovingBackground background = new MovingBackground();
        background.setLayout(null);
        add(background, BorderLayout.CENTER);

        // Title panel
        CustomPanel title = createPanel("/resource/Assets/Puzzlebound.png", false,
                (int) screenSize.getWidth() / 2 - 400, 0, 800, 400);
        background.add(title);

        // Button container panel
        CustomPanel container = createPanel(BUTTON_SPRITESHEET, 14, 20, BUTTON_WIDTH, BUTTON_WIDTH,
                (int) screenSize.getWidth() / 2 - w / 2,
                (int) screenSize.getHeight() / 2 - (h - 350),
                w, h);
        container.setLayout(new GridLayout(4, 1, 0, 25));
        background.add(container);

        // Buttons
        container.add(createButton("Play", GameFrame.cards, GameFrame.screens, "GamePanel"));
        container.add(createButton("Level Select", GameFrame.cards, GameFrame.screens, "LevelSelect"));
        container.add(createButton("Instructions", GameFrame.cards, GameFrame.screens, "Instructions"));
        container.add(createButton("Exit", null, null, null));
        
    }

    private CustomPanel createPanel(String image, boolean isTile, int x, int y, int width, int height) {
        CustomPanel panel = new CustomPanel(image, isTile);
        panel.setBounds(x, y, width, height);
        panel.setPreferredSize(new Dimension(width, height));
        panel.setBorder(BorderFactory.createEmptyBorder(75, 75, 75, 75));
        panel.setOpaque(false);
        return panel;
    }

    private CustomPanel createPanel(String image, int sx, int sy, int width, int height,
                                    int x, int y, int w, int h) {
        CustomPanel panel = new CustomPanel(image, sx, sy, width, height);
        panel.setBounds(x, y, w, h);
        panel.setPreferredSize(new Dimension(w, h));
        panel.setBorder(BorderFactory.createEmptyBorder(75, 75, 75, 75));
        panel.setOpaque(false);
        return panel;
    }

    private CustomButton createButton(String text, CardLayout cards, JPanel screens, String screenName) {
        CustomButton btn = new CustomButton(text, BUTTON_SPRITESHEET,
                9, 33, 9, 34, BUTTON_WIDTH, BUTTON_HEIGHT);
        btn.setPreferredSize(new Dimension(200, 100));
        if ("Exit".equals(text)) {
            btn.addActionListener(_ -> {
                GameFrame.music.clicked();
                System.exit(0);
            });
        } else {
            btn.addActionListener(_ -> { 
                GameFrame.music.clicked();
                cards.show(screens, screenName);
            
                if ("GamePanel".equals(screenName)) {
                    GameFrame.isNormalPlay = true;

                    //this ensures that only the normal play level or whatver current progress han player it iya og plaplay
                    GameFrame.manager.setLevelKey(GameFrame.manager.getCurrentLevelKey());
                    GameFrame.levelPanel.setLevel(GameFrame.manager.getCurrentLevelKey());

                    SwingUtilities.invokeLater(() -> {
                        GameFrame.hideAllGameOverlays();
                        GameFrame.menu.menu.setVisible(false); // Ensure menu is off!
                        GameFrame.levelPanel.setFocusable(true);
                        GameFrame.levelPanel.requestFocusInWindow();
                        GameFrame.isGameFrozen = false;
                    });
                }
            });

        }
    return btn;
    }
}
