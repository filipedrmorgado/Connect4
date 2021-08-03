package pt.isec.a2019137625.Connect4.UI.gui.Resources;

import javafx.scene.Parent;

public class CSSManager {
    private CSSManager() {}

    public static void setCSS(Parent parent, String name) {
        parent.getStylesheets().add(Resources.getResourceFilename("css/"+name));
    }
}
