package controllers.subsidiary;

import com.google.inject.Inject;
import controllers.Controller;
import controllers.main.matchmaking.ControllerMatchMaking;
import gui.windows.WindowType;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import main.AGame;
import managment.paramManagement.ParamsManager;
import java.net.URL;
import java.util.ResourceBundle;

public final class ControllerParams implements Initializable, Controller {

    @Inject
    private AGame aGame;

    //Global:
    @FXML
    private Pane paramsPane;

    @FXML
    private AnchorPane paramScreens;

    @FXML
    private Button buttonAccept;

    @FXML
    private Button buttonBack;

    @FXML
    private Text paramMessage;

    //Params:
    @FXML
    private Pane parametersPane;

    @FXML
    private Button buttonVideoParams;

    @FXML
    private Button buttonSoundParams;

    @FXML
    private Button buttonGameParams;

    @FXML
    private Button hotKeyParams;

    //Panes:
    @FXML
    private AnchorPane videoParamsPane;

    @FXML
    private AnchorPane soundParamsPane;

    @FXML
    private AnchorPane gameParamsPane;

    @FXML
    private AnchorPane hotKeyParamsPane;

    private final ParamsManager paramsManager = new ParamsManager();

    @Override
    public final void initialize(URL location, ResourceBundle resources) {


    }

    @Override
    public final void appearance() {

    }

    //Style & Interface:
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public final void buttonAcceptClicked(){
        paramMessage.setOpacity(1);
        final FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), paramMessage);
        fadeTransition.setToValue(0);
        fadeTransition.play();
    }

    public final void buttonBackClicked(){
        paramsPane.setVisible(false);
        final ControllerMatchMaking matchMaking = (ControllerMatchMaking) aGame.getWindowMap()
                .get(WindowType.MATCHMAKING).getController();
        matchMaking.getMenuPane().setVisible(true);
    }

    public final void buttonVideoParamsClicked(){
        resetScreens();
        videoParamsPane.setVisible(true);
    }

    public final void buttonSoundParamsClicked(){
        resetScreens();
        soundParamsPane.setVisible(true);
    }

    public final void buttonGameParamsClicked(){
        resetScreens();
        gameParamsPane.setVisible(true);
    }

    public final void buttonHotKeyParamsClicked(){
        resetScreens();
        hotKeyParamsPane.setVisible(true);
    }

    private void resetScreens(){
        for (final Node screen: paramScreens.getChildren()){
            screen.setVisible(false);
        }
    }
}
