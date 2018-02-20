package controllers.main.matchmaking;

import javafx.fxml.Initializable;
import managment.playerManagement.ATeam;

import java.net.URL;
import java.util.ResourceBundle;

public final class ControllerRightLocation extends ControllerLocation implements Initializable {

    @Override
    public final void initialize(final URL location, final ResourceBundle resources) {

    }

    public final void imageHeroOnClicked() {
        final ATeam rightATeam = playerManager.getRightATeam();
        makeHeroRequest(rightATeam);
    }

    public final void changeHeroOnClicked(){
        final ATeam rightATeam = playerManager.getRightATeam();
        makeSwapHeroRequest(rightATeam);
    }
}