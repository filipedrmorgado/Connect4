package pt.isec.a2019137625.Connect4.UI.gui.VistaJogo.ToolBarVista;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import pt.isec.a2019137625.Connect4.Connect4Observer;
import pt.isec.a2019137625.Connect4.Logica.dados.Enum.Situacao;
import pt.isec.a2019137625.Connect4.UI.gui.Resources.CSSManager;

import static pt.isec.a2019137625.Connect4.Logica.Constantes.PROPRIEDADE_DADOS;
import static pt.isec.a2019137625.Connect4.Logica.Constantes.PROPRIEDADE_ESTADO;
import static pt.isec.a2019137625.Connect4.UI.gui.ConstantesGUI.*;

public class ToolBarVirtual extends HBox {
    private final Connect4Observer C4Observer;
    private Button btnAvancar;

    public ToolBarVirtual(Connect4Observer C4Observer){
        this.C4Observer = C4Observer;
        criarLayout();
        disporVista();
        registarListeners();
        registaObservador();
        CSSManager.setCSS(this,CSS_DESIGN_V2);
    }

    private void registaObservador() {
        C4Observer.addPropertyChangeListener(
                PROPRIEDADE_ESTADO,
                e-> this.setVisible(C4Observer.getSituacao() == Situacao.EsperaJogada && C4Observer.getTipoJogador().equalsIgnoreCase("Virtual") ||
                        C4Observer.getSituacao() == Situacao.JogaMiniJogo && C4Observer.getTipoJogador().equalsIgnoreCase("Virtual") ||
                        C4Observer.getSituacao() == Situacao.DecideJogaMiniJogo && C4Observer.getTipoJogador().equalsIgnoreCase("Virtual")));

        C4Observer.addPropertyChangeListener(
                PROPRIEDADE_DADOS,
                e->this.setVisible(C4Observer.getSituacao() == Situacao.EsperaJogada && C4Observer.getTipoJogador().equalsIgnoreCase("Virtual")));
    }

    private void criarLayout() {
        btnAvancar = new Button("_Avançar");
        btnAvancar.setPrefSize(BTN_WIDTH_HUM,BTN_HEIGHT_REP);
    }

    private void disporVista() {
        this.getChildren().addAll(btnAvancar);
        btnAvancar.setAlignment(Pos.CENTER);
        this.setAlignment(Pos.CENTER);
    }

    private void registarListeners() {
        btnAvancar.setOnAction(e-> C4Observer.EfetuaJogada(0));
    }
}
