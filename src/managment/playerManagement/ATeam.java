package managment.playerManagement;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import org.jetbrains.annotations.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ATeam {
    private static final Logger log = LoggerFactory.getLogger(ATeam.class);


    private Player alternativePlayer;
    private int turn;

    private int time;

    private final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> time--));

    ATeam(){}

    ATeam(final Player currentPlayer, final Player alternativePlayer) {
        currentPlayer.setCurrent(true);
        this.currentPlayer = currentPlayer;
        alternativePlayer.setCurrent(false);
        this.alternativePlayer = alternativePlayer;
    }

    private boolean swapAccess = true;

    public final boolean swapPlayers(){
        if (swapAccess && currentPlayer.getHero().getHitPoints() > 0){
            eagerSwapPlayers();
            return true;
        }
        return false;
    }

    public void eagerSwapPlayers(){
            final Player swapper = currentPlayer;
            currentPlayer = alternativePlayer;
            alternativePlayer = swapper;

            currentPlayer.setCurrent(true);
            alternativePlayer.setCurrent(false);
            log.info("Swap was successful");
    }

    public final void launchTimer(){
        timeline.play();
    }

    public final void pauseTimer(){
        timeline.pause();
    }

    @Contract(pure = true)
    public final Player getCurrentPlayer() {
        return currentPlayer;
    }

    @Contract(pure = true)
    public final Player getAlternativePlayer() {
        return alternativePlayer;
    }

    @Contract(pure = true)
    public final int getTurn() {
        return turn;
    }

    public final void setTurn(final int turn) {
        this.turn = turn;
    }

    public int getTime() {
        return time;
    }

    public void setTime(final int time) {
        this.time = time;
        this.timeline.setCycleCount(time);
    }

    public final Timeline getTimeline() {
        return timeline;
    }

    public final boolean isSwapAccess() {
        return swapAccess;
    }

    public final void setSwapAccess(boolean swapAccess) {
        this.swapAccess = swapAccess;
    }

    @Override
    public final String toString() {
        return "ATeam{" +
                "currentPlayer=" + currentPlayer.getProfile().getName() +
                ", alternativePlayer=" + alternativePlayer.getProfile().getName() +
                ", turn=" + turn +
                '}';
    }

    public void setCurrentPlayer(Player currentPlayer) {
        currentPlayer.setCurrent(true);
        this.currentPlayer = currentPlayer;
    }

    private Player currentPlayer;

    public void setAlternativePlayer(Player alternativePlayer) {
        alternativePlayer.setCurrent(false);
        this.alternativePlayer = alternativePlayer;
    }

}
