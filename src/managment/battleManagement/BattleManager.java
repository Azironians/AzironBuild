package managment.battleManagement;

import bonus.bonuses.Bonus;
import gui.locations.engine.GraphicEngine;
import heroes.abstractHero.hero.Hero;
import managment.actionManagement.ActionManager;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import managment.actionManagement.actions.ActionEventFactory;
import managment.actionManagement.service.bonusEngine.BonusEventEngine;
import managment.playerManagement.ATeam;
import managment.playerManagement.GameMode;
import managment.playerManagement.Player;
import managment.playerManagement.PlayerManager;
import managment.processors.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;

public final class BattleManager {

    private final static Logger log = LoggerFactory.getLogger(BattleManager.class);

    @Inject
    private PlayerManager playerManager;

    @Inject
    private ActionManager actionManager;

    @Inject
    private GraphicEngine graphicEngine;

    @Inject
    private BonusEventEngine bonusEventEngine;

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

    private boolean isStandardRandomBonus = true;

    private Processor processor = () -> {
        //Empty
    };

    //Next turn:
    public final void nextTurn() {
        final Player currentPlayer = playerManager.getCurrentTeam().getCurrentPlayer();
        final boolean isDestroyed = currentPlayer.getHero().getHitPoints() <= 0;
        if (isDestroyed) {
            currentPlayer.setAlive(false);
            if (isEndGame()) {
                bonusEventEngine.handle(ActionEventFactory.getEndGame(currentPlayer));
                log.info("GAME_OVER");
                endGame();
            } else {
                bonusEventEngine.handle(ActionEventFactory.getPlayerOut(currentPlayer));
                makeEagerPlayerSwapRequest();
                log.info("PLAYER_OUT");
            }
        } else {
            changeTurn();
            if (skipTurn) {
                graphicEngine.hideBonuses();
                final Player newCurrentPlayer = playerManager.getCurrentTeam().getCurrentPlayer();
                bonusEventEngine.handle(ActionEventFactory.getSkipTurn(newCurrentPlayer));
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

        currentPlayer.getHero().reloadSkills();
        if (playerManager.getGameMode() == GameMode._2x2){
            alternativePlayer.getHero().reloadSkills();
            alternativePlayer.getHero().getSwapSkill().reload();
        }
        //handling:
        bonusEventEngine.handle(ActionEventFactory.getStartTurn(currentPlayer));
        bonusEventEngine.handle(ActionEventFactory.getStartTurn(alternativePlayer));
        loadRandomBonuses(currentPlayer.getHero());
    }

    public void loadRandomBonuses(final Hero hero) {
        if (isStandardRandomBonus){
            final List<Bonus> bonusList = hero.getBonusCollection();
            final Random random = new Random();
            final int bound = 16; //16 cards;

            final int firstBonus = random.nextInt(bound);
            int secondBonus = random.nextInt(bound);
            int thirdBonus = random.nextInt(bound);
            while (secondBonus == firstBonus){
                secondBonus = random.nextInt(bound);
            }
            while (thirdBonus == firstBonus || thirdBonus == secondBonus){
                thirdBonus = random.nextInt(bound);
            }
            graphicEngine.showBonuses(bonusList, firstBonus, secondBonus, thirdBonus);
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

    public final boolean isStandardRandomBonus() {
        return isStandardRandomBonus;
    }

    public final void setStandardRandomBonus(boolean standardRandomBonus) {
        isStandardRandomBonus = standardRandomBonus;
    }
}