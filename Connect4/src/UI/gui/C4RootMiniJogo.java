package pt.isec.a2019137625.Connect4.UI.gui;

import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import pt.isec.a2019137625.Connect4.Connect4Observer;
import pt.isec.a2019137625.Connect4.UI.gui.VistaMiniJogo.VistaDecideMiniJogo;
import pt.isec.a2019137625.Connect4.UI.gui.VistaMiniJogo.VistaJogaMiniJogo;

public class C4RootMiniJogo extends BorderPane {
    private final Connect4Observer C4Observer;

    private StackPane rootPane;
    private VistaDecideMiniJogo vistaDecideMiniJogo;
    private VistaJogaMiniJogo vistaJogaMiniJogo;

    public C4RootMiniJogo(Connect4Observer c4Observer){
        this.C4Observer = c4Observer;
        criarComponentes();
        disporVista();
    }

    public Parent obtemRootPane() {
        return rootPane;
    }

    private void criarComponentes() {
        rootPane = new StackPane();
        vistaDecideMiniJogo = new VistaDecideMiniJogo(C4Observer);
        vistaJogaMiniJogo = new VistaJogaMiniJogo(C4Observer);
    }

    private void disporVista() {
        vistaDecideMiniJogo.setVisible(true);
        vistaJogaMiniJogo.setVisible(false);
        rootPane.getChildren().addAll(vistaDecideMiniJogo, vistaJogaMiniJogo);
    }

}
