package pt.isec.a2019137625.Connect4.UI.gui.VistaJogo.Replay;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import pt.isec.a2019137625.Connect4.Connect4Observer;
import pt.isec.a2019137625.Connect4.Logica.dados.Enum.Situacao;
import pt.isec.a2019137625.Connect4.UI.gui.Resources.CSSManager;

import static pt.isec.a2019137625.Connect4.Logica.Constantes.PROPRIEDADE_ESTADO;
import static pt.isec.a2019137625.Connect4.UI.gui.ConstantesGUI.*;
import static pt.isec.a2019137625.Connect4.UI.gui.Resources.Utility.setAlert;

public class ToolBarReplay extends HBox {
    Connect4Observer C4Observer;
    Button btnAvancarReplay;

    public ToolBarReplay(Connect4Observer C4Observer){
        this.C4Observer = C4Observer;
        criarLayout();
        disporVista();
        registarListeners();
        registaObservador();
        CSSManager.setCSS(this,CSS_MAIN_PLAY);
    }

    private void registaObservador() {
        C4Observer.addPropertyChangeListener(
                PROPRIEDADE_ESTADO,
                e-> this.setVisible(C4Observer.getSituacao() == Situacao.ReplayJogo));
    }

    private void criarLayout() {
        btnAvancarReplay = new Button("_Avançar Replay");
        btnAvancarReplay.setPrefSize(BTN_WIDTH_REP,BTN_HEIGHT_REP);
    }

    private void disporVista() {
        this.getChildren().addAll(btnAvancarReplay);
        btnAvancarReplay.setAlignment(Pos.CENTER);
        this.setAlignment(Pos.CENTER);
    }

    private void registarListeners() {
        btnAvancarReplay.setOnAction(e->{
            if(C4Observer.avancaMemJogo().equalsIgnoreCase("Vai visualizar o final do jogo! Vai voltar ao inicio de seguida!"))
            {
                String txtShow = "Chegou ao fim do jogo. " + C4Observer.getVencedor();
                setAlert(2,txtShow,"Replay","Fim do Replay");
            }
            C4Observer.ReplayJogo(C4Observer.getSizeStackJogo());
        });
    }

}
