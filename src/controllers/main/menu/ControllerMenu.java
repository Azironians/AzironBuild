package controllers.main.menu;

import controllers.Controller;
import controllers.main.matchmaking.ControllerMatchMaking;
import gui.clock.AClock;
import gui.windows.WindowType;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
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
import managment.playerManagement.PlayerManager;
import org.jetbrains.annotations.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.URL;
import java.util.ResourceBundle;

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
    @FXML
    private ImageView buttonOffGameTwo;
    @FXML
    private ImageView buttonOnGameTwo;
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

    private static final int PRIMARY_PLAYER_INDEX = 3;

    private static final int SECONDARY_PLAYER_INDEX = 4;

    @Override
    public final void initialize(final URL location, final ResourceBundle resources) {
        AClock.launchTimer(time).start();
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

    @Contract(" -> fail")
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

    //Кнопка "1x1":
    public final void buttonOffGame1x1Entered() {
        buttonOffGameTwo.setVisible(false);
        buttonOnGameTwo.setVisible(true);
    }

    public final void buttonOnGame1x1Exited() {
        buttonOffGameTwo.setVisible(true);
        buttonOnGameTwo.setVisible(false);
    }

    public final void buttonOnGame1x1Clicked() {
        setPlayerCount(2);
        setCommandGame(false);
        showLocMatchPanel();

        playerPane.getChildren().get(PRIMARY_PLAYER_INDEX).setVisible(true);
        playerPane.getChildren().get(SECONDARY_PLAYER_INDEX).setVisible(false);
        Text gameMode = (Text) playerPane.getChildren().get(5);
        gameMode.setText("1x1");
    }

    //Кнопка "2x2":
    public final void buttonOffGame2x2Entered() {
    }

    public final void buttonOnGame2x2Exited() {

    }

    public final void buttonOnGame2x2Clicked() {
        setPlayerCount(4);
        setCommandGame(true);
        showLocMatchPanel();

        playerPane.getChildren().get(PRIMARY_PLAYER_INDEX).setVisible(true);
        playerPane.getChildren().get(SECONDARY_PLAYER_INDEX).setVisible(true);
        Text gameMode = (Text) playerPane.getChildren().get(5);
        gameMode.setText("2x2");
    }

    private void showLocMatchPanel() {
        playerPane.setVisible(true);
        playerPane.setDisable(false);
        panelLocMch.setVisible(false);
        panelLocMch.setDisable(true);
        paneButtons.setDisable(true);
        paneButtons.setVisible(false);
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
            fadeTransition.setOnFinished(event -> {
                logger.info("Ready");
            });
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

    private void setPlayerCount(final int count) {
        playerManager.setPlayerCount(count);
        logger.info("Setup players: " + playerManager.getCountPlayers());
    }

    private void setCommandGame(boolean setter) {
        final ControllerMatchMaking controllerMatchMaking = (ControllerMatchMaking) aGame.getWindowMap()
                .get(WindowType.MATCHMAKING).getController();
        controllerMatchMaking.getLeftLocation().getHeroes().setVisible(setter);
        controllerMatchMaking.getRightLocation().getHeroes().setVisible(setter);
    }

    //Getters:
    @Contract(pure = true)
    final Pane getPanelLocMch() {
        return panelLocMch;
    }

    @Contract(pure = true)
    final Pane getPaneButtons() {
        return paneButtons;
    }

    @Contract(pure = true)
    final Pane getPaneMessage() {
        return paneMessage;
    }
}
