package pt.isec.a2019137625.Connect4.UI.gui.VistaMiniJogo;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import pt.isec.a2019137625.Connect4.Connect4Observer;
import pt.isec.a2019137625.Connect4.Logica.dados.Enum.Situacao;
import pt.isec.a2019137625.Connect4.UI.gui.Resources.CSSManager;
import pt.isec.a2019137625.Connect4.UI.gui.Resources.FontsManager;
import pt.isec.a2019137625.Connect4.UI.gui.Resources.ImageLoader;
import pt.isec.a2019137625.Connect4.UI.gui.Resources.MusicPlayer;

import java.util.Timer;
import java.util.TimerTask;

import static pt.isec.a2019137625.Connect4.Logica.Constantes.*;
import static pt.isec.a2019137625.Connect4.UI.gui.ConstantesGUI.*;
import static pt.isec.a2019137625.Connect4.UI.gui.Resources.Utility.setAlert;

public class VistaJogaMiniJogo extends BorderPane {
    private final Connect4Observer C4Observer;
    private Label questaoJogo,introducaoJogo,lblTimer,lblTimerNumb,lblAcertouPerg;
    private TextField txtResposta;
    private Button btnResponde;
    private VBox vBoxPerg;
    private HBox hBoxResposta,hBoxTimer;
    private Timer timer;

    public VistaJogaMiniJogo(Connect4Observer c4Observer){
        this.C4Observer = c4Observer;
        criarComponentes();
        disporVista();
        registarListeners();
        registaObserver();
        imagemFundo();
        CSSManager.setCSS(this,CSS_DESIGN_V2);
    }

    private void imagemFundo(){
        Image imgTeste = ImageLoader.getImage(BG_MATH);
        BackgroundSize backgroundSize = new BackgroundSize(this.getWidth(), this.getHeight(), true, true, true, true);
        if(imgTeste != null)
            this.setBackground(new Background(new BackgroundImage(imgTeste,BackgroundRepeat.SPACE, BackgroundRepeat.SPACE, BackgroundPosition.CENTER,backgroundSize)));
    }

    private void registaObserver() {
        C4Observer.addPropertyChangeListener(
                PROPRIEDADE_ESTADO,
                e-> atualizaVista());
        C4Observer.addPropertyChangeListener(
                PROPRIEDADE_MINIJOGO,
                e-> configuraDados());
        C4Observer.addPropertyChangeListener(
                PROPRIEDADE_TERMINAR,
                e-> pararTimer());
    }

    private void criarComponentes() {
        introducaoJogo = new Label();
        lblTimer = new Label("Tempo para responder:");
        lblTimerNumb = new Label();
        lblAcertouPerg = new Label();
        questaoJogo = new Label();
        txtResposta = new TextField();
        txtResposta.setPromptText("Insira a resposta");
        btnResponde = new Button("R_esponder");
        btnResponde.setPrefSize(BTN_WIDTH_HIGH,BTN_HEIGHT_LOW);
        hBoxResposta = new HBox(txtResposta,btnResponde);
        hBoxTimer = new HBox(lblTimer,lblTimerNumb);
        vBoxPerg = new VBox();
        definicaoFontes();
    }

    private void definicaoFontes(){
        introducaoJogo.setFont(FontsManager.loadFont(HEADER_FONT,TXT_FONT_SIZE_HIGH));
        lblTimer.setFont(FontsManager.loadFont(TXT_FONT,TXT_FONT_SIZE_HIGH));
        lblTimerNumb.setFont(FontsManager.loadFont(TXT_FONT,TXT_FONT_SIZE_HIGH));
        questaoJogo.setFont(FontsManager.loadFont(TXT_FONT,TXT_FONT_SIZE_HIGH));
        txtResposta.setFont(FontsManager.loadFont(TXT_FONT,TXT_FONT_SIZE_HIGH));
        lblAcertouPerg.setFont(FontsManager.loadFont(TXT_FONT,TXT_FONT_SIZE_HIGH));
    }

    private void disporVista() {
        hBoxResposta.setPadding(new Insets(HBOX_PADDING_LOW));
        hBoxResposta.setSpacing(HBOX_SPACING_V1);
        hBoxResposta.setAlignment(Pos.CENTER);
        hBoxTimer.setPadding(new Insets(HBOX_PADDING_LOW));
        hBoxTimer.setSpacing(HBOX_SPACING_V1);
        hBoxTimer.setAlignment(Pos.CENTER);

        questaoJogo.setAlignment(Pos.CENTER);
        questaoJogo.setTextAlignment(TextAlignment.CENTER);
        lblAcertouPerg.setAlignment(Pos.CENTER);
        lblAcertouPerg.setTextAlignment(TextAlignment.CENTER);
        vBoxPerg.getChildren().addAll(introducaoJogo,questaoJogo,hBoxResposta,hBoxTimer,lblAcertouPerg);
        vBoxPerg.setAlignment(Pos.CENTER);
        this.setCenter(vBoxPerg);
        this.setPadding(new Insets(HBOX_PADDING_LOW));
    }

    private void registarListeners() {
        btnResponde.setOnAction(e->{
            String resposta = txtResposta.getText().trim();
            if(resposta.isEmpty()){
                setAlert(4,"A resposta encontra-se vazia. Preencha.","Resposta","Informação sobre a questão");
                System.out.println("Tem que inserir a resposta");
                return;
            }
            C4Observer.RespostaMiniJogo(resposta);
            verificaEstadoJogo();
        });
    }

    private void atualizaVista(){
        if(C4Observer.getSituacao() == Situacao.JogaMiniJogo){
            criaTimer();
            configuraDados();
            configuraLblPerg();
            this.setVisible(true);
        }else{
            pararTimer();
            this.setVisible(false);
        }
    }

    private void configuraLblPerg(){
        lblAcertouPerg.setVisible(C4Observer.getNomeMiniJogo().contains("Matemática"));
        lblAcertouPerg.setText("");
        lblTimerNumb.setTextFill(Color.BLACK);
    }

    private void criaTimer() {
        pararTimer();
        timer = new Timer(true);
            timer.scheduleAtFixedRate(new TimerTask() {
            int timePassed = (int) C4Observer.getTempoAcerto();
                public void run() {
                    if(timePassed > 0){
                        Platform.runLater(() -> lblTimerNumb.setText(String.valueOf(timePassed--)));
                        if(timePassed == 10)
                            lblTimerNumb.setTextFill(Color.RED);
                    }
                    else{
                        Platform.runLater(() -> {
                            C4Observer.RespostaMiniJogo(" ");
                            verificaEstadoJogo();
                        });
            }}}, 0,TIMER);
    }

    private void pararTimer() {
        if(timer!=null){
            timer.purge(); // Removes all cancelled tasks from this timer's task queue.
            timer.cancel(); // Terminates this timer, discarding any currently scheduled tasks.
            timer = null;
        }
    }

    private void configuraDados(){
        txtResposta.setText("");
        txtResposta.requestFocus();
        introducaoJogo.setText(C4Observer.getNomeMiniJogo());
        questaoJogo.setText(C4Observer.getPerguntaMiniJogo());
        lblAcertouPerg.setText(C4Observer.getAcertouPergunta());
    }

    private void verificaEstadoJogo(){
        String estadoJogo = C4Observer.getEstadoJogo();
        if(estadoJogo.contains("Ganhou"))
            MusicPlayer.playMusic(MINI_WINNER);
        if(estadoJogo.contains("Perdeu"))
            MusicPlayer.playMusic(MINI_LOSER);
        if(estadoJogo.contains("Ganhou") || estadoJogo.contains("Perdeu")){
            pararTimer();
            setAlert(1,C4Observer.getEstadoJogo(),C4Observer.getNomeMiniJogo(),"Resultado do Jogo");
        }
    }

}
