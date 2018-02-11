package controllers.main.matchmaking;

import com.google.inject.Inject;
import managment.actionManagement.ActionManager;
import managment.playerManagement.ATeam;
import managment.playerManagement.PlayerManager;

abstract class ControllerLocation {

    @Inject
    private ActionManager actionManager;

    @Inject
    protected PlayerManager playerManager;

    final void makeHeroRequest(final ATeam aTeam){
        actionManager.setHeroRequest(aTeam);
    }

    final void makeSwapHeroRequest(final ATeam aTeam){
        actionManager.setPlayerSwapRequest(aTeam);
    }
}
