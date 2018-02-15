package controllers.main;

import controllers.Controller;
import gui.sceneMover.SceneMover;
import gui.windows.WindowType;
import com.google.inject.Inject;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

//Finished:
public final class ControllerInitialization implements Initializable, Controller {

    private final Logger logger = LoggerFactory.getLogger(ControllerInitialization.class);

    {
        PropertyConfigurator.configure("src\\log4j.properties");
        logger.info("Hello world");
    }

    @FXML
    private ImageView animation;

    @FXML
    private Button trigger;

    @Inject
    private SceneMover SceneMover;

    @Override
    public final void initialize(final URL location, final ResourceBundle resources) {
        appearance();
    }

    @Override
    public final void appearance() {
        final Timeline timer = new Timeline(new KeyFrame(Duration.millis(9250)));
        timer.setOnFinished(animationEnd -> {
            animation.setVisible(false);
            SceneMover.moveToScene(WindowType.MENU);
        });
        trigger.setOnKeyPressed(event -> timer.jumpTo(Duration.millis(9500)));
        timer.play();
    }
}