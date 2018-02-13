package gui.animation;

import com.google.inject.Singleton;
import heroes.abstractHero.hero.AHero;
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
            final AHero hero = player.getHero();
            final int level = hero.getLevel();
            if (level - 1 < 9){
                player.getLocation().setRequiredExperience(hero.getListOfRequiredExperience().get(level - 1).intValue());
            } else {
                player.getLocation().setRequiredExperience("");
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
