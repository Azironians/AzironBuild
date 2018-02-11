package controllers.main.menu;

import com.google.inject.Inject;
import controllers.Controller;
import controllers.main.ControllerChoiceHero;
import controllers.main.matchmaking.ControllerMatchMaking;
import gui.locations.engine.GraphicEngine;
import gui.sceneMover.SceneMover;
import gui.windows.WindowType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import main.AGame;
import managment.actionManagement.service.bonusEngine.BonusEventEngine;
import managment.playerManagement.PlayerManager;
import managment.playerManagement.FictionalTeams;

import java.net.URL;
import java.util.ResourceBundle;

public final class ControllerPlayer implements Initializable, Controller {

    @FXML
    private AnchorPane root;

    @FXML
    private Text gameMode;

    @FXML
    private Button buttonStart;

    @FXML
    private Button buttonBack;

    @FXML
    private AnchorPane primaryPlayerPane;

    @FXML
    private AnchorPane secondaryPlayerPane;

    @FXML
    private Button primaryLeftPlayer;

    @FXML
    private Button primaryRightPlayer;

    @FXML
    private Button secondaryLeftPlayer;

    @FXML
    private Button secondaryRightPlayer;

    @Inject
    private AGame aGame;

    @Inject
    private BonusEventEngine bonusEventEngine;

    @Inject
    private PlayerManager playerManager;

    @Inject
    private GraphicEngine graphicEngine;

    @Inject
    private SceneMover sceneMover;

    @Override
    public final void initialize(final URL location, final ResourceBundle resources) {
        buttonStart.setOnKeyPressed(event -> {
            System.out.println("OK");
            buttonStartClicked();
        });
    }

    @Override
    public final void appearance() {

    }

    public final void addPrimaryLeftPlayer(){
        addPlayer(PlayerRequest.PRIMARY_LEFT);
    }

    public final void addSecondaryLeftPlayer(){
        addPlayer(PlayerRequest.SECONDARY_LEFT);
    }

    public final void addPrimaryRightPlayer(){
        addPlayer(PlayerRequest.PRIMARY_RIGHT);
    }

    public final void addSecondaryRightPlayer(){
        addPlayer(PlayerRequest.SECONDARY_RIGHT);
    }

    private void addPlayer(final PlayerRequest playerRequest){
        final ControllerChoiceHero controllerChoiceHero = getControllerChoiceHero();
        controllerChoiceHero.setPlayerRequest(playerRequest);
        sceneMover.moveToScene(WindowType.AUTHORIZATION);
    }

    public final void buttonStartClicked() {
        installPlayers();
        installGraphic();
        installEngines();
        installMatchMaking();
        start();
    }

    public final void buttonBackClicked() {
        root.setVisible(false);
        root.setDisable(true);
        final ControllerMenu controllerMenu = getControllerMenu();
        controllerMenu.getPanelLocMch().setVisible(true);
        controllerMenu.getPanelLocMch().setDisable(false);
        controllerMenu.getPaneButtons().setVisible(false);
        controllerMenu.getPaneButtons().setDisable(true);
        controllerMenu.getPaneMessage().setVisible(false);
        controllerMenu.getPaneMessage().setDisable(true);
    }

    private void installPlayers() {
        //setup teams:
        playerManager.setLeftATeam(FictionalTeams.createLeft());
        playerManager.setRightATeam(FictionalTeams.createRight());
        //setup randomly start team:
        playerManager.setStartPosition();
    }

    private void installGraphic(){
        graphicEngine.install();
    }

    private void installEngines(){
        bonusEventEngine.install();
    }

    private void start(){
        playerManager.start();
    }

    private void installMatchMaking(){
        final ControllerMatchMaking matchMaking = getControllerMatchMaking();
        matchMaking.appearance();
        sceneMover.moveToScene(WindowType.MATCHMAKING);
    }

    //Getters & setters:
    private ControllerMatchMaking getControllerMatchMaking(){
        return (ControllerMatchMaking) aGame.getWindowMap().get(WindowType.MATCHMAKING).getController();
    }

    private ControllerMenu getControllerMenu(){
        return (ControllerMenu) aGame.getWindowMap().get(WindowType.MENU).getController();
    }

    private ControllerChoiceHero getControllerChoiceHero(){
        return (ControllerChoiceHero) aGame.getWindowMap().get(WindowType.CHOICE_HERO).getController();
    }
    //Style & interface:
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
