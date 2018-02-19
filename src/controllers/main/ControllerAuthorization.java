package controllers.main;

import controllers.Controller;
import gui.clock.AClock;
import gui.endavour.Endeavour;
import gui.sceneMover.SceneMover;
import gui.windows.WindowType;
import security.gate.AGate;
import com.google.inject.Inject;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

//Worked:
public final class ControllerAuthorization implements Initializable, Controller {

    @FXML
    private Pane paneSignIn;

    @FXML
    private Pane paneSignUp;

    @FXML
    private Pane paneLuckSign;

    @FXML
    private Text textErrorSignUp;

    @FXML
    private Text textErrorSignIn;

    @FXML
    private TextField textFieldSignIn;

    @FXML
    private TextField textFieldNewName;

    @FXML
    private TextField textFieldNewNameRepeat;

    @FXML
    private PasswordField passwordFieldSignIn;

    @FXML
    private PasswordField passwordFieldNewPassword;

    @FXML
    private PasswordField passwordFieldNewPasswordRepeat;

    @FXML
    private ImageView buttonOffBackToMenu;

    @FXML
    private ImageView buttonOnBackToMenu;

    @FXML
    private Text time;

    @FXML
    private Text textLuck;

    @FXML
    private Button trigger;

    @Inject
    private SceneMover sceneMover;

    @Inject
    private AGate aGate;

    @Inject
    private AGate.RegisterService registerService;

    @Override
    public final void initialize(URL location, ResourceBundle resources) {
        trigger.setOnKeyPressed(this::triggerPressed);
        textFieldSignIn.setOnKeyPressed(this::triggerPressed);
        passwordFieldSignIn.setOnKeyPressed(this::triggerPressed);
        AClock.launchTimer(time).start();
    }

    //Style & interface:
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Кнопка "Вход":
    public final void buttonSignInClicked() {
        final Endeavour endeavour = aGate.isAuthorizationSuccessful(textFieldSignIn, passwordFieldSignIn);
        if (endeavour.isSuccessful()) {
            aGate.doAuthorization();
            paneSignIn.setVisible(false);
            paneLuckSign.setVisible(true);
            textLuck.setText(endeavour.getMessage());
            final Timeline delay = new Timeline(new KeyFrame(Duration.millis(1000)));
            delay.setOnFinished(event -> {
                sceneMover.moveToScene(WindowType.PROFILE);
                paneLuckSign.setVisible(false);
                paneSignIn.setVisible(true);
                textErrorSignIn.setText("");
                textFieldSignIn.setText("");
            });
            delay.play();
        } else {
            textErrorSignIn.setText(endeavour.getMessage());
        }
    }

    //Кнопка "Регистрация"
    public final void buttonSignUpClicked() {
        paneSignIn.setVisible(false);
        paneSignUp.setVisible(true);
    }

    // Кнопка "Назад в меню"
    public final void buttonOffBackToMenuEntered() {
        buttonOffBackToMenu.setVisible(false);
        buttonOnBackToMenu.setVisible(true);
    }

    public final void buttonOnBackToMenuExited() {
        buttonOnBackToMenu.setVisible(false);
        buttonOffBackToMenu.setVisible(true);
    }

    public final void buttonOnBackClicked() {
        if (paneSignIn.isVisible()) {
            sceneMover.moveToScene(WindowType.MENU);
        }
        if (paneSignUp.isVisible()) {
            paneSignUp.setVisible(false);
            paneSignIn.setVisible(true);
        }
    }

    //Кнопка: "Зарегистрироваться"
    public final void buttonSignUpOkClicked() {
        final Endeavour endeavour = registerService.isRegistrationSuccessful(
                textFieldNewName, textFieldNewNameRepeat, passwordFieldNewPassword,
                passwordFieldNewPasswordRepeat);
        if (endeavour.isSuccessful()) {
            registerService.doRegistration(textFieldNewName, passwordFieldNewPassword);
            paneSignUp.setVisible(false);
            paneLuckSign.setVisible(true);
            textLuck.setText(endeavour.getMessage());
            final Timeline delay = new Timeline(new KeyFrame(Duration.millis(1000)));
            delay.setOnFinished(event -> {
                paneLuckSign.setVisible(false);
                paneSignIn.setVisible(true);
                textErrorSignUp.setText("");
            });
            delay.play();
        } else {
            textErrorSignUp.setText(endeavour.getMessage());
        }
    }

    public final void buttonFromSignUpToSignInClicked() {
        paneSignIn.setVisible(true);
        paneSignUp.setVisible(false);
    }

    private void triggerPressed(KeyEvent event) {
        if (event.getCode() == (KeyCode.ENTER)) {
            buttonSignInClicked();
        }
        if (event.getCode() == (KeyCode.ESCAPE)) {
            sceneMover.moveToScene(WindowType.MENU);
        }
    }

    @Override
    public final void appearance() {
    }
}