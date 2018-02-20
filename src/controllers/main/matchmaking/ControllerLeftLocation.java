package controllers.main.matchmaking;

import javafx.fxml.Initializable;
import managment.playerManagement.ATeam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

public final class ControllerLeftLocation extends ControllerLocation implements Initializable {

    private static final Logger log = LoggerFactory.getLogger(ControllerLeftLocation.class);

    @Override
    public final void initialize(final URL location, final ResourceBundle resources) {

    }

    public final void imageHeroOnClicked() {
        final ATeam leftATeam = playerManager.getLeftATeam();
        makeHeroRequest(leftATeam);
    }

    public final void changeHeroOnClicked(){
        final ATeam leftTeam = playerManager.getLeftATeam();
        makeSwapHeroRequest(leftTeam);
    }
}
