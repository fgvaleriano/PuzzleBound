package src;

import java.awt.*;
import javax.swing.*;

public class LevelMenu extends JPanel {
    private static final String SPRITESHEET = "/resource/Assets/Wooden Pixel Art GUI 32x32.png";
    private static final int BUTTON_WIDTH = 32;
    private static final int BUTTON_HEIGHT = 32;

    public CustomPanel menu;
    private JTextArea hintLabel;

    public LevelMenu() {
        setLayout(new BorderLayout());

        menu = new CustomPanel(SPRITESHEET, 14, 20, 96, 96);
        menu.setOpaque(false);
        menu.setLayout(new BorderLayout());
        menu.setPreferredSize(new Dimension(500, 800));
        menu.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        menu.setVisible(false);
        add(menu, BorderLayout.CENTER);

        // Center: hint container
        JPanel centerContainer = createTransparentPanel(new BorderLayout());
        centerContainer.setBorder(BorderFactory.createEmptyBorder(200, 100, 0, 100));
        hintLabel = new JTextArea(GameFrame.levelPanel.getHint(), 3, 5);
        hintLabel.setBackground(new Color(0,0,0,0));
        hintLabel.setEditable(false);
        hintLabel.setLineWrap(true);         // Enable line wrapping
        hintLabel.setWrapStyleWord(true);
        hintLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerContainer.add(hintLabel, BorderLayout.CENTER);  // Add the hint label here
        menu.add(centerContainer, BorderLayout.CENTER);

        // South: buttons
        JPanel southContainer = createTransparentPanel(new FlowLayout());
        southContainer.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        menu.add(southContainer, BorderLayout.SOUTH);

        southContainer.add(createActionButton(11, _ -> {
            GameFrame.music.clicked();
            toggleMenu();
        })); // Resume

        southContainer.add(createActionButton(10, _ -> {
            GameFrame.music.clicked();
            menu.setVisible(false);
            GameFrame.cards.show(GameFrame.screens, "LevelSelect");
        })); // Back to Level Select

        southContainer.add(createActionButton(9, _ -> {
            GameFrame.music.clicked();
            menu.setVisible(false);
            GameFrame.cards.show(GameFrame.screens, "MainMenu");
        })); // Back to Main Menu
    }

    public void updateHint() {
        // Update the hint text based on the current level
        String newHint = GameFrame.levelPanel.getHint();  // Get the current level's hint
        hintLabel.setText(newHint);  // Update the JLabel with the new hint
    }

    private JPanel createTransparentPanel(LayoutManager layout) {
        JPanel panel = new JPanel(layout);
        panel.setOpaque(false);
        return panel;
    }

    private CustomButton createActionButton(int spriteY, java.awt.event.ActionListener action) {
        CustomButton btn = new CustomButton("", SPRITESHEET, spriteY, 20, spriteY, 21, BUTTON_WIDTH, BUTTON_HEIGHT);
        btn.setPreferredSize(new Dimension(100, 100));
        btn.addActionListener(action);
        return btn;
    }

    public void toggleMenu() {
        boolean willShowMenu = !menu.isVisible();

        // Prevent opening of level menu if Win or Lose panel is visible
        if (willShowMenu && (GameFrame.win.isVisible() || GameFrame.lose.isVisible())) {
            return; // Cancel opening the menu
        }

        this.setVisible(willShowMenu);
        menu.setVisible(willShowMenu);

        //the following switch the focus between the menu and the level panel
        //depedning on the menu visibility
        if (willShowMenu) {
            menu.setFocusable(true);
            menu.requestFocusInWindow();

            GameFrame.levelPanel.setFocusable(false);
            GameFrame.isGameFrozen = true;
        } else {
            menu.setFocusable(false);

            GameFrame.levelPanel.setFocusable(true);
            GameFrame.levelPanel.requestFocusInWindow();
            GameFrame.isGameFrozen = false;
        }
    }

}

