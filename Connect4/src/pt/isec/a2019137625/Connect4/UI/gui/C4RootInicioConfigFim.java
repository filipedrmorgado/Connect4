package pt.isec.a2019137625.Connect4.UI.gui;

import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import pt.isec.a2019137625.Connect4.Connect4Observer;
import pt.isec.a2019137625.Connect4.UI.gui.VistaInicioFim.VistaConfigura;
import pt.isec.a2019137625.Connect4.UI.gui.VistaInicioFim.VistaFimJogo;
import pt.isec.a2019137625.Connect4.UI.gui.VistaInicioFim.VistaInicio;

public class C4RootInicioConfigFim extends BorderPane {
    private final Connect4Observer C4Observer;

    private StackPane rootPane;
    private VistaInicio vistaInicio;
    private VistaConfigura vistaConfigura;
    private VistaFimJogo vistaFimJogo;

    public C4RootInicioConfigFim(Connect4Observer c4Observer){
        this.C4Observer = c4Observer;
        criarComponentes();
        disporVista();
    }

    public Parent obtemRootPane() {
        return rootPane;
    }

    private void criarComponentes() {
        rootPane = new StackPane();
        vistaConfigura = new VistaConfigura(C4Observer);
        vistaInicio = new VistaInicio(C4Observer);
        vistaFimJogo = new VistaFimJogo(C4Observer);
    }

    private void disporVista() {
        vistaInicio.setVisible(true);
        vistaConfigura.setVisible(false);
        vistaFimJogo.setVisible(false);
        rootPane.getChildren().addAll(vistaInicio,vistaConfigura, vistaFimJogo);
    }
}
