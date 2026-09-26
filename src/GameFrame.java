package src;

import java.awt.*;
import javax.swing.*;


public class GameFrame {
    //the following static ensures that all access the same objects
    public static final int TILE_SIZE = 32;
    public static final int LEVEL_WIDTH_IN_TILES = 32;
    public static final int LEVEL_HEIGHT_IN_TILES = 18;
    public static final int GAME_WIDTH = TILE_SIZE * LEVEL_WIDTH_IN_TILES;
    public static final int GAME_HEIGHT = TILE_SIZE * LEVEL_HEIGHT_IN_TILES;
    public static final CardLayout cards = new CardLayout();
    public static final JPanel screens = new JPanel(cards);
    public static final GamePanel levelPanel = new GamePanel();
    public static LevelMenu menu = new LevelMenu();
    public static final GameOverPanel lose = new GameOverPanel();
    public static final VictoryPanel win = new VictoryPanel();
    public static final JLayeredPane background = new JLayeredPane();
    public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static final LevelManager manager = new LevelManager();
    public static final LevelSelect levelSelect = new LevelSelect();
    public static final MusicPlayer music = new MusicPlayer();
    public static boolean isGameFrozen = false;
    public static boolean isNormalPlay = false;
    public Image logo;
    public ImageIcon icon = new ImageIcon(GameFrame.class.getResource("/resource/Puzzlebound.png"));
        
    public GameFrame() {
        UIManager.put("Button.font", PixelFont.getPixelFont(12f));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Label.font", PixelFont.getPixelFont(12f));
        UIManager.put("Label.foreground", Color.WHITE);
        UIManager.put("TextArea.font", PixelFont.getPixelFont(12f));
        UIManager.put("TextArea.foreground", Color.WHITE);
        
        
        JFrame frame = new JFrame("PuzzleBound");

        JPanel container = new JPanel(new BorderLayout()); //for the all, contain the gamepanel
        container.setBackground(Color.decode("#211F30"));
        container.add(background, BorderLayout.CENTER);

        levelPanel.setBounds((int)screenSize.getWidth()/2-(GAME_WIDTH/2), (int)screenSize.getHeight()/2-(GAME_HEIGHT/2), GAME_WIDTH, GAME_HEIGHT);
        background.add(levelPanel, JLayeredPane.DEFAULT_LAYER);

        menu = new LevelMenu();
        menu.setOpaque(false); //holder for the menu, pra transparent sya
        menu.setBounds((int)screenSize.getWidth()/2-(800/2), (int)screenSize.getHeight()/2-(500/2), 800, 500);
        menu.setVisible(false);
        background.add(menu, JLayeredPane.POPUP_LAYER);
        
        
        win.setOpaque(false);
        win.setBounds((int)screenSize.getWidth()/2-(800/2), (int)screenSize.getHeight()/2-(800/2), 800, 800);
        win.setVisible(false);
        background.add(win, JLayeredPane.POPUP_LAYER);
    
        lose.setOpaque(false);
        lose.setBounds((int)screenSize.getWidth()/2-(800/2), (int)screenSize.getHeight()/2-(800/2), 800, 800);
        lose.setVisible(false);
        background.add(lose, JLayeredPane.POPUP_LAYER);

    
        screens.add(new MainMenu(), "MainMenu");
        screens.add(new LevelSelect(), "LevelSelect");
        screens.add(new Instructions(), "Instructions");

        screens.add(container, "GamePanel");
        cards.show(screens, "MainMenu");
        music.playMusic();
        
        frame.add(screens);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(icon.getImage());
    }

    //this ensure that all of this must close, before playing like switch to switch
    public static void hideAllGameOverlays() {  
        win.win.setVisible(false);         
        lose.losePanel.setVisible(false);  
        lose.setVisible(false);
        win.setVisible(false);
        menu.setVisible(false);            
        isGameFrozen = false;
    }
}