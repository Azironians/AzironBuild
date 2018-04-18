package managment.actionManagement.actionProccessors;

import heroes.abstractHero.hero.Hero;
import managment.actionManagement.ActionManager;
import managment.battleManagement.BattleManager;
import managment.playerManagement.ATeam;
import managment.playerManagement.Player;
import managment.processors.Processor;

//Not final!
public class TreatmentProcessor implements Processor {

    protected final ActionManager actionManager;

    protected final BattleManager battleManager;

    private ATeam team;

    public TreatmentProcessor(final ActionManager actionManager, final BattleManager battleManager){
        this.actionManager = actionManager;
        this.battleManager = battleManager;
    }

    @Override
    public void process() {
        final Player currentPlayer = team.getCurrentPlayer();
        final Hero currentHero = currentPlayer.getCurrentHero();
        final double treatmentValue = currentHero.getTreatment();
        if (currentHero.getHealing(treatmentValue)) {
            actionManager.getEventEngine().handle();
        }
        actionManager.refreshScreen();
        if (battleManager.isEndTurn()) {
            actionManager.endTurn(team);
        }
    }

    public void setTeam(final ATeam team){
        this.team = team;
    }
}