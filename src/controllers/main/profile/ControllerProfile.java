package controllers.main.profile;

import controllers.Controller;
import controllers.main.ControllerChoiceBonus;
import controllers.main.fastChoiceHero.ControllerFastChoiceHero;
import gui.clock.AClock;
import gui.sceneMover.SceneMover;
import gui.windows.WindowType;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import main.AGame;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import managment.profileManagement.ProfileManager;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public final class ControllerProfile implements Initializable, Controller {

    private static Logger log = Logger.getLogger(ControllerProfile.class.getName());

    @FXML
    private ImageView buttonOffLogOut;

    @FXML
    private ImageView buttonOnLogOut;

    @FXML
    private ImageView buttonOffShowStatistics;

    @FXML
    private ImageView buttonOnShowStatistics;

    @FXML
    private ImageView buttonOffChoiceHeroes;

    @FXML
    private ImageView buttonOnChoiceHeroes;

    @FXML
    private Pane paneHeroes;

    @FXML
    private ImageView devourer;

    @FXML
    private ImageView lordVamp;

    @FXML
    private ImageView orcBash;

    @FXML
    private Text textFavouriteHero;

    @FXML
    private Text textProfileName;

    @FXML
    private Pane paneStatistics;

    @FXML
    private Text time;

    @Inject
    private AGame aGame;

    @Inject
    private ProfileManager profileManager;

    @Inject
    private SceneMover sceneMover;

    @Override
    public final void initialize(final URL location, final ResourceBundle resources) {
        AClock.launchTimer(time);
    }

    @Override
    public final void appearance() {
        final String name = profileManager.getBestDeck().getHero();
        switch (name) {
            case "Devourer":
                devourer.setVisible(true);
                break;
            case "LordVampire":
                lordVamp.setVisible(true);
                break;
            case "OrcBash":
                orcBash.setVisible(true);
        }
        final String headline = "Любимый герой: " + name;
        textFavouriteHero.setText(headline);
    }

    private void resetHeroImages(){
        final ObservableList<Node> images = paneHeroes.getChildren();
        images.forEach(image -> image.setVisible(false));
    }

    //Style & gameInterface:
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public final void buttonOnChoiceHeroesClicked() {
        final ControllerFastChoiceHero controllerFastChoiceHero = getControllerChoiceHero();
        controllerFastChoiceHero.installHeroes();
        sceneMover.moveToScene(WindowType.FAST_CHOICE_HERO);
    }

    public void buttonOffChoiceHeroesEntered() {
        buttonOffChoiceHeroes.setVisible(false);
        buttonOnChoiceHeroes.setVisible(true);
    }

    public void buttonOnChoiceHeroesExited() {
        buttonOnChoiceHeroes.setVisible(false);
        buttonOffChoiceHeroes.setVisible(true);
    }

    public void buttonOffShowStatisticsEntered() {
        buttonOffShowStatistics.setVisible(false);
        buttonOnShowStatistics.setVisible(true);
    }

    public void buttonOnShowStatisticsExited() {
        buttonOffShowStatistics.setVisible(true);
        buttonOnShowStatistics.setVisible(false);
    }

    public void buttonOnShowStatisticsClicked() {
        paneStatistics.setVisible(true);
    }

    public void buttonOffLogOutEntered() {
        buttonOffLogOut.setVisible(false);
        buttonOnLogOut.setVisible(true);
    }

    public void buttonOnLogOutExited() {
        buttonOffLogOut.setVisible(true);
        buttonOnLogOut.setVisible(false);
    }

    public void buttonOnLogOutClicked() {
        profileManager.removeAccountData();
        resetHeroImages();
        sceneMover.moveToScene(WindowType.AUTHORIZATION);
    }

    //Getters & setters:
    public final Pane getPaneStatistics() {
        return paneStatistics;
    }

    private ControllerFastChoiceHero getControllerChoiceHero(){
        return (ControllerFastChoiceHero) aGame.getWindowMap().get(WindowType.FAST_CHOICE_HERO).getController();
    }

    private ControllerChoiceBonus getControllerChoiceBonus(){
        return (ControllerChoiceBonus) aGame.getWindowMap().get(WindowType.CHOICE_BONUS).getController();
    }

    public Text getTextProfileName() {
        return textProfileName;
    }

    public void setTextProfileName(final String textProfileName) {
        this.textProfileName.setText(textProfileName);
    }
}