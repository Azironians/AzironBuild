package managment.actionManagement.actionProccessors;

import heroes.abstractHero.hero.Hero;
import managment.actionManagement.ActionManager;
import managment.actionManagement.actions.ActionEventFactory;
import managment.actionManagement.service.engine.EventEngine;
import managment.battleManagement.BattleManager;
import managment.playerManagement.ATeam;
import managment.playerManagement.Player;
import managment.processors.Processor;

//Not final!
public class AttackProcessor implements Processor {

    private ATeam attackTeam;

    private ATeam victimTeam;

    protected final ActionManager actionManager;

    protected final BattleManager battleManager;

    public AttackProcessor(final ActionManager actionManager, final BattleManager battleManager){
        this.actionManager = actionManager;
        this.battleManager = battleManager;
    }

    @Override
    public void process() {
        final Player attackPlayer = attackTeam.getCurrentPlayer();
        final Hero attackHero = attackPlayer.getCurrentHero();
        final double attackValue = attackHero.getAttack();
        final EventEngine eventEngine = actionManager.getEventEngine();
        if (attackHero.addExperience(attackValue)) {
            eventEngine.handle();
        }
        final Hero victimHero = victimTeam.getCurrentPlayer().getCurrentHero();
        if (victimHero.getDamage(attackValue)) {
            eventEngine.handle(ActionEventFactory.getAfterDealDamage(attackPlayer, victimHero, attackValue));
        }
        actionManager.refreshScreen();
        if (battleManager.isEndTurn()) {
            actionManager.endTurn(attackTeam);
        }
    }

    public void setTeams(final ATeam attackTeam, final ATeam victimTeam){
        this.attackTeam = attackTeam;
        this.victimTeam = victimTeam;
    }
}