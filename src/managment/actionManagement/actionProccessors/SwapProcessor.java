package managment.actionManagement.actionProccessors;

import heroes.abstractHero.hero.Hero;
import heroes.abstractHero.skills.Skill;
import managment.actionManagement.ActionManager;
import managment.actionManagement.actions.ActionEventFactory;
import managment.actionManagement.service.engine.EventEngine;
import managment.battleManagement.BattleManager;
import managment.playerManagement.ATeam;
import managment.playerManagement.PlayerManager;
import managment.processors.Processor;


//Not final!
public class SwapProcessor implements Processor {

    private final ActionManager actionManager;

    private final BattleManager battleManager;

    private final PlayerManager playerManager;

    private final SkillProcessor skillProcessor;

    private ATeam team;

    public SwapProcessor(final ActionManager actionManager, final BattleManager battleManager
            , final PlayerManager playerManager, final SkillProcessor skillProcessor){
        this.actionManager = actionManager;
        this.battleManager = battleManager;
        this.playerManager = playerManager;
        this.skillProcessor = skillProcessor;
    }

    @Override
    public void process() {
        final ATeam currentTeam = playerManager.getCurrentTeam();
        final Hero alternativeHero = currentTeam.getAlternativePlayer().getCurrentHero();
        if (alternativeHero.getSwapSkill().isReady() && team.swapPlayers()) {
            final Hero currentHero = currentTeam.getCurrentPlayer().getCurrentHero();
            final Skill swapSkill = currentHero.getSwapSkill();
            final EventEngine eventEngine = actionManager.getEventEngine();
            eventEngine.handle(ActionEventFactory.getPlayerSwap(currentTeam.getCurrentPlayer()));
            eventEngine.handle(ActionEventFactory.getPlayerSwap(currentTeam.getAlternativePlayer()));
            if (swapSkill.isSkillAccess()) {
                this.battleManager.setEndTurn(false);
                this.skillProcessor.setTeamAndSkill(currentTeam, swapSkill);
                this.skillProcessor.process();
                this.battleManager.setEndTurn(true);
            }
            actionManager.refreshScreen();
            if (battleManager.isEndTurn()) {
                actionManager.endTurn(team);
            }
        }
    }

    public void setTeam(final ATeam team){
        this.team = team;
    }
}