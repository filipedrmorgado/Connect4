package pt.isec.a2019137625.Connect4.UI.gui.VistaInicioFim;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.a2019137625.Connect4.Connect4Observer;
import pt.isec.a2019137625.Connect4.Logica.dados.Enum.Situacao;
import pt.isec.a2019137625.Connect4.UI.gui.Resources.CSSManager;
import pt.isec.a2019137625.Connect4.UI.gui.Resources.FontsManager;
import pt.isec.a2019137625.Connect4.UI.gui.Resources.ImageLoader;

import static pt.isec.a2019137625.Connect4.Logica.Constantes.PROPRIEDADE_ESTADO;
import static pt.isec.a2019137625.Connect4.UI.gui.ConstantesGUI.*;
import static pt.isec.a2019137625.Connect4.UI.gui.Resources.Utility.*;

public class VistaConfigura extends BorderPane {
    private final Connect4Observer C4Observer;

    private ToggleGroup radioGroup;
    private RadioButton twoPlayer,onePlayerOneVrt,twoVirt;
    private TextField nomeJog1,nomeJog2;
    private ColorPicker jog1Color, jog2Color;
    private Button btnAvancar,btnSair;
    private Label lblconfig;
    private HBox hBoxJog1,hBoxJog2,hBoxBtns,hBoxPicker;
    private VBox vBoxConfig;
    private ImageView imageView;

    public VistaConfigura(Connect4Observer c4Observer){
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
                e-> this.setVisible(C4Observer.getSituacao() == Situacao.EscolheTipoJogadores ||
                        C4Observer.getSituacao() == Situacao.DefineNomes));
    }

    private void criarComponentes() {
        lblconfig = new Label("Escolha o modo de jogo");
        btnAvancar = new Button("Avançar");
        btnSair = new Button("Voltar ao Menu");
        hBoxBtns = new HBox(btnAvancar,btnSair);
        hBoxBtns.setSpacing(HBOX_SPACING_V1);
        // Radio Button
        twoPlayer = new RadioButton("2 Jogadores Humanos");
        onePlayerOneVrt = new RadioButton("1 Jogador Humano e 1 Virtual");
        twoVirt = new RadioButton("2 Jogadores Virtuais");
        radioGroup = new ToggleGroup();
        twoPlayer.setToggleGroup(radioGroup);
        onePlayerOneVrt.setToggleGroup(radioGroup);
        twoVirt.setToggleGroup(radioGroup);
        twoPlayer.setSelected(true);
        twoPlayer.requestFocus();
        criaPickers(); // Criação dos ColorPickers
        inputJogadores(); // Criação de inputs jogadores
        setFonts(); // Atribuição das Fonts
        vBoxConfig = new VBox(HBOX_SPACING_V1);
        vBoxConfig.getChildren().addAll(lblconfig,twoPlayer,onePlayerOneVrt,twoVirt,hBoxJog1,hBoxJog2,hBoxPicker,hBoxBtns);
    }

    private void setFonts(){
        lblconfig.setFont(FontsManager.loadFont(HEADER_FONT,HEADER_FONT_SIZE));
        twoPlayer.setFont(FontsManager.loadFont(TXT_FONT, TXT_FONT_SIZE_MEDIUM));
        onePlayerOneVrt.setFont(FontsManager.loadFont(TXT_FONT, TXT_FONT_SIZE_MEDIUM));
        twoVirt.setFont(FontsManager.loadFont(TXT_FONT, TXT_FONT_SIZE_MEDIUM));
    }

    private void criaPickers(){
        // Color pickers
        jog1Color = new ColorPicker(Color.RED);
        jog1Color.setStyle(PICKER_STYLE);
        jog1Color.setMaxWidth(PICKER_WIDTH);

        jog2Color = new ColorPicker(Color.YELLOW);
        jog2Color.setStyle(PICKER_STYLE);
        jog2Color.setMaxWidth(PICKER_WIDTH);
        hBoxPicker = new HBox(jog1Color,jog2Color);
        hBoxPicker.setSpacing(HBOX_SPACING_V1);
    }

    private void inputJogadores(){
        // Jogador 1
        Label lbljog1 = new Label("Jogador 1");
        lbljog1.setFont(FontsManager.loadFont(TXT_FONT, TXT_FONT_SIZE_HIGH));
        nomeJog1 = new TextField();
        nomeJog1.setMinWidth(TEXTFIELD_WIDTH_HIGH);
        nomeJog1.setPromptText("Ex: Joao");
        nomeJog1.setFont(FontsManager.loadFont(TXT_FONT, TXT_FONT_SIZE_LOW));
        hBoxJog1 = new HBox(HBOX_SPACING_V2, lbljog1,nomeJog1);
        // Jogador 2
        Label lbljog2 = new Label("Jogador 2");
        lbljog2.setFont(FontsManager.loadFont(TXT_FONT, TXT_FONT_SIZE_HIGH));
        nomeJog2 = new TextField();
        nomeJog2.setMinWidth(TEXTFIELD_WIDTH_HIGH);
        nomeJog2.setFont(FontsManager.loadFont(TXT_FONT, TXT_FONT_SIZE_LOW));
        nomeJog2.setPromptText("Ex: Lola");
        hBoxJog2 = new HBox(HBOX_SPACING_V1, lbljog2,nomeJog2);
        listenersInput();
    }

    private void listenersInput(){
        nomeJog1.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                nomeJog1.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
            if(newValue.length() > 7) nomeJog1.setText(oldValue);
        });
        nomeJog2.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                nomeJog2.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
            if(newValue.length() > 7) nomeJog2.setText(oldValue);
        });
    }

    private void disporVista() {
        displayLogo();
        setAlignment(imageView,Pos.CENTER);
        hBoxJog1.setAlignment(Pos.CENTER);
        hBoxJog2.setAlignment(Pos.CENTER);
        hBoxBtns.setAlignment(Pos.CENTER);
        hBoxPicker.setAlignment(Pos.CENTER);
        vBoxConfig.setAlignment(Pos.CENTER);
        this.setTop(imageView);
        this.setCenter(vBoxConfig);
    }

    private void displayLogo(){
        imageView = new ImageView(ImageLoader.getImage(C4_LOGO));
        imageView.setFitHeight(IMG_VIEW_HEIGHT);
        imageView.setFitWidth(IMG_VIEW_WIDTH);
    }

    private void registarListeners() {
        btnAvancar.setOnAction(e->{
            if(nomeJog1.getText().isEmpty() || nomeJog2.getText().isEmpty()){
                setAlert(4,"Tem que preencher ambos os nomes!","Configuração","Definição dos nomes, tipo de jogo e cores");
                return;
            }
            if(nomeJog1.getText().trim().equalsIgnoreCase(nomeJog2.getText().trim())) {
                setAlert(4, "Tem que inserir nomes diferentes!", "Configuração", "Definição dos nomes, tipo de jogo e cores");
                return;
            }
            if(jog1Color.getValue().equals(jog2Color.getValue())){
                setAlert(4,"Tem que inserir cores diferentes!","Configuração","Definição dos nomes, tipo de jogo e cores");
                return;
            }
            if(toRGBCode(jog1Color.getValue()).equalsIgnoreCase("Cor clara") || toRGBCode(jog2Color.getValue()).equalsIgnoreCase("Cor clara")){
                setAlert(4,"Não pode inserir cores demasiado claras!","Configuração","Definição dos nomes, tipo de jogo e cores");
                return;
            }
            geraTipoJogo();
            C4Observer.EscolheNomes(nomeJog1.getText(),nomeJog2.getText(),toRGBCode(jog1Color.getValue()),toRGBCode(jog2Color.getValue()));
            atualizaVista();
        });

        btnSair.setOnAction(e-> C4Observer.RecomecaJogo());
    }

    private void geraTipoJogo(){
        RadioButton selectedRadioButton = (RadioButton) radioGroup.getSelectedToggle();
        if(selectedRadioButton.equals(twoPlayer))
            C4Observer.EscolheTipoJog(1);
        else if(selectedRadioButton.equals(onePlayerOneVrt))
            C4Observer.EscolheTipoJog(2);
        else if(selectedRadioButton.equals(twoVirt))
            C4Observer.EscolheTipoJog(3);
    }

    private void atualizaVista() {
        nomeJog1.setText("");
        nomeJog2.setText("");
        jog1Color.setValue(Color.RED);
        jog2Color.setValue(Color.YELLOW);
    }

}
