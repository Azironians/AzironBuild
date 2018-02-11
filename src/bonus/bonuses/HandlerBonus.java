package bonus.bonuses;

import managment.actionManagement.actions.ActionEvent;
import managment.playerManagement.Player;

public interface HandlerBonus {

    GetAHandler getHandlerInstance();

    interface GetAHandler {

        void setup();

        void handle(final ActionEvent actionEvent);

        String getName();

        Player getPlayer();

        boolean isWorking();

        void setAble(final boolean able);
    }
}
