package managment.actionManagement;

import bonus.bonuses.Bonus;
import gui.locations.engine.GraphicEngine;
import com.google.inject.Inject;
import heroes.abstractHero.AHero;
import main.AGame;
import managment.actionManagement.actions.ActionEventFactory;
import managment.actionManagement.service.bonusEngine.BonusEventEngine;
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
    private AGame aGame;

    @Inject
    private BattleManager battleManager;

    @Inject
    private PlayerManager playerManager;

    @Inject
    private BonusEventEngine bonusEventEngine;

    @Inject
    private GraphicEngine graphicEngine;

    private boolean isStandardAttack = true;

    private boolean isStandardTreatment = true;

    private boolean isStandardSwap = true;

    private boolean isStandardBonus = true;

    private boolean isStandardSkill = true;

    private Processor processor = () -> {
        //Nothing;
    };

    public final void setHeroRequest(final ATeam clickedTeam) {
        final ATeam currentTeam = playerManager.getCurrentTeam();
        final Player currentPlayer = currentTeam.getCurrentPlayer();
        final AHero currentHero = currentPlayer.getHero();

        if (clickedTeam.equals(currentTeam)) {
            if (currentHero.isTreatmentAccess()) {
                bonusEventEngine.handle(ActionEventFactory.getTreatment(currentPlayer));
                healingProcess(clickedTeam);
            }
        } else {
            if (currentHero.isAttackAccess()) {
                bonusEventEngine.handle(ActionEventFactory.getAttack(currentPlayer));
                attackProcess(clickedTeam, currentTeam);
            }
        }
    }

    private void healingProcess(final ATeam team) {
        if (isStandardTreatment) {
            final Player currentPlayer = team.getCurrentPlayer();
            final AHero currentHero = currentPlayer.getHero();
            final double treatmentValue = currentHero.getTreatment();

            if (currentHero.getHealing(treatmentValue)) {
                bonusEventEngine.handle();
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
            final AHero attackHero = attackPlayer.getHero();
            final double attackValue = attackHero.getAttack();

            if (attackHero.addExperience(attackValue)) {
                bonusEventEngine.handle();
            }
            if (victimTeam.getCurrentPlayer().getHero().getDamage(attackValue)) {
                bonusEventEngine.handle(ActionEventFactory.getDealDamage(attackPlayer));
            }
            refreshScreen();
            if (battleManager.isEndTurn()) {
                endTurn(attackTeam);
            }
        } else {
            processor.process();
        }
    }

    public final void setSkillRequest(final AHero hero, final AHero.Skill skill) {
        final ATeam currentTeam = playerManager.getCurrentTeam();
        final Player currentPlayer = currentTeam.getCurrentPlayer();

        final boolean heroAuthentication = hero.equals(currentPlayer.getHero());
        log.info("hero authentication: " + heroAuthentication);
        if (heroAuthentication) {
            final boolean access = skill.isSkillAccess();
            log.info("skill access:" + access);
            if (access) {
                bonusEventEngine.handle(ActionEventFactory.getUsedSkill(currentPlayer, skill.getName()));
                skillProcess(currentTeam, skill);
            }
        }
    }

    private void skillProcess(final ATeam currentTeam, AHero.Skill skill) {
        if (isStandardSkill) {
            skill.getActionEvents().clear();
            skill.use(battleManager, playerManager);
            skill.reset();
            bonusEventEngine.handle();
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
                final AHero alternativeHero = currentTeam.getAlternativePlayer().getHero();
                if (alternativeHero.getSwapSkill().isReady() && team.swapPlayers()) {
                    final AHero currentHero = currentTeam.getCurrentPlayer().getHero();
                    final AHero.Skill swapSkill = currentHero.getSwapSkill();
                    bonusEventEngine.handle(ActionEventFactory.getPlayerSwap(currentTeam.getCurrentPlayer()));
                    bonusEventEngine.handle(ActionEventFactory.getPlayerSwap(currentTeam.getAlternativePlayer()));
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
        bonusEventEngine.handle(ActionEventFactory.getUsedBonus(currentPlayer, bonus.getName()));
        bonusProcess(bonus);
    }

    private void bonusProcess(final Bonus bonus){
        if (isStandardBonus) {
            bonus.getActionEvents().clear();
            bonus.use();
            refreshScreen();
            bonusEventEngine.handle();
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
        bonusEventEngine.handle(ActionEventFactory.getEndTurn(team.getCurrentPlayer()));
        bonusEventEngine.handle(ActionEventFactory.getEndTurn(team.getAlternativePlayer()));
        refreshScreen();
        battleManager.nextTurn();
    }

    public final BonusEventEngine getBonusEventEngine() {
        return bonusEventEngine;
    }

    public final Processor getProcessor() {
        return processor;
    }

    public final void setProcessor(Processor processor) {
        this.processor = processor;
    }

    public final void setDefaultProcessor() {
        this.processor = () -> {
        };
    }

    public boolean isStandardAttack() {
        return isStandardAttack;
    }

    public void setStandardAttack(boolean standardAttack) {
        isStandardAttack = standardAttack;
    }

    public boolean isStandardTreatment() {
        return isStandardTreatment;
    }

    public void setStandardTreatment(boolean standardTreatment) {
        isStandardTreatment = standardTreatment;
    }

    public boolean isStandardSwap() {
        return isStandardSwap;
    }

    public void setStandardSwap(boolean standardSwap) {
        isStandardSwap = standardSwap;
    }

    public boolean isStandardBonus() {
        return isStandardBonus;
    }

    public void setStandardBonus(boolean standardBonus) {
        isStandardBonus = standardBonus;
    }

    public boolean isStandardSkill() {
        return isStandardSkill;
    }

    public void setStandardSkill(boolean standardSkill) {
        isStandardSkill = standardSkill;
    }
}
