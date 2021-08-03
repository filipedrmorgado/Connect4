package pt.isec.a2019137625.Connect4.UI.gui.VistaInicioFim;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import pt.isec.a2019137625.Connect4.Connect4Observer;
import pt.isec.a2019137625.Connect4.Logica.dados.Enum.Situacao;
import pt.isec.a2019137625.Connect4.UI.gui.Resources.CSSManager;
import pt.isec.a2019137625.Connect4.UI.gui.Resources.ImageLoader;

import java.io.File;

import static pt.isec.a2019137625.Connect4.Logica.Constantes.PROPRIEDADE_ESTADO;
import static pt.isec.a2019137625.Connect4.UI.gui.ConstantesGUI.*;
import static pt.isec.a2019137625.Connect4.UI.gui.Resources.Utility.changeBackground;
import static pt.isec.a2019137625.Connect4.UI.gui.Resources.Utility.setAlert;

public class VistaInicio extends BorderPane {
    private final Connect4Observer C4Observer;

    private VBox vBoxDisplay;
    private Button btnStart,btnReplay,btnCarrJogo,btnInstrucoes;
    private ImageView imageView;

    public VistaInicio(Connect4Observer c4Observer){
        this.C4Observer = c4Observer;
        criarComponentes();
        disporVista();
        registarListeners();
        registaObservador();
        CSSManager.setCSS(this,CSS_DESIGN_V2);
        changeBackground(this,Color.LIGHTSKYBLUE);
    }

    private void registaObservador(){
        C4Observer.addPropertyChangeListener(
                PROPRIEDADE_ESTADO,
                e-> this.setVisible(C4Observer.getSituacao() == Situacao.Inicio));
    }

    private void criarComponentes() {
        displayLogo();
        setAlignment(imageView,Pos.CENTER);

        btnStart = new Button("Começar Jogo");
        changeBackground(btnStart,Color.CADETBLUE);

        btnCarrJogo = new Button("Carregar um Jogo");
        btnReplay = new Button("Replay de um Jogo");
        btnInstrucoes = new Button("Instruções");

        vBoxDisplay = new VBox(HBOX_SPACING_V1,btnStart,btnReplay,btnCarrJogo,btnInstrucoes);
        vBoxDisplay.setAlignment(Pos.CENTER);
    }

    private void displayLogo(){
        imageView = new ImageView(ImageLoader.getImage(C4_LOGO));
        imageView.setFitHeight(IMG_VIEW_HEIGHT);
        imageView.setFitWidth(IMG_VIEW_WIDTH);
    }

    private void disporVista() {
        this.setPadding(new Insets(BORDER_PADDING));
        this.setCenter(vBoxDisplay);
        this.setTop(imageView);
    }

    private void registarListeners() {
        btnStart.setOnAction(e-> C4Observer.ComecaJogo());
        btnReplay.setOnAction(e->carregaReplay());
        btnCarrJogo.setOnAction(e->carregaJogo());
        btnInstrucoes.setOnAction(e-> setAlert(1,"Para ganhar terá que conectar 4 peças iguais na vertical, horizontal ou diagonal! Boa sorte!","Inicio","Instruções"));
    }

    private void carregaJogo(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("." + File.separator +"Gravacoes"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            String resCarregGrav = C4Observer.carregarJogo(selectedFile);
            if(resCarregGrav.equalsIgnoreCase("Jogo carregado com sucesso!\n"))
                setAlert(2,resCarregGrav,"Inicio","Resultado do carregamento do Jogo");
            else
                setAlert(3,resCarregGrav,"Inicio","Resultado do carregamento do Jogo");
        } else {
            System.err.println("Leitura cancelada!");
        }
    }

    private void carregaReplay(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("." + File.separator + "Replays"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            String resCarregReplay = C4Observer.carregarReplay(selectedFile);
            if(resCarregReplay.equalsIgnoreCase("Replay carregado com sucesso!")){
                setAlert(2,resCarregReplay,"Inicio","Resultado do carregamento do Replay");
                C4Observer.avancaMemJogo();
                C4Observer.ReplayJogo(C4Observer.getSizeStackJogo());
            }else
                setAlert(3,resCarregReplay,"Inicio","Resultado do carregamento do Replay");
        } else {
            System.err.println("Leitura cancelada!");
        }
    }
}
