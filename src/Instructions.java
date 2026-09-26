package src;

import java.awt.*;
import javax.swing.*;

public class Instructions extends JPanel {
    private static final String BUTTON_SPRITESHEET = "/resource/Assets/Wooden Pixel Art GUI 32x32.png";
    private static final int BUTTON_WIDTH = 96;
    private static final int BUTTON_HEIGHT = 32;
    private GameFrame frame;

    public Instructions() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int w = 400;
        int h = 400;

        setLayout(new BorderLayout());

        MovingBackground background = new MovingBackground();
        background.setBorder(BorderFactory.createEmptyBorder(150, 250, 150, 250));
        background.setLayout(new BorderLayout());
        add(background, BorderLayout.CENTER);

        CustomPanel howTo = new CustomPanel("/resource/Assets/Instruction.png", false);
        howTo.setOpaque(false);
        howTo.setLayout(null);
        howTo.setVisible(true);
        background.add(howTo, BorderLayout.CENTER);

        CustomButton back = new CustomButton(" ", BUTTON_SPRITESHEET, 0, 0, 0, 1, 32, 32);
        back.setBounds(185, 10, 100, 100);
        back.addActionListener(_ -> {
            GameFrame.music.clicked();
            GameFrame.cards.show(GameFrame.screens, "MainMenu");   
        });
        howTo.add(back);
    }
}
