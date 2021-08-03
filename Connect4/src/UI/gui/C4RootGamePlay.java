package pt.isec.a2019137625.Connect4.UI.gui;

import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import pt.isec.a2019137625.Connect4.Connect4Observer;
import pt.isec.a2019137625.Connect4.UI.gui.VistaJogo.VistaJogo;


public class C4RootGamePlay extends BorderPane {
    private final Connect4Observer C4Observer;
    // Organização das Vistas
    private StackPane rootPane;
    private VistaJogo vistaJogo;

    public C4RootGamePlay(Connect4Observer C4Observer){
        this.C4Observer = C4Observer;
        criarComponentes();
        disporVista();
    }

    public Parent obtemRootPane() {
        return rootPane;
    }

    private void criarComponentes() {
        rootPane = new StackPane();
        vistaJogo = new VistaJogo(C4Observer);
    }

    private void disporVista() {
        vistaJogo.setVisible(true);
        rootPane.getChildren().addAll(vistaJogo);
    }
}
