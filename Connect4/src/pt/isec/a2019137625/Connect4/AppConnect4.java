package pt.isec.a2019137625.Connect4;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pt.isec.a2019137625.Connect4.Logica.GestaoJogoDados;
import pt.isec.a2019137625.Connect4.Logica.dados.Enum.Situacao;
import pt.isec.a2019137625.Connect4.UI.gui.C4RootGamePlay;
import pt.isec.a2019137625.Connect4.UI.gui.C4RootInicioConfigFim;
import pt.isec.a2019137625.Connect4.UI.gui.C4RootMiniJogo;
import pt.isec.a2019137625.Connect4.UI.gui.Resources.ImageLoader;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static pt.isec.a2019137625.Connect4.Logica.Constantes.PROPRIEDADE_ESTADO;
import static pt.isec.a2019137625.Connect4.Logica.Constantes.PROPRIEDADE_TERMINAR;
import static pt.isec.a2019137625.Connect4.UI.gui.ConstantesGUI.*;

public class AppConnect4 extends Application {
    Connect4Observer connect4observer;
    @Override
    public void start(Stage primaryStage) {
        GestaoJogoDados gestaoMaquinaEstados = new GestaoJogoDados();
        connect4observer = new Connect4Observer(gestaoMaquinaEstados);
        // Criação do stage principal, o jogo
        primStage(primaryStage,connect4observer);
        // Criacao do stage referente aos Mini Jogos
        Stage secondaryStage = new Stage();
        secStage(secondaryStage,connect4observer);
        // Criação do stage para lidar com o inicio, fim e configuração dos jogos
        Stage thirdStage = new Stage();
        thiStage(thirdStage,connect4observer);
        registaObserver(connect4observer,primaryStage,secondaryStage,thirdStage);

        connect4observer.addPropertyChangeListener(PROPRIEDADE_TERMINAR,new ProcCloseStage(primaryStage,true));
        connect4observer.addPropertyChangeListener(PROPRIEDADE_TERMINAR,new ProcCloseStage(secondaryStage,true));
        connect4observer.addPropertyChangeListener(PROPRIEDADE_TERMINAR,new ProcCloseStage(thirdStage,true));
       // criacaoDoClone(connect4observer);
    }

    private void registaObserver(Connect4Observer connect4observer,Stage primaryStage,Stage secondaryStage,Stage thirdStage){
        connect4observer.addPropertyChangeListener(
                PROPRIEDADE_ESTADO,
                e-> {
                    if(connect4observer.getSituacao() == Situacao.Inicio ||
                            connect4observer.getSituacao() == Situacao.EscolheTipoJogadores ||
                            connect4observer.getSituacao() == Situacao.DefineNomes ||
                            connect4observer.getSituacao() == Situacao.FimJogo )
                        thirdStage.show();

                    else
                        thirdStage.hide();

                    if(connect4observer.getSituacao() == Situacao.DecideJogaMiniJogo ||
                            connect4observer.getSituacao() == Situacao.JogaMiniJogo)
                        secondaryStage.show();
                    else
                        secondaryStage.hide();

                    if(connect4observer.getSituacao() == Situacao.EsperaJogada ||
                            connect4observer.getSituacao() == Situacao.ReplayJogo ||
                            connect4observer.getSituacao() == Situacao.DecideJogaMiniJogo ||
                            connect4observer.getSituacao() == Situacao.JogaMiniJogo)
                        primaryStage.show();
                    else
                        primaryStage.hide();
                });
    }

    private void primStage(Stage primaryStage,Connect4Observer connect4observer){
        C4RootGamePlay c4RootGamePlay = new C4RootGamePlay(connect4observer);
        primaryStage.setTitle("Connect4 GamePlay");
        primaryStage.setScene(new Scene(c4RootGamePlay.obtemRootPane(),STG_PRI_HEIGHT,STG_PRI_WIDTH));
        primaryStage.getIcons().add(ImageLoader.getImage(C4_ICON));
        primaryStage.setMinHeight(STG_PRI_HEIGHT);
        primaryStage.setMinWidth(STG_PRI_WIDTH);
    }
    private void secStage(Stage secondaryStage,Connect4Observer connect4observer) {
        C4RootMiniJogo c4RootJogo = new C4RootMiniJogo(connect4observer);
        secondaryStage.setTitle("Connect4 MiniJogo");
        secondaryStage.setScene(new Scene(c4RootJogo.obtemRootPane(),STG_MAX_WIDTH_HEIGHT,STG_WIDTH_HEIGHT_MIN));
        secondaryStage.setResizable(false);
        secondaryStage.getIcons().add(ImageLoader.getImage(C4_ICON));
        secondaryStage.initModality(Modality.APPLICATION_MODAL);
    }

    private void thiStage(Stage thirdStage,Connect4Observer connect4observer) {
        C4RootInicioConfigFim c4RootInicioConfigFim = new C4RootInicioConfigFim(connect4observer);
        thirdStage.setTitle("Connect4 Configuração");
        thirdStage.setScene(new Scene(c4RootInicioConfigFim.obtemRootPane(),STG_WIDTH_HEIGHT_MIN,STG_HEIGHT_MIN));
        thirdStage.setResizable(false);
        thirdStage.getIcons().add(ImageLoader.getImage(C4_ICON));
        thirdStage.show();
    }

    class ProcCloseStage implements PropertyChangeListener {
        Stage stage;
        boolean closeAll;

        public ProcCloseStage(Stage stage, boolean closeAll) {
            this.stage = stage;
            this.closeAll = closeAll;
            stage.setOnCloseRequest(e-> {
                if (closeAll)
                    connect4observer.terminar();
                else
                    connect4observer.removeListener(PROPRIEDADE_TERMINAR,this);
            });
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            connect4observer.removeListener(evt.getPropertyName(),this);
            stage.close();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void criacaoDoClone(Connect4Observer connect4observer) {
        C4RootGamePlay c4Root1 = new C4RootGamePlay(connect4observer);
        Stage primaryStage1 = new Stage();
        primaryStage1.setTitle("Connect4 GamePlay");
        primaryStage1.setScene(new Scene(c4Root1.obtemRootPane(),STG_PRI_HEIGHT,STG_PRI_WIDTH));
        primaryStage1.getIcons().add(ImageLoader.getImage(C4_ICON));
        primaryStage1.setMinHeight(STG_PRI_HEIGHT);
        primaryStage1.setMinWidth(STG_PRI_WIDTH);

        Stage secondaryStage1 = new Stage();
        C4RootMiniJogo c4RootJogo1 = new C4RootMiniJogo(connect4observer);
        secondaryStage1.setTitle("MiniJogo");
        secondaryStage1.setScene(new Scene(c4RootJogo1.obtemRootPane(),STG_MAX_WIDTH_HEIGHT,STG_WIDTH_HEIGHT_MIN));
        secondaryStage1.setResizable(false);
        secondaryStage1.getIcons().add(ImageLoader.getImage(C4_ICON));
        secondaryStage1.initModality(Modality.APPLICATION_MODAL);

        Stage thirdStage1 = new Stage();
        C4RootInicioConfigFim c4RootInicioConfigFim1 = new C4RootInicioConfigFim(connect4observer);
        thirdStage1.setTitle("Connect4 Configuração");
        thirdStage1.setScene(new Scene(c4RootInicioConfigFim1.obtemRootPane(),STG_WIDTH_HEIGHT_MIN,STG_HEIGHT_MIN));
        thirdStage1.setResizable(false);
        thirdStage1.getIcons().add(ImageLoader.getImage(C4_ICON));
        thirdStage1.show();

        connect4observer.addPropertyChangeListener(PROPRIEDADE_TERMINAR,new ProcCloseStage(primaryStage1,true));
        connect4observer.addPropertyChangeListener(PROPRIEDADE_TERMINAR,new ProcCloseStage(secondaryStage1,true));
        connect4observer.addPropertyChangeListener(PROPRIEDADE_TERMINAR,new ProcCloseStage(thirdStage1,true));


        connect4observer.addPropertyChangeListener(
                PROPRIEDADE_ESTADO,
                e-> {
                    if(connect4observer.getSituacao() == Situacao.Inicio ||
                            connect4observer.getSituacao() == Situacao.EscolheTipoJogadores ||
                            connect4observer.getSituacao() == Situacao.DefineNomes ||
                            connect4observer.getSituacao() == Situacao.FimJogo )
                        thirdStage1.show();

                    else
                        thirdStage1.hide();

                    if(connect4observer.getSituacao() == Situacao.DecideJogaMiniJogo ||
                            connect4observer.getSituacao() == Situacao.JogaMiniJogo)
                        secondaryStage1.show();
                    else
                        secondaryStage1.hide();

                    if(connect4observer.getSituacao() == Situacao.EsperaJogada ||
                            connect4observer.getSituacao() == Situacao.ReplayJogo ||
                            connect4observer.getSituacao() == Situacao.DecideJogaMiniJogo ||
                            connect4observer.getSituacao() == Situacao.JogaMiniJogo)
                        primaryStage1.show();
                    else
                        primaryStage1.hide();


                });
    }
}
