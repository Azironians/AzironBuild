package security.gate;

import annotations.bindingAnnotations.BonusService;
import annotations.bindingAnnotations.ProfileService;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import managment.profileManagement.Profile;
import security.loadSuppliers.LoadSupplier;
import gui.endavour.Endeavour;
import com.google.inject.Inject;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.jetbrains.annotations.NotNull;
import security.assistants.ProfileAssistant;
import security.loadSuppliers.bonusSupplier.BonusData;

import java.io.*;
import java.util.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static heroes.heroUtils.HeroUtils.heroNames;

@Singleton
public final class AGate {
    //Logger:
    private final Logger logger = Logger.getLogger(AGate.class.getName());

    {
        logger.setLevel(Level.FINEST);
        logger.addHandler(new ConsoleHandler() {{
            setLevel(Level.FINEST);
        }});
    }

    @Inject
    @ProfileService
    private LoadSupplier<Profile> profileLoadSupplier;

    @Inject
    @BonusService
    private LoadSupplier<BonusData> bonusLoadSupplier;

    @NotNull
    public final Endeavour isAuthorizationSuccessful(final TextField textFieldSignIn
            , final PasswordField passwordFieldSignIn) {
        final String login = textFieldSignIn.getText();
        final String password = passwordFieldSignIn.getText();

        final Endeavour profileReading = profileLoadSupplier.readData(login, password);
        if (profileReading.isSuccessful()) {
            return bonusLoadSupplier.readData(login, password);
        } else {
            return profileReading;
        }
    }

    public final void doAuthorization(final Profile profile, final BonusData bonusData){
        profileLoadSupplier.setData(profile);
        bonusLoadSupplier.setData(bonusData);
        doAuthorization();
    }

    public final void doAuthorization() {
        profileLoadSupplier.sendData();
        bonusLoadSupplier.sendData();
    }

    @Singleton
    public static final class RegisterService {

        //Registration messages:
        @Inject
        @Named("DIFFERENT_NAMES")
        private static String DIFFERENT_NAMES;

        @Inject
        @Named("NOT_FILLED_LOGIN_FIELD")
        private static String NOT_FILLED_LOGIN_FIELD;

        @Inject
        @Named("DIFFERENT_PASSWORD")
        private static String DIFFERENT_PASSWORD;

        @Inject
        @Named("PROFILE_IS_EXIST")
        private static String PROFILE_IS_EXIST;

        @Inject
        @Named("SUCCESSFUL_REGISTRATION")
        private static String SUCCESSFUL_REGISTRATION;

        public final Endeavour isRegistrationSuccessful(final TextField textFieldNewName
                , final TextField textFieldNewNameRepeat
                , final PasswordField passwordFieldNewPassword
                , final PasswordField passwordFieldNewPasswordRepeat) {
            if (!textFieldNewName.getText().equals(textFieldNewNameRepeat.getText())) {
                return new Endeavour(DIFFERENT_NAMES, false);
            }
            if (textFieldNewName.getText().equals("")) {
                return new Endeavour(NOT_FILLED_LOGIN_FIELD, false);
            }
            if (!passwordFieldNewPassword.getText().equals(passwordFieldNewPasswordRepeat.getText())) {
                return new Endeavour(DIFFERENT_PASSWORD, false);
            }
            if (new File(ProfileAssistant.profilePath(textFieldNewName.getText())).canRead()) {
                return new Endeavour(PROFILE_IS_EXIST, false);
            }
            return new Endeavour(SUCCESSFUL_REGISTRATION, true);
        }

        public final void doRegistration(final TextField textFieldNewName
                , final PasswordField passwordFieldNewPassword) {
            final String login = textFieldNewName.getText();
            final String password = passwordFieldNewPassword.getText();
            BufferedWriter bufferedWriter;
            try {
                //Creating of new account:
                final File newDirectory = new File(ProfileAssistant.directoryPath(login));
                if (newDirectory.mkdirs()) {
                    final File newProfile = new File(ProfileAssistant.profilePath(login));
                    bufferedWriter = new BufferedWriter(new FileWriter(newProfile));
                    final List<String> profileList = ProfileAssistant.createProfile(login, password);
                    int i = 0;
                    for (final String line : profileList) {
                        bufferedWriter.write(line);
                        i++;
                        if (i < profileList.size()) bufferedWriter.newLine();
                    }
                    bufferedWriter.close();
                }
                //Creating start decks for account:
                final File newCollectionDirectory = new File(ProfileAssistant.collectionsPath(login));
                if (newCollectionDirectory.mkdirs()) {
                    for (final String hero : heroNames) {
                        bufferedWriter = new BufferedWriter(new FileWriter(new File
                                (ProfileAssistant.heroCollectionPath(login, hero))));
                        final String classicCollectionID = ProfileAssistant.createDefaultDeck(hero);
                        bufferedWriter.write(classicCollectionID);
                        bufferedWriter.close();
                    }
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }
}