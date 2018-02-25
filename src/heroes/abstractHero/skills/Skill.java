package heroes.abstractHero.skills;

import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import managment.actionManagement.ActionManager;
import managment.actionManagement.actions.ActionEvent;
import managment.battleManagement.BattleManager;
import managment.playerManagement.PlayerManager;

import java.util.List;

public interface Skill {

    void use(final BattleManager battleManager, final PlayerManager playerManager);

    default boolean isReady(){
        return getTemp() >= getReload() && getParent().getLevel() >= getRequiredLevel();
    }

    void reload();

    void reset();

    void install(final Pane parentPane, final Hero parent
            , final double spriteX, final double spriteY
            , final double descriptionX, final double descriptionY
            , final boolean invert);

    //Getters & setters:
    void showAnimation();

    List<ActionEvent> getActionEvents();

    List<Double> getCoefficients();

    ImageView getSprite();

    String getName();

    void setCoefficients(List<Double> coefficients);

    List<Media> getListOfVoices();

    Media getAnimationSound();

    boolean isSkillAccess();

    void setSkillAccess(boolean skillAccess);

    int getTemp();

    void setTemp(final int temp);

    int getReload();

    void setReload(int reload);

    Hero getParent();

    void setActionManager(final ActionManager actionManager);

    int getRequiredLevel();

    void setRequiredLevel(final int requiredLevel);
}
