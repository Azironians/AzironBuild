package managment.actionManagement.service.bonusEngine;

import bonus.bonuses.Bonus;
import bonus.bonuses.HandlerBonus;
import bonus.bonuses.InstallerBonus;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import managment.actionManagement.ActionManager;
import managment.actionManagement.actions.ActionEvent;
import managment.battleManagement.BattleManager;
import managment.playerManagement.Player;
import managment.playerManagement.PlayerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Singleton
public final class BonusEventEngine {

    private static final Logger log = LoggerFactory.getLogger(BonusEventEngine.class);

    private static final ActionEvent EMPTY_EVENT = new ActionEvent(null, null, null);

    @Inject
    private ActionManager actionManager;

    @Inject
    private BattleManager battleManager;

    @Inject
    private PlayerManager playerManager;

    private List<HandlerBonus.GetAHandler> bonusHandlers;

    public final void install(){
        this.bonusHandlers = Collections.synchronizedList(new ArrayList<>());
        install(playerManager.getCurrentTeam().getCurrentPlayer());
        install(playerManager.getCurrentTeam().getAlternativePlayer());
        install(playerManager.getOpponentATeam().getCurrentPlayer());
        install(playerManager.getOpponentATeam().getCurrentPlayer());
        log.info("BonusEventEngine install was successful!");
    }

    private void install(final Player player){
        final List<Bonus> collection = player.getHero().getBonusCollection();
        for (final Bonus bonus : collection){
            wireManagersToBonus(bonus, actionManager, battleManager, playerManager);
            if (implementsInstallerBonus(bonus)){
                final InstallerBonus installerBonus = (InstallerBonus) bonus;
                addHandler(installerBonus.getInstallHandlerInstance(player));
            }
        }
    }

    private void wireManagersToBonus(final Bonus bonus
            , final ActionManager actionManager
            , final BattleManager battleManager
            , final PlayerManager playerManager){
        bonus.setActionManager(actionManager);
        bonus.setBattleManager(battleManager);
        bonus.setPlayerManager(playerManager);
    }

    private boolean implementsInstallerBonus(final Bonus bonus){
        final Class<?>[] interfaces = bonus.getClass().getInterfaces();
        for (final Class clazz : interfaces){
            if (clazz.equals(InstallerBonus.class)){
                log.info(bonus.getName() + " implements InstallerBonus");
                return true;
            }
        }
        return false;
    }

    public synchronized final void handle(final ActionEvent actionEvent) {
        final List<HandlerBonus.GetAHandler> garbageHandlerList = new ArrayList<>();
        for (int i = 0 ; i < bonusHandlers.size(); i++) {
            if (bonusHandlers.get(i).isWorking()) {
                bonusHandlers.get(i).handle(actionEvent);
            } else {
                garbageHandlerList.add(bonusHandlers.get(i));
                log.debug(bonusHandlers.get(i).getName() + " successfully was removed");
            }
        }
        bonusHandlers.removeAll(garbageHandlerList);
    }

    public final void handle() {
        handle(EMPTY_EVENT);
    }

    public final void addHandler(final HandlerBonus.GetAHandler handler) {
        handler.setup();
        bonusHandlers.add(handler);
    }
}