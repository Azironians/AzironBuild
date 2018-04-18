package controllers.main.menu;

import com.google.inject.Inject;
import controllers.Controller;
import controllers.main.fastChoiceHero.ControllerFastChoiceHero;
import controllers.main.matchmaking.ControllerMatchMaking;
import gui.service.graphicEngine.GraphicEngine;
import gui.sceneMover.SceneMover;
import gui.windows.WindowType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import main.AGame;
import managment.actionManagement.ActionManager;
import managment.actionManagement.service.engine.EventEngine;
import managment.playerManagement.PlayerManager;
import managment.profileManagement.Profile;
import managment.profileManagement.ProfileManager;
import security.gate.AGate;

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
    public  Button primaryLeftPlayer;

    @FXML
    public  Button primaryRightPlayer;

    @FXML
    public  Button secondaryLeftPlayer;

    @FXML
    public  Button secondaryRightPlayer;

    @Inject
    private AGame aGame;

    //EventEngines:
    @Inject
    private EventEngine eventEngine;

    //Managers:
    @Inject
    private ActionManager actionManager;

    @Inject
    private PlayerManager playerManager;

    @Inject
    private ProfileManager profileManager;

    //GraphicEngines:
    @Inject
    private GraphicEngine graphicEngine;

    //Gate:
    @Inject
    private AGate aGate;

    //SceneMover:
    @Inject
    private SceneMover sceneMover;

    @Override
    public final void initialize(final URL location, final ResourceBundle resources) {
        buttonStart.setOnKeyPressed(event -> {
            System.out.println("OK");
            buttonStartClicked();
        });
        buttonStart.setVisible(false);
    }

    @Override
    public final void appearance() {

    }

    public final void addPrimaryLeftPlayer(){
        addPlayer(ProfileRequest.PRIMARY_LEFT);
    }

    public final void addSecondaryLeftPlayer(){
        addPlayer(ProfileRequest.SECONDARY_LEFT);
    }

    public final void addPrimaryRightPlayer(){
        addPlayer(ProfileRequest.PRIMARY_RIGHT);
    }

    public final void addSecondaryRightPlayer(){
        addPlayer(ProfileRequest.SECONDARY_RIGHT);
    }

    private void addPlayer(final ProfileRequest profileRequest){
        if (!profileRequest.isAuthorized()){
            profileManager.setProfileRequest(profileRequest);
            sceneMover.moveToScene(WindowType.AUTHORIZATION);
        } else {
            final Profile profile = profileManager.getProfileEnumMap().get(profileRequest).getProfile();
            aGate.doAuthorization(profile, profile.getBonusData());
            sceneMover.moveToScene(WindowType.PROFILE);
        }
    }

    public final void buttonStartClicked() {
        installPlayers();
        installGraphic();
        installEngines();
        installActionManager();
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
        //setup randomly start team:
        playerManager.setStartPosition();
    }

    private void installActionManager(){
        actionManager.install();
    }

    private void installGraphic(){
        graphicEngine.install();
    }

    private void installEngines(){
        eventEngine.install();
    }

    private void start(){
        playerManager.start();
    }

    private void installMatchMaking(){
        sceneMover.moveToScene(WindowType.MATCHMAKING);
    }

    //Getters & setters:
    private ControllerMatchMaking getControllerMatchMaking(){
        return (ControllerMatchMaking) aGame.getWindowMap().get(WindowType.MATCHMAKING).getController();
    }

    private ControllerMenu getControllerMenu(){
        return (ControllerMenu) aGame.getWindowMap().get(WindowType.MENU).getController();
    }

    private ControllerFastChoiceHero getControllerChoiceHero(){
        return (ControllerFastChoiceHero) aGame.getWindowMap().get(WindowType.FAST_CHOICE_HERO).getController();
    }
    //Style & interface:
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}