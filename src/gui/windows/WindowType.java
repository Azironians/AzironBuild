package gui.windows;

import main.AGame;
import org.jetbrains.annotations.Contract;

import java.net.URL;

public enum WindowType {

    INITIALIZATION(AGame.class.getResource("../fxmlFiles/main/WindowInitialization.fxml"))
    , MENU(AGame.class.getResource("../fxmlFiles/main/WindowMenu.fxml"))
    , AUTHORIZATION(AGame.class.getResource("../fxmlFiles/main/WindowAuthorization.fxml"))
    , PROFILE(AGame.class.getResource("../fxmlFiles/main/profile/WindowProfile.fxml"))
    , CHOICE_BONUS(AGame.class.getResource("../fxmlFiles/main/WindowChoiceBonus.fxml"))
    , FAST_CHOICE_HERO(AGame.class.getResource("../fxmlFiles/main/choiceHero/WindowFastChoiceHero.fxml"))
    , MATCHMAKING(AGame.class.getResource("../fxmlFiles/main/matchmaking/WindowMatchMaking.fxml"))
    , PLAYER(AGame.class.getResource("../fxmlFiles/subsidiary/WindowPlayer.fxml"))
    , RIGHT_TEAM(AGame.class.getResource("../fxmlFiles/main/matchmaking/rightLocation.fxml"))
    , LEFT_TEAM(AGame.class.getResource("../fxmlFiles/main/matchmaking/leftLocation.fxml"));

    private final URL url;

    WindowType(URL url){
        this.url = url;
    }

    @Contract(pure = true)
    public URL URL() {
        return url;
    }
}
