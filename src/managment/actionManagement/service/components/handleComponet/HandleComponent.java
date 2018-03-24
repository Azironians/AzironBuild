package managment.actionManagement.service.components.handleComponet;

import managment.actionManagement.actions.ActionEvent;
import managment.playerManagement.Player;

public interface HandleComponent {

    void setup();

    void handle(final ActionEvent actionEvent);

    String getName();

    Player getCurrentPlayer();

    boolean isWorking();

    void setWorking(final boolean able) throws IllegalSwitchOffHandleComponentException;
}