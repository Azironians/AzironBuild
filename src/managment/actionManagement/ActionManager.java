package managment.actionManagement;

import annotations.sourceAnnotations.Transcendental;
import bonus.bonuses.Bonus;
import gui.service.graphicEngine.GraphicEngine;
import com.google.inject.Inject;
import heroes.abstractHero.hero.Hero;
import heroes.abstractHero.skills.Skill;
import managment.actionManagement.actions.ActionEventFactory;
import managment.actionManagement.service.engine.EventEngine;
import managment.battleManagement.BattleManager;
import managment.playerManagement.ATeam;
import managment.playerManagement.Player;
import managment.playerManagement.PlayerManager;
import managment.processors.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ActionManager {
    private static final Logger log = LoggerFactory.getLogger(ActionManager.class);

    @Inject
    private BattleManager battleManager;

    @Inject
    private PlayerManager playerManager;

    @Inject
    private EventEngine eventEngine;

    @Inject
    private GraphicEngine graphicEngine;

    // FIXME: 17.04.2018 There are not standard processors!!!
    // FIXME: You should to wrap all actions in SAM Processor and add all processor Bonuses field "previousProcessor"

    @Transcendental
    private boolean isStandardAttack = true;

    @Transcendental
    private boolean isStandardTreatment = true;

    @Transcendental
    private boolean isStandardSwap = true;

    @Transcendental
    private boolean isStandardBonus = true;

    @Transcendental
    private boolean isStandardSkill = true;

    @Transcendental
    private Processor processor = () -> {
        //Nothing;
    };

    public final void setHeroRequest(final ATeam clickedTeam) {
        final ATeam currentTeam = playerManager.getCurrentTeam();
        final Player currentPlayer = currentTeam.getCurrentPlayer();
        final Hero currentHero = currentPlayer.getCurrentHero();

        if (clickedTeam.equals(currentTeam)) {
            if (currentHero.isTreatmentAccess()) {
                eventEngine.handle(ActionEventFactory.getTreatment(currentPlayer));
                healingProcess(clickedTeam);
            }
        } else {
            if (currentHero.isAttackAccess()) {
                eventEngine.handle(ActionEventFactory.getAttack(currentPlayer));
                attackProcess(clickedTeam, currentTeam);
            }
        }
    }

    private void healingProcess(final ATeam team) {
        if (isStandardTreatment) {
            final Player currentPlayer = team.getCurrentPlayer();
            final Hero currentHero = currentPlayer.getCurrentHero();
            final double treatmentValue = currentHero.getTreatment();

            if (currentHero.getHealing(treatmentValue)) {
                eventEngine.handle();
            }
            refreshScreen();
            if (battleManager.isEndTurn()) {
                endTurn(team);
            }
        } else {
            processor.process();
        }
    }

    private void attackProcess(final ATeam victimTeam, final ATeam attackTeam) {
        if (isStandardAttack) {
            final Player attackPlayer = attackTeam.getCurrentPlayer();
            final Hero attackHero = attackPlayer.getCurrentHero();
            final double attackValue = attackHero.getAttack();

            if (attackHero.addExperience(attackValue)) {
                eventEngine.handle();
            }
            final Hero victimHero = victimTeam.getCurrentPlayer().getCurrentHero();
            if (victimHero.getDamage(attackValue)) {
                eventEngine.handle(ActionEventFactory.getAfterDealDamage(attackPlayer, victimHero, attackValue));
            }
            refreshScreen();
            if (battleManager.isEndTurn()) {
                endTurn(attackTeam);
            }
        } else {
            processor.process();
        }
    }

    public final void setSkillRequest(final Hero hero, final Skill skill) {
        final ATeam currentTeam = playerManager.getCurrentTeam();
        final Player currentPlayer = currentTeam.getCurrentPlayer();

        final boolean heroAuthentication = hero.equals(currentPlayer.getCurrentHero());
        log.info("hero authentication: " + heroAuthentication);
        if (heroAuthentication) {
            final boolean access = skill.isSkillAccess();
            log.info("skill access:" + access);
            if (access) {
                eventEngine.handle(ActionEventFactory.getBeforeUsedSkill(currentPlayer
                        , skill.getName()));
                skillProcess(currentTeam, skill);
            }
        }
    }

    private void skillProcess(final ATeam currentTeam, Skill skill) {
        if (isStandardSkill) {
            skill.getActionEvents().clear();
            skill.use(battleManager, playerManager); //FIXME: wrap all skills in processor
            skill.reset();
            skill.getActionEvents().forEach(eventEngine::handle);
            refreshScreen();
            if (battleManager.isEndTurn()) {
                endTurn(currentTeam);
            }
        } else {
            processor.process();
        }
    }

    public final void setPlayerSwapRequest(final ATeam team) {
        final ATeam currentTeam = playerManager.getCurrentTeam();
        if (team.equals(currentTeam)) {
            if (isStandardSwap) {
                final Hero alternativeHero = currentTeam.getAlternativePlayer().getCurrentHero();
                if (alternativeHero.getSwapSkill().isReady() && team.swapPlayers()) {
                    final Hero currentHero = currentTeam.getCurrentPlayer().getCurrentHero();
                    final Skill swapSkill = currentHero.getSwapSkill();
                    eventEngine.handle(ActionEventFactory.getPlayerSwap(currentTeam.getCurrentPlayer()));
                    eventEngine.handle(ActionEventFactory.getPlayerSwap(currentTeam.getAlternativePlayer()));
                    if (swapSkill.isSkillAccess()) {
                        battleManager.setEndTurn(false);
                        skillProcess(currentTeam, swapSkill);
                        battleManager.setEndTurn(true);
                    }
                    refreshScreen();
                    if (battleManager.isEndTurn()) {
                        endTurn(team);
                    }
                }
            } else {
                processor.process();
            }
        }
    }

    public final void setBonusRequest(final Bonus bonus) {
        graphicEngine.hideBonuses();
        final ATeam currentTeam = playerManager.getCurrentTeam();
        final Player currentPlayer = currentTeam.getCurrentPlayer();
        eventEngine.handle(ActionEventFactory.getAfterUsedBonus(currentPlayer, bonus));
        bonusProcess(bonus);
    }

    private void bonusProcess(final Bonus bonus){
        if (isStandardBonus) {
            bonus.getActionEvents().clear();
            bonus.use();
            refreshScreen();
            eventEngine.handle();
            refreshScreen();
        } else {
            processor.process();
        }
    }

    public final void setEagerPlayerSwapRequest(final ATeam team) {
        team.eagerSwapPlayers();
        refreshScreen();
    }

    public final void refreshScreen() {
        graphicEngine.showLocation();
    }

    public final void endTurn(final ATeam team) {
        eventEngine.handle(ActionEventFactory.getEndTurn(team.getCurrentPlayer()));
        eventEngine.handle(ActionEventFactory.getEndTurn(team.getAlternativePlayer()));
        refreshScreen();
        battleManager.nextTurn();
    }

    public final EventEngine getEventEngine() {
        return eventEngine;
    }

    public final GraphicEngine getGraphicEngine() {
        return graphicEngine;
    }

    @Transcendental
    public final Processor getProcessor() {
        return processor;
    }

    public final void setProcessor(Processor processor) {
        this.processor = processor;
    }

    @Transcendental
    public final void setDefaultProcessor() {
        this.processor = () -> {
        };
    }

    @Transcendental
    public boolean isStandardAttack() {
        return isStandardAttack;
    }

    @Transcendental
    public void setStandardAttack(boolean standardAttack) {
        isStandardAttack = standardAttack;
    }

    @Transcendental
    public boolean isStandardTreatment() {
        return isStandardTreatment;
    }

    @Transcendental
    public void setStandardTreatment(boolean standardTreatment) {
        isStandardTreatment = standardTreatment;
    }

    @Transcendental
    public boolean isStandardSwap() {
        return isStandardSwap;
    }

    @Transcendental
    public void setStandardSwap(boolean standardSwap) {
        isStandardSwap = standardSwap;
    }

    @Transcendental
    public boolean isStandardBonus() {
        return isStandardBonus;
    }

    @Transcendental
    public void setStandardBonus(boolean standardBonus) {
        isStandardBonus = standardBonus;
    }

    @Transcendental
    public boolean isStandardSkill() {
        return isStandardSkill;
    }

    @Transcendental
    public void setStandardSkill(boolean standardSkill) {
        isStandardSkill = standardSkill;
    }
}
