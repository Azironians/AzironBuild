package gui.animation;

import com.google.inject.Singleton;
import gui.service.locations.ALocation;
import heroes.abstractHero.hero.Hero;
import managment.playerManagement.Player;

@Singleton
public final class MainAnimator {

    public final void showLowHitPoint(final Player player){
        if (player.isCurrent()){

        } else {

        }
    }

    public final void showLevelUp(final Player player){
        if (player.isCurrent()){
            final Hero hero = player.getCurrentHero();
            final int level = hero.getLevel();
            final ALocation location = player.getCurrentHero().getLocation();
            if (level - 1 < 9){
                location.setRequiredExperience(hero.getListOfRequiredExperience().get(level - 1).intValue());
            } else {
                location.setRequiredExperience("");
            }
        } else {

        }
    }

    public final void showLevelDown(final Player player){
        if (player.isCurrent()){

        } else {

        }
    }
}
