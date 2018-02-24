package managment.battleManagement;

import bonus.bonuses.Bonus;
import gui.service.graphicEngine.GraphicEngine;
import heroes.abstractHero.hero.Hero;
import javafx.util.Pair;
import managment.actionManagement.ActionManager;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import managment.actionManagement.actions.ActionEventFactory;
import managment.actionManagement.service.components.ProviderComponent;
import managment.actionManagement.service.engine.EventEngine;
import managment.playerManagement.ATeam;
import managment.playerManagement.GameMode;
import managment.playerManagement.Player;
import managment.playerManagement.PlayerManager;
import managment.processors.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
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

    private List<Pair> standardRandomBonuses = Arrays
            .asList(new Pair<>(true, 1), new Pair<>(true, 2), new Pair<>(true, 3));

    private List<ProviderComponent<Integer>> bonusGetterList = new ArrayList<>(){{
        final Random random = new Random();
        final int bound = 16; //16 cards;
        add(() -> random.nextInt(bound));
        add(() -> random.nextInt(bound));
        add(() -> random.nextInt(bound));
    }};

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

        currentPlayer.getHero().reloadSkills();
        if (playerManager.getGameMode() == GameMode._2x2){
            alternativePlayer.getHero().reloadSkills();
            alternativePlayer.getHero().getSwapSkill().reload();
        }
        //handling:
        eventEngine.handle(ActionEventFactory.getStartTurn(currentPlayer));
        if (playerManager.getGameMode() == GameMode._2x2){
            eventEngine.handle(ActionEventFactory.getStartTurn(alternativePlayer));
        }
        loadRandomBonuses(currentPlayer.getHero());
    }

    public void loadRandomBonuses(final Hero hero) {
        if (isStandardRandomBonusEngine){
            final List<Bonus> bonusList = hero.getBonusCollection();
            final int firstBonus = bonusGetterList.get(0).getValue();
            int secondBonus = bonusGetterList.get(1).getValue();
            int thirdBonus = bonusGetterList.get(2).getValue();
            while (secondBonus == firstBonus){
                secondBonus = bonusGetterList.get(1).getValue();
            }
            while (thirdBonus == firstBonus || thirdBonus == secondBonus){
                thirdBonus = bonusGetterList.get(2).getValue();
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

    public final boolean isStandardRandomBonusEngine() {
        return isStandardRandomBonusEngine;
    }

    public final void setStandardRandomBonusEngine(boolean standardRandomBonusEngine) {
        isStandardRandomBonusEngine = standardRandomBonusEngine;
    }

    public final Processor getProcessor() {
        return processor;
    }

    public final void setProcessor(final Processor processor) {
        this.processor = processor;
    }

    public final void setDefaultProcessor() {
        this.processor = () -> {
            //Empty
        };
    }

    public List<Pair> getStandardRandomBonuses() {
        return standardRandomBonuses;
    }

    public void setStandardRandomBonuses(final List<Pair> standardRandomBonuses) {
        this.standardRandomBonuses = standardRandomBonuses;
    }

    public final List<ProviderComponent<Integer>> getBonusGetterList() {
        return bonusGetterList;
    }
}