package bonus.devourerBonuses.bonuses.health.regenerationRoot;

import heroes.abstractHero.hero.Hero;
import managment.actionManagement.ActionManager;
import managment.actionManagement.actionProccessors.AttackProcessor;
import managment.actionManagement.actions.ActionEventFactory;
import managment.battleManagement.BattleManager;
import managment.playerManagement.ATeam;
import managment.playerManagement.Player;
import managment.playerManagement.PlayerManager;

public final class RegenerationRootProcessor extends AttackProcessor {

    private final PlayerManager playerManager;

    private Player currentPlayer;

    public RegenerationRootProcessor(final ActionManager actionManager, final BattleManager battleManager
            , final PlayerManager playerManager) {
        super(actionManager, battleManager);
        this.playerManager = playerManager;
    }

    public final void process(){
        final Hero currentHero = currentPlayer.getCurrentHero();
        final double treatmentValue = currentHero.getAttack();
        if (currentHero.getHealing(treatmentValue)) {
            actionManager.getEventEngine().handle(ActionEventFactory.getAttack(currentPlayer));
        }
        if (currentHero.addExperience(treatmentValue)){
            actionManager.getEventEngine().handle();
        }
        actionManager.refreshScreen();
        if (battleManager.isEndTurn()) {
            actionManager.endTurn(playerManager.getCurrentTeam());
        }
    }

    public final void setTeams(final ATeam unused_1, final ATeam unused_2){
        this.currentPlayer = playerManager.getCurrentTeam().getCurrentPlayer();
    }
}