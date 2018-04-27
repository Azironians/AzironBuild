package bonus.generalBonuses.bonuses.attack.doubleInHead;

import heroes.abstractHero.hero.Hero;
import managment.actionManagement.ActionManager;
import managment.actionManagement.actionProccessors.AttackProcessor;
import managment.actionManagement.actions.ActionEventFactory;
import managment.actionManagement.service.engine.EventEngine;
import managment.battleManagement.BattleManager;
import managment.playerManagement.ATeam;
import managment.playerManagement.Player;
import managment.playerManagement.PlayerManager;

import static bonus.generalBonuses.bonuses.attack.doubleInHead.ADoubleInHead.ATTACK_COEFFICIENT;
import static bonus.generalBonuses.bonuses.attack.doubleInHead.ADoubleInHead.LOG;

public final class DoubleInHeadProcessor extends AttackProcessor {

    private final PlayerManager playerManager;

    DoubleInHeadProcessor(final ActionManager actionManager, final BattleManager battleManager
            , final PlayerManager playerManager) {
        super(actionManager, battleManager);
        this.playerManager = playerManager;
    }

    private ATeam attackTeam;

    private ATeam victimTeam;

    @Override
    public final void process(){
        final Player attackPlayer = attackTeam.getCurrentPlayer();
        final Hero attackHero = attackPlayer.getCurrentHero();
        final EventEngine eventEngine = actionManager.getEventEngine();
        final double attackValue = attackHero.getAttack() * ATTACK_COEFFICIENT;
        LOG.info("BEFORE_ATTACK IS DUPLICATED");
        final Hero opponentHero = victimTeam.getCurrentPlayer().getCurrentHero();
        eventEngine.handle(ActionEventFactory.getBeforeDealDamage(attackPlayer, opponentHero, attackValue));
        if (opponentHero.getDamage(attackValue)) {
            eventEngine.handle(ActionEventFactory.getAfterDealDamage(attackPlayer, opponentHero, attackValue));
        }
        actionManager.refreshScreen();
        if (battleManager.isEndTurn()) {
            actionManager.endTurn(attackTeam);
        }
    }

    @Override
    public final void setTeams(final ATeam attackTeam, final ATeam victimTeam){
        this.attackTeam = playerManager.getCurrentTeam();
        this.victimTeam = playerManager.getOpponentTeam();
    }
}