package controllers.main;

import bonus.deck.Deck;
import com.google.inject.Inject;
import controllers.Controller;
import controllers.main.menu.ControllerMenu;
import controllers.main.menu.ControllerPlayer;
import controllers.main.menu.ProfileRequest;
import gui.sceneMover.SceneMover;
import gui.windows.WindowType;
import heroes.abstractHero.AHero;
import heroes.abstractHero.AHeroBuilder;
import heroes.devourer.DevourerBuilder;
import heroes.lordVampire.LVBuilder;
import heroes.orcBash.OrcBashBuilder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import main.AGame;
import managment.playerManagement.Player;
import managment.playerManagement.PlayerManager;
import managment.profileManagement.Profile;
import managment.profileManagement.ProfileManager;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public final class ControllerChoiceHero implements Initializable, Controller {

    private static Logger log = Logger.getLogger(ControllerChoiceHero.class.getName());

    @FXML
    private Text nameOfBonusCollection;

    @FXML
    private Pane heroSpotlights;

    @FXML
    private ImageView btnOffLeft;

    @FXML
    private ImageView btnOnLeft;

    @FXML
    private ImageView btnOnRight;

    @FXML
    private ImageView btnOffRight;

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
    private AGame aGame;

    @Inject
    private PlayerManager playerManager;

    @Inject
    private ProfileManager profileManager;

    @Inject
    private SceneMover sceneMover;

    private int pointer;

    private List<AHero> heroes;

    private List<AHero.Presentation> presentations;

    @Override
    public final void initialize(URL location, ResourceBundle resources) {
        appearance();
    }

    @Override
    public final void appearance() {

    }

    public final void installHeroes(){
        final List<AHero> heroes = new ArrayList<>();
        final List<AHero.Presentation> presentations = new ArrayList<>();
        final List<AHeroBuilder> builders = Arrays.asList(new DevourerBuilder(), new LVBuilder(), new OrcBashBuilder());
        final Deck bestDeck = profileManager.getBestDeck();
        clearHeroSpotLights();
        for (int i = 0; i < builders.size(); i++){
            final AHero hero = builders.get(i).buildHero(null);
            final AHero.Presentation presentation = hero.getPresentation();
            final Deck privilegedDeck = profileManager.getPrivilegedDecks().get(i);
            presentation.setDeckInfo(privilegedDeck.getCollectionName(), privilegedDeck.getPriority());
            heroes.add(hero);
            presentations.add(presentation);
            setGraphicSpotlight(presentation.getBackGround());
            if (hero.getName().equals(bestDeck.getHero())){
                presentation.getBackGround().setVisible(true);
                setDeckHeadline(bestDeck.getCollectionName());
                this.pointer = i;
            } else {
                presentation.getBackGround().setVisible(false);
            }
        }
        this.heroes = heroes;
        this.presentations = presentations;
    }

    private void clearHeroSpotLights(){
        this.heroSpotlights.getChildren().clear();
    }

    private void setGraphicSpotlight(final ImageView imageView){
        this.heroSpotlights.getChildren().add(imageView);
    }

    private void setDeckHeadline(final String deckName){
        nameOfBonusCollection.setText(deckName);
    }

    //Style & interface
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public final void buttonOffBackEntered(){
        btnOffBack.setVisible(false);
        btnOnBack.setVisible(true);
    }

    public final void buttonOnBackExited(){
        btnOffBack.setVisible(true);
        btnOnBack.setVisible(false);
    }

    public final void buttonOnBackClicked(){
        sceneMover.moveToScene(WindowType.PROFILE);
    }

    public final void buttonOffLeftEntered(){
        btnOffLeft.setVisible(false);
        btnOnLeft.setVisible(true);
    }

    public final void buttonOnLeftExited(){
        btnOnLeft.setVisible(false);
        btnOffLeft.setVisible(true);
    }

    public final void buttonOnLeftClicked(){
        previousSpotlight();
    }

    public final void buttonOffRightEntered(){
        btnOffRight.setVisible(false);
        btnOnRight.setVisible(true);
    }

    public final void buttonOnRightExited(){
        btnOnRight.setVisible(false);
        btnOffRight.setVisible(true);
    }

    public final void buttonOnRightClicked(){
        nextSpotlight();
    }

    private void nextSpotlight(){
        heroSpotlights.getChildren().get(pointer).setVisible(false);
        if (pointer + 1 == presentations.size()){
            pointer = 0;
        } else {
            pointer++;
        }
        heroSpotlights.getChildren().get(pointer).setVisible(true);
        setDeckHeadline(presentations.get(pointer).getDeckName());
    }

    private void previousSpotlight(){
        heroSpotlights.getChildren().get(pointer).setVisible(false);
        if (pointer - 1 == -1){
            pointer = presentations.size() - 1;
        } else {
            pointer--;
        }
        heroSpotlights.getChildren().get(pointer).setVisible(true);
        setDeckHeadline(presentations.get(pointer).getDeckName());
    }

    public final void buttonOffChoiceHeroEntered(){
        btnOffChoiceHero.setVisible(false);
        btnOnChoiceHero.setVisible(true);
    }

    public final void buttonOnChoiceHeroExited(){
        btnOnChoiceHero.setVisible(false);
        btnOffChoiceHero.setVisible(true);
    }

    public final void buttonOnChoiceHeroClicked(){
        final AHero selectedHero = heroes.get(pointer);
        selectedHero.putBonusCollection(profileManager.getPrivilegedDecks().get(pointer).getCollection());
        final Profile profile = profileManager.getCurrentProfile();
        final Player player = convertToPlayer(profile, selectedHero);
        final ProfileRequest profileRequest = profileManager.getProfileRequest();
        switch (profileRequest){
            case PRIMARY_LEFT:
                playerManager.getLeftATeam().setCurrentPlayer(player);
                break;
            case PRIMARY_RIGHT:
                playerManager.getRightATeam().setCurrentPlayer(player);
                break;
            case SECONDARY_LEFT:
                playerManager.getLeftATeam().setAlternativePlayer(player);
                break;
            case SECONDARY_RIGHT:
                playerManager.getRightATeam().setAlternativePlayer(player);
        }
        profileManager.putInProfileEnumMap(player);
        playerManager.getMapOfPlayers().put(profile.getName(), player);
        profileRequest.setAuthorized(true);

        final ControllerMenu controllerMenu = getControllerMenu();
        controllerMenu.setReadyProfile();
        sceneMover.moveToScene(WindowType.MENU);
    }

    private Player convertToPlayer(final Profile profile, final AHero hero){
        return new Player(profile,  hero);
    }



    private ControllerMenu getControllerMenu(){
        return (ControllerMenu) aGame.getWindowMap().get(WindowType.MENU).getController();
    }
}
