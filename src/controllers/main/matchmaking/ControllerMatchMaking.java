package controllers.main.matchmaking;

import com.google.inject.Inject;
import controllers.Controller;
import gui.service.locations.ALocation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import managment.actionManagement.ActionManager;
import javafx.fxml.Initializable;
import managment.playerManagement.PlayerManager;
import org.jetbrains.annotations.Contract;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public final class ControllerMatchMaking implements Initializable, Controller {

    private final Logger logger = Logger.getLogger(ControllerMatchMaking.class.getName());


    @Inject
    private ActionManager actionManager;

    @Inject
    private PlayerManager playerManager;

    @FXML
    private AnchorPane leftLocationPane;

    @FXML
    private AnchorPane rightLocationPane;

    @FXML
    private AnchorPane bonusLocationPane;

    //Global:
    @FXML
    private Button menuButton;

    //Pause menu:
    @FXML
    private AnchorPane pausePane;

    @FXML
    private AnchorPane menuPane;

    @FXML
    private Pane paramPane;

    @FXML
    private Pane infoPane;

    @FXML
    private Button buttonResumeGame;

    @FXML
    private Button buttonParams;

    @FXML
    private Button buttonEndMatch;

    @FXML
    private Button buttonExit;

    @FXML
    private Button buttonInfo;

    private ALocation leftLocation;

    private ALocation rightLocation;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("Start!");
        leftLocation = new ALocation(leftLocationPane, false);
        rightLocation = new ALocation(rightLocationPane, true);
    }

    @Override
    public void appearance() {
        System.out.println("Launch");
    }

    //Style & Interface:
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public final void buttonMenuClicked() {
        pausePane.setVisible(true);
        logger.info(playerManager.getCurrentTeam().toString());
        logger.info(playerManager.getOpponentTeam().toString());
    }

    public final void buttonResumeGameClicked() {
        pausePane.setVisible(false);
    }

    public final void buttonParamsClicked() {
        menuPane.setVisible(false);
        paramPane.setVisible(true);
    }

    public final void buttonInfoClicked() {
        menuPane.setVisible(false);
        infoPane.setVisible(true);
    }

    @Contract(" -> fail")
    public final void buttonExitClicked() {
        System.exit(1);
    }

    //Getters:
    public AnchorPane getMenuPane() {
        return menuPane;
    }

    public ALocation getLeftLocation() {
        return leftLocation;
    }

    public ALocation getRightLocation() {
        return rightLocation;
    }

    public final AnchorPane getBonusLocationPane() {
        return bonusLocationPane;
    }

    public final ActionManager getActionManager() {
        return actionManager;
    }
}
