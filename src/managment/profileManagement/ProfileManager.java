package managment.profileManagement;

import annotations.bindingAnnotations.ProfileService;
import bonus.deck.Deck;
import com.google.inject.Inject;
import controllers.main.ControllerChoiceBonus;
import controllers.main.fastChoiceHero.ControllerFastChoiceHero;
import controllers.main.menu.ControllerMenu;
import controllers.main.menu.ProfileRequest;
import gui.windows.WindowType;
import main.AGame;
import managment.playerManagement.Player;
import managment.playerManagement.PlayerManager;
import security.loadSuppliers.bonusSupplier.BonusData;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public final class ProfileManager {

    @Inject
    private AGame aGame;

    @Inject
    private PlayerManager playerManager;

    private Profile currentProfile;

    private Deck bestDeck;

    private List<Deck> privilegedDecks;

    private Map<String, List<Deck>> heroDecks;

    private ProfileRequest profileRequest;

    @Inject
    @ProfileService
    private EnumMap<ProfileRequest, Player> profileEnumMap;

    public void removeAccountData() {
        profileRequest.setAuthorized(false);
        profileEnumMap.remove(profileRequest);
        playerManager.getMapOfPlayers().remove(currentProfile.getName());
        getControllerMenu().setReadyProfile();

        this.currentProfile = null;
        this.bestDeck = null;
        this.privilegedDecks = null;
        this.heroDecks = null;
    }

    private ControllerMenu getControllerMenu(){
        return (ControllerMenu) aGame.getWindowMap().get(WindowType.MENU).getController();
    }

    private ControllerFastChoiceHero getControllerChoiceHero(){
        return (ControllerFastChoiceHero) aGame.getWindowMap().get(WindowType.FAST_CHOICE_HERO).getController();
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

    public List<Deck> getPrivilegedDecks() {
        return privilegedDecks;
    }

    public ProfileRequest getProfileRequest() {
        return profileRequest;
    }

    public void setProfileRequest(ProfileRequest profileRequest) {
        this.profileRequest = profileRequest;
    }

    public void putInProfileEnumMap(final Player player){
        profileEnumMap.put(profileRequest, player);
    }

    public EnumMap<ProfileRequest, Player> getProfileEnumMap() {
        return profileEnumMap;
    }

    public void clearProfileEnumMap(){
        this.profileEnumMap.clear();
    }

    public final void setBonusData(final BonusData bonusData) {
        this.bestDeck = bonusData.getBestDeck();
        this.privilegedDecks = bonusData.getPrivilegedDecks();
        this.heroDecks = bonusData.getHeroDecks();
        if (currentProfile != null && currentProfile.getBonusData() == null){
            currentProfile.setBonusData(bonusData);
        }
    }
}
