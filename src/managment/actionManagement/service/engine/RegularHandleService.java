package managment.actionManagement.service.engine;

import managment.actionManagement.service.components.HandleComponent;
import managment.playerManagement.Player;

public interface RegularHandleService {

    HandleComponent getRegularHandlerInstance(Player player);

}
