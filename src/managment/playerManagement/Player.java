package managment.playerManagement;

import annotations.sourceAnnotations.Transcendental;
import bonus.bonuses.Bonus;
import gui.service.locations.ALocation;
import heroes.abstractHero.hero.Hero;
import managment.profileManagement.Profile;
import org.jetbrains.annotations.Contract;

import java.util.List;

public final class Player {

    private final Profile profile;

    private Hero currentHero;

    @Transcendental
    private List<Hero> allHeroes;

    private boolean isCurrent;

    private boolean isAlive;

    private double dealDamage = 0;
    private double restoredHitPoints = 0;
    private int reachedLevel = 1;
    private int usedSkills = 0;
    private Bonus favouriteBonus;
    private int remainingTime;
    private Boolean winner = null;

    private ALocation location;


    public Player(final Profile profile, final Hero currentHero) {
        this.profile = profile;
        this.currentHero = currentHero;
        this.isAlive = true;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    @Contract(pure = true)
    public Profile getProfile() {
        return profile;
    }

    @Contract(pure = true)
    public Hero getCurrentHero() {
        return currentHero;
    }

    public void setCurrentHero(Hero currentHero) {
        this.currentHero = currentHero;
    }

    @Contract(pure = true)
    public double getDealDamage() {
        return dealDamage;
    }

    void setDealDamage(int dealDamage) {
        this.dealDamage = dealDamage;
    }

    @Contract(pure = true)
    public double getRestoredHitPoints() {
        return restoredHitPoints;
    }

    void setRestoredHitPoints(int restoredHitPoints) {
        this.restoredHitPoints = restoredHitPoints;
    }

    @Contract(pure = true)
    public int getReachedLevel() {
        return reachedLevel;
    }

    void setReachedLevel(byte reachedLevel) {
        this.reachedLevel = reachedLevel;
    }

    @Contract(pure = true)
    public int getUsedSkills() {
        return usedSkills;
    }

    void setUsedSkills(byte usedSkills) {
        this.usedSkills = usedSkills;
    }

    @Contract(pure = true)
    public Bonus getFavouriteBonus() {
        return favouriteBonus;
    }

    public void setFavouriteBonus(Bonus favouriteBonus) {
        this.favouriteBonus = favouriteBonus;
    }

    @Contract(pure = true)
    public int getRemainingTime() {
        return remainingTime;
    }

    void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    @Contract(pure = true)
    public boolean isWinner() {
        return winner;
    }

    void setWinner(boolean winner) {
        this.winner = winner;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }

    @Transcendental
    public final List<Hero> getAllHeroes() {
        return allHeroes;
    }
}




