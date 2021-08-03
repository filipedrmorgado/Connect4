package pt.isec.a2019137625.Connect4.UI.gui.Resources;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

public class Utility {
    private Utility(){}

    public static int getValue(String texto,int maxValue) throws NumberFormatException {
        int value;
        for (char c : texto.toCharArray())
        {
            if (!Character.isDigit(c))
                throw new NumberFormatException();
        }
        value = Integer.parseInt(texto);
        if(value < 0 || value > maxValue)
            throw new NumberFormatException();
        return value;
    }
    
    public static void setAlert(int tipo, String txtPrincipal, String txtTitle, String txtHeader){
        Alert alert;
        if(tipo == 1)
            alert = new Alert(Alert.AlertType.INFORMATION,txtPrincipal);
        else if(tipo == 2)
            alert = new Alert(Alert.AlertType.CONFIRMATION,txtPrincipal);
        else if(tipo == 3)
            alert = new Alert(Alert.AlertType.ERROR,txtPrincipal);
        else
            alert = new Alert(Alert.AlertType.WARNING,txtPrincipal);

        alert.setTitle(txtTitle);
        alert.setHeaderText(txtHeader);
        alert.showAndWait();
    }

    public static String toRGBCode( Color color )
    {
        if(((int)(color.getRed() * 255) > 175) && ((int)(color.getGreen() * 255) > 175) && ((int)(color.getBlue() * 255) > 175) )
            return "Cor clara";

        return String.format( "#%02X%02X%02X",
                (int)( color.getRed() * 255 ),
                (int)( color.getGreen() * 255 ),
                (int)( color.getBlue() * 255 ) );
    }

    public static void changeBackground(Region region, Color color){
        region.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public static int inverteLinha(int linha){
        if(linha == 0){linha = 5; return linha;}
        if(linha == 1){linha = 4;return linha;}
        if(linha == 2){linha = 3;return linha;}
        if(linha == 3){linha = 2;return linha;}
        if(linha == 4){linha = 1;return linha;}
        if(linha == 5){linha = 0;return linha;}
        return linha;
    }


}
