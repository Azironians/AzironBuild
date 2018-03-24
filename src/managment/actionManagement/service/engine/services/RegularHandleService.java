package managment.actionManagement.service.engine.services;

import managment.actionManagement.service.components.handleComponet.HandleComponent;
import managment.playerManagement.Player;

public interface RegularHandleService {

    HandleComponent getRegularHandlerInstance(Player player);

}
