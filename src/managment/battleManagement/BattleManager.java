package managment.battleManagement;

import annotations.sourceAnnotations.Transcendental;
import bonus.bonuses.Bonus;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import gui.service.graphicEngine.GraphicEngine;
import heroes.abstractHero.hero.Hero;
import managment.actionManagement.ActionManager;
import managment.actionManagement.actions.ActionEventFactory;
import managment.actionManagement.service.components.providerComponent.ProviderComponent;
import managment.actionManagement.service.engine.EventEngine;
import heroes.abstractHero.bonusManagement.BonusManager;
import managment.playerManagement.ATeam;
import managment.playerManagement.GameMode;
import managment.playerManagement.Player;
import managment.playerManagement.PlayerManager;
import managment.processors.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public final class BattleManager {

    private final static Logger log = LoggerFactory.getLogger(BattleManager.class);

    @Inject
    private PlayerManager playerManager;

    @Inject
    private ActionManager actionManager;

    @Inject
    private GraphicEngine graphicEngine;

    @Inject
    private EventEngine eventEngine;

    @Inject
    @Named("start time")
    private static int startTime;

    @Inject
    @Named("turn")
    private int turn;

    //End turn condition:
    @Inject
    @Named("end turn condition")
    private boolean endTurn;

    @Inject
    @Named("skip turn condition")
    private boolean skipTurn;

    private boolean isStandardRandomBonusEngine = true;

    private Processor processor = () -> {
        //Empty
    };

    //Next turn:
    public final void nextTurn() {
        final Player currentPlayer = playerManager.getCurrentTeam().getCurrentPlayer();
        final boolean isDestroyed = currentPlayer.getCurrentHero().getHitPoints() <= 0;
        if (isDestroyed) {
            currentPlayer.setAlive(false);
            if (isEndGame()) {
                eventEngine.handle(ActionEventFactory.getEndGame(currentPlayer));
                log.info("GAME_OVER");
                endGame();
            } else {
                eventEngine.handle(ActionEventFactory.getPlayerOut(currentPlayer));
                makeEagerPlayerSwapRequest();
                log.info("PLAYER_OUT");
            }
        } else {
            changeTurn();
            if (skipTurn) {
                graphicEngine.hideBonuses();
                final Player newCurrentPlayer = playerManager.getCurrentTeam().getCurrentPlayer();
                eventEngine.handle(ActionEventFactory.getSkipTurn(newCurrentPlayer));
                skipTurn = false;
                nextTurn();
            }
        }
    }

    //Defines turn:
    private void changeTurn() {
        final ATeam left = playerManager.getLeftATeam();
        final ATeam right = playerManager.getRightATeam();
        //CHANGE: //////////////////////////////////////////////////////////
        turn = (turn + 1) % 2;
        if (turn == left.getTurn()) {
            playerManager.setCurrentATeam(left);
            playerManager.setOpponentATeam(right);
        }
        if (turn == right.getTurn()) {
            playerManager.setCurrentATeam(right);
            playerManager.setOpponentATeam(left);
        }
        ////////////////////////////////////////////////////////////////////
        final ATeam currentTeam = playerManager.getCurrentTeam();
        final Player currentPlayer = currentTeam.getCurrentPlayer();
        final Player alternativePlayer = currentTeam.getAlternativePlayer();

        currentPlayer.getCurrentHero().reloadSkills();
        if (playerManager.getGameMode() == GameMode._2x2){
            alternativePlayer.getCurrentHero().reloadSkills();
            alternativePlayer.getCurrentHero().getSwapSkill().reload();
        }
        //handling:
        eventEngine.handle(ActionEventFactory.getStartTurn(currentPlayer));
        if (playerManager.getGameMode() == GameMode._2x2){
            eventEngine.handle(ActionEventFactory.getStartTurn(alternativePlayer));
        }
        loadRandomBonuses(currentPlayer.getCurrentHero());
    }

    @Transcendental
    public void loadRandomBonuses(final Hero hero) {
        if (isStandardRandomBonusEngine){
            final List<Bonus> bonusList = hero.getBonusCollection();
            final List<ProviderComponent<Integer>> providerComponents = hero.getBonusManager()
                    .getProviderComponentList();
            final int firstBonus = providerComponents.get(0).getValue();
            int secondBonus = providerComponents.get(1).getValue();
            int thirdBonus = providerComponents.get(2).getValue();
            while (secondBonus == firstBonus){
                secondBonus = providerComponents.get(1).getValue();
            }
            while (thirdBonus == firstBonus || thirdBonus == secondBonus){
                thirdBonus = providerComponents.get(2).getValue();
            }
            graphicEngine.show3Bonuses(bonusList, firstBonus, secondBonus, thirdBonus);
            log.info("BONUS ID: " + firstBonus);
            log.info("BONUS ID: " + secondBonus);
            log.info("BONUS ID: " + thirdBonus);
        } else {
            processor.process();
        }
    }

    private boolean isEndGame() {
        final Player alternativePlayer = playerManager.getCurrentTeam().getAlternativePlayer();
        return alternativePlayer == null || !alternativePlayer.isAlive();
    }

    public final void endGame() {

    }

    private void makeEagerPlayerSwapRequest() {
        final ATeam currentTeam = playerManager.getCurrentTeam();
        actionManager.setEagerPlayerSwapRequest(currentTeam);
    }

    public static int getStartTime() {
        return startTime;
    }

    public final boolean isEndTurn() {
        return endTurn;
    }

    public final void setEndTurn(final boolean endTurn) {
        this.endTurn = endTurn;
    }

    public final void setSkipTurn(final boolean skipTurn) {
        this.skipTurn = skipTurn;
    }

    @Transcendental
    public final void setStandardRandomBonusEngine(boolean standardRandomBonusEngine) {
        this.isStandardRandomBonusEngine = standardRandomBonusEngine;
    }

    @Transcendental
    public final Processor getProcessor() {
        return processor;
    }

    @Transcendental
    public final void setProcessor(final Processor processor) {
        this.processor = processor;
    }

    @Transcendental
    public final void setDefaultProcessor() {
        this.processor = () -> {
            //Empty
        };
    }
}