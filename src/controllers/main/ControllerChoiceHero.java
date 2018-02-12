package controllers.main;

import bonus.deck.Deck;
import com.google.inject.Inject;
import controllers.Controller;
import controllers.main.menu.PlayerRequest;
import controllers.main.profile.ControllerProfile;
import heroes.abstractHero.AHero;
import heroes.abstractHero.AHeroBuilder;
import heroes.devourer.DevourerBuilder;
import heroes.lordVampire.LVBuilder;
import heroes.orcBash.OrcBashBuilder;
import managment.playerManagement.Player;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import managment.profileManagement.ProfileManager;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public final class ControllerChoiceHero implements Initializable, Controller {

    private static Logger log = Logger.getLogger(ControllerChoiceHero.class.getName());



    private List<AHero> heroes;


    @FXML
    private Pane heroSpotlights;

    @FXML
    private ImageView spotLightDev;

    @FXML
    private ImageView spotLightLV;

    @FXML
    private ImageView spotLightBHR;

    @FXML
    private ImageView btnOffLeft;

    @FXML
    private ImageView btnOnLeft;

    @FXML
    private ImageView btnOnRight;

    @FXML
    private ImageView btnOffRight;

    @FXML
    private Button btnChoiceHero;

    @FXML
    private Button btnBackToProfile;

    @FXML
    private Pane paneMessage;

    @FXML
    private ImageView btnOffBack;

    @FXML
    private ImageView btnOnBack;

    @FXML
    private ImageView btnOffChoiceHero;

    @FXML
    private ImageView btnOnChoiceHero;

    @Inject
    private ProfileManager profileManager;



    @Override
    public final void initialize(URL location, ResourceBundle resources) {
        appearance();
    }

    @Override
    public final void appearance() {
        installHeroes();

    }

    private void installHeroes(){
        final List<AHero> heroes = new ArrayList<>();
        final List<AHeroBuilder> builders = Arrays.asList(new DevourerBuilder(), new LVBuilder(), new OrcBashBuilder());
        for (final AHeroBuilder heroBuilder: builders){
            final AHero hero = heroBuilder.buildHero(null);
            heroes.add(hero);
        }
        this.heroes = heroes;
    }
}
