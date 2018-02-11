package controllers.main.matchmaking;

import com.google.inject.Inject;
import javafx.fxml.Initializable;
import managment.actionManagement.ActionManager;
import managment.playerManagement.PlayerManager;

import java.net.URL;
import java.util.ResourceBundle;

public final class ControllerBonusLocation implements Initializable {

    @Inject
    private ActionManager actionManager;

    @Inject
    private PlayerManager playerManager;

    @Override
    public final void initialize(URL location, ResourceBundle resources) {

    }
}
