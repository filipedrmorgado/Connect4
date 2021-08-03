package pt.isec.a2019137625.Connect4.UI.gui.Resources;

import javafx.scene.text.Font;

public class FontsManager {
    private FontsManager() {}

    public static Font loadFont(String name,double size) {
        return Font.loadFont(Resources.getResourceFileAsStream("fonts/"+name),size);
    }
}
