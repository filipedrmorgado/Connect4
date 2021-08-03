package pt.isec.a2019137625.Connect4.UI.gui.Resources;

import javafx.scene.image.Image;

import java.util.HashMap;

public class ImageLoader {
    static HashMap<String,Image> imgCache;
    static {
        imgCache = new HashMap<>();
    }

    public static Image getImage(String name) {
        Image img = imgCache.get(name);
        if (img!=null)
            return img;
        try {
            img = new Image(Resources.getResourceFileAsStream("images/" + name));
            imgCache.put(name,img);
            return img;
        } catch (Exception e) {
            System.err.println("Erro a ler imagem");
        }
        return null;
    }
    public static Image getImageForce(String name) {
        imgCache.remove(name);
        return getImage(name);
    }
}
