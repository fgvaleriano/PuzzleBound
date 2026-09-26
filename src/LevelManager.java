package src;
import java.util.HashMap;
import java.util.Map;

public class LevelManager {
    private final Map<String, Level> levels = new HashMap<>();
    private String currentLevelKey;
    private String normalPlayCurrentLevel = "Level1"; // For main story play


    public LevelManager() {
        levels.put("Level1", new Level1());
        levels.put("Level2", new Level2());
        levels.put("Level3", new Level3());
        levels.put("Level4", new Level4());

        // Default current level
        currentLevelKey = "Level1";
    }

    //this if for the level selection
    public Level getLevel(String key) {
        return levels.get(key);
    }

    public Level getCurrentLevel() {
        return levels.get(currentLevelKey);
    }

    public String getCurrentLevelKey() {
        if(GameFrame.isNormalPlay){
            return normalPlayCurrentLevel;
        }
        return currentLevelKey;
    }

    public void setLevelKey(String key) {
        if (levels.containsKey(key)) {
            if(GameFrame.isNormalPlay){
                normalPlayCurrentLevel = key;
            }
            
            currentLevelKey = key;
        }
    }

    public String getLevelHint(Level currentLevel){
        return currentLevel.getLevelHint();
    }

    //the following used switch casing rather if-else if for better readability and improve performance
    //kay kun if, you have to check all if statements

    public int getLevelIndex(String levelKey) {
        return switch (levelKey) {
            case "Level1" -> 0;
            case "Level2" -> 1;
            case "Level3" -> 2;
            case "Level4" -> 3;
            default -> -1;
        };
    }

    public String getLevelKeyByIndex(int index) {
        return switch (index) {
            case 0 -> "Level1";
            case 1 -> "Level2";
            case 2 -> "Level3";
            case 3 -> "Level4";
            default -> null;
        };
    }

}
