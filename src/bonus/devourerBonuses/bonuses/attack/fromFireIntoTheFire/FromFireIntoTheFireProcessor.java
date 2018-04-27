package bonus.devourerBonuses.bonuses.attack.fromFireIntoTheFire;

import heroes.abstractHero.hero.Hero;
import managment.actionManagement.ActionManager;
import managment.actionManagement.actionProccessors.TreatmentProcessor;
import managment.actionManagement.actions.ActionEventFactory;
import managment.actionManagement.service.engine.EventEngine;
import managment.battleManagement.BattleManager;
import managment.playerManagement.ATeam;
import managment.playerManagement.Player;
import managment.playerManagement.PlayerManager;

public final class FromFireIntoTheFireProcessor extends TreatmentProcessor {

    private final PlayerManager playerManager;

    private Player currentPlayer;

    private Player opponentPlayer;

    public FromFireIntoTheFireProcessor(final ActionManager actionManager, final BattleManager battleManager
            , final PlayerManager playerManager) {
        super(actionManager, battleManager);
        this.playerManager = playerManager;
    }

    public final void process(){
        final Hero currentHero = currentPlayer.getCurrentHero();
        final Hero opponentHero = opponentPlayer.getCurrentHero();
        final double damage = currentHero.getTreatment();
        if (opponentHero.getDamage(damage)) {
            final EventEngine eventEngine = actionManager.getEventEngine();
            eventEngine.handle(ActionEventFactory.getTreatment(currentPlayer));
            eventEngine.handle(ActionEventFactory.getAfterDealDamage(currentPlayer, opponentHero, damage));
        }
        actionManager.refreshScreen();
        if (battleManager.isEndTurn()) {
            actionManager.endTurn(playerManager.getCurrentTeam());
        }
    }

    public final void setTeam(final ATeam unused){
        this.currentPlayer = playerManager.getCurrentTeam().getCurrentPlayer();
        this.opponentPlayer = playerManager.getOpponentTeam().getCurrentPlayer();
    }
}