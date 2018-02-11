package managment.profileManagement;

import bonus.deck.Deck;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import controllers.main.ControllerChoiceBonus;
import controllers.main.ControllerChoiceHero;
import gui.windows.WindowType;
import main.AGame;

@Singleton
public class ProfileManager {

    @Inject
    private AGame aGame;

    private Profile currentProfile;

    private Deck bestDeck;

    public void removeAccountData() {
        final ControllerChoiceBonus controllerChoiceBonus = getControllerChoiceBonus();
        controllerChoiceBonus.setBonusData(null);
        controllerChoiceBonus.setPrivilegedCollections(null);
        controllerChoiceBonus.setDefaultPrimaryDeck(null);

        final ControllerChoiceHero controllerChoiceHero = getControllerChoiceHero();
        controllerChoiceHero.setPrivilegedCollections(null);
        controllerChoiceHero.setDefaultPrimaryDeck(null);
        currentProfile = null;
        bestDeck = null;
    }


    private ControllerChoiceHero getControllerChoiceHero(){
        return (ControllerChoiceHero) aGame.getWindowMap().get(WindowType.CHOICE_HERO).getController();
    }

    private ControllerChoiceBonus getControllerChoiceBonus(){
        return (ControllerChoiceBonus) aGame.getWindowMap().get(WindowType.CHOICE_BONUS).getController();
    }

    public Profile getCurrentProfile() {
        return currentProfile;
    }

    public void setCurrentProfile(Profile currentProfile) {
        this.currentProfile = currentProfile;
    }

    public Deck getBestDeck() {
        return bestDeck;
    }

    public void setBestDeck(Deck bestDeck) {
        this.bestDeck = bestDeck;
    }
}
