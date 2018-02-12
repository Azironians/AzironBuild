package security.loadSuppliers.profileSupplier;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import controllers.main.profile.ControllerProfile;
import gui.endavour.Endeavour;
import gui.windows.WindowType;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import main.AGame;
import managment.playerManagement.PlayerManager;
import managment.profileManagement.Profile;
import managment.profileManagement.ProfileManager;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import security.assistants.ProfileAssistant;
import security.loadSuppliers.LoadSupplier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Singleton
public final class ProfileLoadSupplier implements LoadSupplier<Profile> {

    private static final Logger log = LoggerFactory.getLogger(ProfileLoadSupplier.class);

    //Authorization messages:
    @Inject
    @Named("NOT_CORRECT_LOGIN")
    private static String NOT_CORRECT_LOGIN;

    @Inject
    @Named("CORRECT_PROFILE")
    private static String CORRECT_PROFILE;

    @Inject
    @Named("PLAYER_WAS_AUTHORIZED")
    private static String PLAYER_WAS_AUTHORIZED;

    //ProfileProperties
    @Inject
    @Named("LOGIN_INDEX")
    private static int LOGIN_INDEX;

    @Inject
    @Named("PASS_INDEX")
    private static int PASS_INDEX;

    @Inject
    @Named("RANK_INDEX")
    private static int RANK_INDEX; //Example (3/327)

    @Inject
    @Named("PROFILE_LINES")
    private static int PROFILE_LINES;

    @Inject
    @Named("MIN_LEVEL")
    private static int MIN_LEVEL;

    @Inject
    @Named("MAX_LEVEL")
    private static int MAX_LEVEL;

    @Inject
    @Named("MIN_RATING")
    private static int MIN_RATING;

    @Inject
    @Named("MAX_RATING")
    private static int MAX_RATING;

    @Inject
    private AGame aGame;

    @Inject
    private PlayerManager playerManager;

    @Inject
    private ProfileManager profileManager;

    private List<String> profileData;

    private Profile profile;

    @NotNull
    public final Endeavour readData(final String login, final String password) {
        final Map mapOfPlayers = playerManager.getMapOfPlayers();
        if (mapOfPlayers.containsKey(login)) {
            return new Endeavour(PLAYER_WAS_AUTHORIZED, false);
        }
        try {
            profileData = loadProfileData(login);
            if (profileData.size() != PROFILE_LINES) {
                return new Endeavour(NOT_CORRECT_LOGIN, false);
            }
            if (!login.equals(profileData.get(LOGIN_INDEX))) {
                return new Endeavour(NOT_CORRECT_LOGIN, false);
            }
            if (!password.equals(profileData.get(PASS_INDEX))) {
                return new Endeavour(NOT_CORRECT_LOGIN, false);
            }
            final String[] parts = profileData.get(RANK_INDEX).split("/");
            if (Byte.parseByte(parts[0]) > MAX_LEVEL || Byte.parseByte(parts[0]) < MIN_LEVEL
                    || Integer.parseInt(parts[1]) < MIN_RATING || Integer.parseInt(parts[1]) > MAX_RATING) {
                return new Endeavour(NOT_CORRECT_LOGIN, false);
            }
            //Check wins for heroes & control sum:
            if (isNotCorrectStatistic()) {
                return new Endeavour(NOT_CORRECT_LOGIN, false);
            }
        } catch (final Exception e) {
            return new Endeavour(NOT_CORRECT_LOGIN, false);
        }
        return new Endeavour(CORRECT_PROFILE, true);
    }

    private boolean isNotCorrectStatistic() {
        return Integer.parseInt(profileData.get(3)) < 0 || Integer.parseInt(profileData.get(4)) < 0
                || Integer.parseInt(profileData.get(5)) < 0 || Integer.parseInt(profileData.get(6)) < 0
                || Integer.parseInt(profileData.get(7)) < 0 || Integer.parseInt(profileData.get(3))
                != Integer.parseInt(profileData.get(5)) + Integer.parseInt(profileData.get(6))
                + Integer.parseInt(profileData.get(7));
    }

    private List<String> loadProfileData(final String profileName) throws FileNotFoundException {
        final BufferedReader reader = new BufferedReader(new FileReader
                (new File(ProfileAssistant.profilePath(profileName))));
        final List<String> profileData = reader.lines().collect(Collectors.toList());
        this.profile = convertToProfile(profileData);
        return profileData;
    }

    public final void sendData() {
        profileManager.setCurrentProfile(profile);
        final ControllerProfile controllerProfile = getControllerProfile();
        controllerProfile.setTextProfileName(profile.getName());
        setStatistics(controllerProfile, profile);
    }

    private Profile convertToProfile(final List<String> profileData) {
        final String[] parts = profileData.get(2).split("/");
        return new Profile(profileData.get(0), Byte.parseByte(parts[0]), Integer.parseInt(parts[1]),
                Integer.parseInt(profileData.get(3)), Integer.parseInt(profileData.get(4)), Integer.parseInt(profileData.get(5)),
                Integer.parseInt(profileData.get(6)), Integer.parseInt(profileData.get(7)));
    }

    private void setStatistics(final ControllerProfile controllerProfile, final Profile profile) {
        final Pane paneStatistic = controllerProfile.getPaneStatistics();
        final Text rank = (Text) paneStatistic.getChildren().get(1);
        rank.setText("Ранг: " + profile.getRank());
        final Text rating = (Text) paneStatistic.getChildren().get(2);
        rating.setText("Рейтинг: " + profile.getMMR());
        final Text wins = (Text) paneStatistic.getChildren().get(3);
        wins.setText("Побед: " + profile.getMMR());
        final Text loses = (Text) paneStatistic.getChildren().get(4);
        loses.setText("Поражений: " + profile.getMMR());
        final Text devourerWins = (Text) paneStatistic.getChildren().get(5);
        devourerWins.setText("Побед за Пожирателя: : " + profile.getMMR());
        final Text lordVampireWins = (Text) paneStatistic.getChildren().get(6);
        lordVampireWins.setText("Побед за Лорда Вампира: " + profile.getMMR());
        final Text orcBashWins = (Text) paneStatistic.getChildren().get(7);
        orcBashWins.setText("Побед за Оглушителя: " + profile.getMMR());
    }

    public final void writeData() {
    }

    @Override
    public final Profile get() {
        return profile;
    }

    @Override
    public final void setData(final Profile profile) {
        this.profile = profile;
    }

    private ControllerProfile getControllerProfile() {
        return (ControllerProfile) aGame.getWindowMap().get(WindowType.PROFILE).getController();
    }
}
