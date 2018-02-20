package controllers.main.menu;

import controllers.Controller;
import gui.clock.AClock;
import gui.windows.WindowType;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import main.AGame;
import com.google.inject.Inject;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import managment.playerManagement.GameMode;
import managment.playerManagement.PlayerManager;
import managment.profileManagement.ProfileManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.URL;
import java.util.ResourceBundle;

import static controllers.main.menu.ProfileRequest.*;

//Finished:
// FIXME: 28.12.2017 falling controller
public final class ControllerMenu implements Initializable, Controller {

    private final Logger logger = LoggerFactory.getLogger(ControllerMenu.class);

    @FXML
    private Pane playerPane;

    @FXML
    private Pane paneInit;

    @FXML
    private ImageView buttonOnLocMch;

    @FXML
    private ImageView buttonOffLocMch;

    @FXML
    private ImageView buttonOffExitProgram;

    @FXML
    private ImageView buttonOnExitProgram;

//    @FXML
//    private ImageView buttonOffGameTwo;
//
//    @FXML
//    private ImageView buttonOnGameTwo;

    @FXML
    private ImageView buttonOffBack;

    @FXML
    private ImageView buttonOnBack;

    @FXML
    private Pane panelLocMch;

    @FXML
    private Pane paneButtons;

    @FXML
    private Pane paneMessage;

    @FXML
    private Text time;

    @FXML
    private ProgressBar progressBar;

    @Inject
    private AGame aGame;

    @Inject
    private PlayerManager playerManager;

    @Inject
    private ProfileManager profileManager;

    private static final int PRIMARY_PLAYER_INDEX = 3;

    private static final int SECONDARY_PLAYER_INDEX = 4;

    @Override
    public final void initialize(final URL location, final ResourceBundle resources) {
        AClock.launchTimer(time).start();
    }

    @Override
    public final void appearance() {
        loadMenu();
        installKeyController();
    }

    private void loadMenu(){
        final Timeline progress = new Timeline(new KeyFrame(Duration.millis(100)
                , event -> progressBar.setProgress(progressBar.getProgress() + 0.012)));
        progress.setCycleCount(100);
        progress.play();
        final Timeline delay = new Timeline(new KeyFrame(Duration.seconds(7)));
        delay.setOnFinished(labelEnd -> {
            paneInit.setVisible(false);
            final FadeTransition fadeTransition = new FadeTransition(Duration.seconds(4), paneMessage);
            fadeTransition.setToValue(1);
            fadeTransition.setOnFinished(event -> logger.info("Ready"));
            fadeTransition.play();
        });
        delay.play();
    }

    private void installKeyController(){
        final Scene scene = aGame.getWindowMap().get(WindowType.MENU).getScene();
        scene.addEventFilter(KeyEvent.KEY_RELEASED , event -> {
            final boolean onMainMenu = !panelLocMch.isVisible() && !playerPane.isVisible();
            final boolean onLocMatch = panelLocMch.isVisible();
            final boolean onPlayerPane = playerPane.isVisible();
            final KeyCode keyCode = event.getCode();
            //Main menu:
            if (keyCode == KeyCode.ESCAPE && onMainMenu){
                buttonOnExitProgramClicked();
            }
            if (keyCode == KeyCode.L && onMainMenu){
                buttonOnLocMchClicked();
            }
            //LocalMatch:
            if (keyCode == KeyCode.DIGIT1 && onLocMatch){
                buttonOnGame1x1Clicked();
            }
            if (keyCode == KeyCode.DIGIT2 && onLocMatch){
                buttonOnGame2x2Clicked();
            }
            if (keyCode == KeyCode.BACK_SPACE && onLocMatch){
                buttonOnBackClicked();
            }
            //PlayerPane:
            if (keyCode == KeyCode.BACK_SPACE && onPlayerPane){
                playerPane.setVisible(false);
                panelLocMch.setVisible(true);
            }
        });
    }

    private void setGameMode(final GameMode gameMode){
        playerManager.setGameMode(gameMode);
        final Text gameModeText = (Text) playerPane.getChildren().get(5);
        gameModeText.setText(gameMode.toString());
    }

    public final void setReadyProfile(){
        final Pane primaryProfiles = (Pane) playerPane.getChildren().get(PRIMARY_PLAYER_INDEX);
        final Pane secondaryProfiles = (Pane) playerPane.getChildren().get(SECONDARY_PLAYER_INDEX);
        Button profileButton = new Button();

        switch (profileManager.getProfileRequest()){
            case PRIMARY_LEFT:
                profileButton = (Button) primaryProfiles.getChildren().get(0);
                break;
            case PRIMARY_RIGHT:
                profileButton = (Button) primaryProfiles.getChildren().get(1);
                break;
            case SECONDARY_LEFT:
                profileButton = (Button) secondaryProfiles.getChildren().get(0);
                break;
            case SECONDARY_RIGHT:
                profileButton = (Button) secondaryProfiles.getChildren().get(1);
        }
        if (profileManager.getProfileRequest().isAuthorized()){
            setReadyProfileGraphic(profileButton, profileManager.getCurrentProfile().getName());
            if (PRIMARY_LEFT.isAuthorized() && PRIMARY_RIGHT.isAuthorized()
                    && SECONDARY_LEFT.isAuthorized() && SECONDARY_RIGHT.isAuthorized() && playerManager.getGameMode() == GameMode._2x2){
                final int BUTTON_START_INDEX = 1;
                playerPane.getChildren().get(BUTTON_START_INDEX).setVisible(true);
            }
            if (PRIMARY_LEFT.isAuthorized() && PRIMARY_RIGHT.isAuthorized() && playerManager.getGameMode() == GameMode._1x1){
                final int BUTTON_START_INDEX = 1;
                playerPane.getChildren().get(BUTTON_START_INDEX).setVisible(true);
            }
        } else {
            setReadyProfileGraphic(profileButton, "Добавить игрока");
        }
    }

    private void setReadyProfileGraphic(final Button button, final String profileName){
        button.setText(profileName);
    }


    //Style & interface:
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Кнопка "Локальный матч":
    public final void buttonOffLocMchEntered() {
        buttonOffLocMch.setVisible(false);
        buttonOnLocMch.setVisible(true);
    }

    public final void buttonOnLocMchExited() {
        buttonOffLocMch.setVisible(true);
        buttonOnLocMch.setVisible(false);
    }

    public final void buttonOnLocMchClicked() {
        paneMessage.setVisible(false);
        paneButtons.setVisible(false);
        paneButtons.setDisable(true);
        panelLocMch.setVisible(true);
        panelLocMch.setDisable(false);
    }

    //Кнопка "Выход из игры":
    public final void buttonOffExitProgramEntered() {
        buttonOffExitProgram.setVisible(false);
        buttonOnExitProgram.setVisible(true);
    }

    public final void buttonOnExitProgramExited() {
        buttonOffExitProgram.setVisible(true);
        buttonOnExitProgram.setVisible(false);
    }

    public final void buttonOnExitProgramClicked() {
        System.exit(0);
    }

    //Кнопка "Назад":
    public final void buttonOffBackEntered() {
        buttonOffBack.setVisible(false);
        buttonOnBack.setVisible(true);
    }

    public final void buttonOnBackExited() {
        buttonOffBack.setVisible(true);
        buttonOnBack.setVisible(false);
    }

    public final void buttonOnBackClicked() {
        paneMessage.setVisible(true);
        paneButtons.setVisible(true);
        paneButtons.setDisable(false);
        panelLocMch.setVisible(false);
        panelLocMch.setDisable(true);
    }

//    //Кнопка "1x1":
//    public final void buttonOffGame1x1Entered() {
//        buttonOffGameTwo.setVisible(false);
//        buttonOnGameTwo.setVisible(true);
//    }
//
//    public final void buttonOnGame1x1Exited() {
//        buttonOffGameTwo.setVisible(true);
//        buttonOnGameTwo.setVisible(false);
//    }

    public final void buttonOnGame1x1Clicked() {
        setGameMode(GameMode._1x1);
        showLocMatchPanel();
        playerPane.getChildren().get(PRIMARY_PLAYER_INDEX).setVisible(true);
        playerPane.getChildren().get(SECONDARY_PLAYER_INDEX).setVisible(false);
    }

    //Кнопка "2x2":
    public final void buttonOffGame2x2Entered() {
    }

    public final void buttonOnGame2x2Exited() {

    }

    public final void buttonOnGame2x2Clicked() {
        setGameMode(GameMode._2x2);
        showLocMatchPanel();
        playerPane.getChildren().get(PRIMARY_PLAYER_INDEX).setVisible(true);
        playerPane.getChildren().get(SECONDARY_PLAYER_INDEX).setVisible(true);
    }

    private void showLocMatchPanel() {
        playerPane.setVisible(true);
        playerPane.setDisable(false);
        panelLocMch.setVisible(false);
        panelLocMch.setDisable(true);
        paneButtons.setDisable(true);
        paneButtons.setVisible(false);
    }

    //Getters:
    final Pane getPanelLocMch() {
        return panelLocMch;
    }

    final Pane getPaneButtons() {
        return paneButtons;
    }

    final Pane getPaneMessage() {
        return paneMessage;
    }
}