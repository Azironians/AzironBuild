package controllers.subsidiary;

import com.google.inject.Inject;
import controllers.Controller;
import controllers.main.matchmaking.ControllerMatchMaking;
import gui.windows.WindowType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import main.AGame;

import java.net.URL;
import java.util.ResourceBundle;

public final class ControllerInfo implements Initializable, Controller {

    @FXML
    private AnchorPane root;

    @FXML
    private AnchorPane infoScreens;

    @FXML
    private AnchorPane howToPlayPane;

    @FXML
    private AnchorPane aboutDevourerPane;

    @FXML
    private AnchorPane aboutLordVampPane;

    @FXML
    private AnchorPane aboutOrcBashPane;

    @FXML
    private AnchorPane aboutAuthors;

    @FXML
    private Button buttonBack;

    @FXML
    private Button buttonHowToPlay;

    @FXML
    private Button buttonAboutDevourer;

    @FXML
    private Button buttonAboutLordVamp;

    @FXML
    private Button buttonAboutOrcBash;

    @FXML
    private Button buttonAuthors;

    @Inject
    private AGame aGame;

    @Override
    public final void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public final void appearance() {

    }

    //Style & Interface:
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public final void buttonBackClicked() {
        root.setVisible(false);
        final ControllerMatchMaking matchMaking = (ControllerMatchMaking) aGame.getWindowMap()
                .get(WindowType.MATCHMAKING).getController();
        matchMaking.getMenuPane().setVisible(true);
    }

    public final void buttonHowToPlayClicked() {
        resetScreens();
        howToPlayPane.setVisible(true);
    }

    public final void buttonAboutDevourerClicked() {
        resetScreens();
        aboutDevourerPane.setVisible(true);
    }

    public final void buttonAboutLordVampClicked() {
        resetScreens();
        aboutLordVampPane.setVisible(true);
    }

    public final void buttonAboutOrcBashClicked() {
        resetScreens();
        aboutOrcBashPane.setVisible(true);
    }

    public final void buttonAuthorsClicked(){
        resetScreens();
        aboutAuthors.setVisible(true);
    }

    private void resetScreens() {
        for (final Node screen : infoScreens.getChildren()) {
            screen.setVisible(false);
        }
    }
}
