package managment.playerManagement;

import com.google.inject.Inject;
import heroes.abstractHero.AHero;
import managment.battleManagement.BattleManager;
import org.jetbrains.annotations.Contract;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public final class PlayerManager {

    @Inject
    private BattleManager battleManager;

    private Map<String, Player> mapOfPlayers = null;

    private int countPlayers = 0;

    private ATeam rightATeam;

    private ATeam leftATeam;

    private ATeam currentATeam;

    private ATeam opponentATeam;

    //Setters:
    public final void setPlayerCount(int countPlayers) {
        this.countPlayers = countPlayers;
        this.mapOfPlayers = new HashMap<>(countPlayers);
    }

    public final void setStartPosition(){
        final Random random = new Random();
        if (random.nextBoolean()) {
            currentATeam = rightATeam;
            opponentATeam = leftATeam;
        } else {
            currentATeam = leftATeam;
            opponentATeam = rightATeam;
        }
        currentATeam.setTurn(0);
        opponentATeam.setTurn(1);
        setAdditionalExperience(opponentATeam);
    }

    public final void start(){
        currentATeam.launchTimer();
        battleManager.loadRandomBonuses(currentATeam.getCurrentPlayer().getHero());
    }

    private void setAdditionalExperience(final ATeam team){
        final AHero hero = team.getCurrentPlayer().getHero();
        final double equalsAttack =  hero.getAttack();
        hero.addExperience(equalsAttack);
    }

    //Getters:
    @Contract(pure = true)
    public final Map<String, Player> getMapOfPlayers() {
        return mapOfPlayers;
    }

    @Contract(pure = true)
    public final int getCountPlayers() {
        return this.countPlayers;
    }

    @Contract(pure = true)
    public final ATeam getRightATeam() {
        return rightATeam;
    }

    @Contract(pure = true)
    public final ATeam getLeftATeam() {
        return leftATeam;
    }

    @Contract(pure = true)
    public final ATeam getCurrentTeam() {
        return currentATeam;
    }

    @Contract(pure = true)
    public final ATeam getOpponentATeam() {
        return opponentATeam;
    }

    public final void setCurrentATeam(ATeam currentATeam) {
        currentATeam.launchTimer();
        this.currentATeam = currentATeam;
    }

    public final void setOpponentATeam(ATeam opponentATeam) {
        opponentATeam.pauseTimer();
        this.opponentATeam = opponentATeam;
    }

    public final void setRightATeam(final ATeam rightATeam) {
        this.rightATeam = rightATeam;
    }

    public final void setLeftATeam(final ATeam leftATeam) {
        this.leftATeam = leftATeam;
    }
}
