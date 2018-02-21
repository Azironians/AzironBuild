package managment.actionManagement.service.components;

import managment.actionManagement.actions.ActionEvent;
import managment.playerManagement.Player;

public interface HandleComponent {

    void setup();

    void handle(final ActionEvent actionEvent);

    String getName();

    Player getPlayer();

    boolean isWorking();

    void setAble(final boolean able);
}
