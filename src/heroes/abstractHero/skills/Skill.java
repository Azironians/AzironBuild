package heroes.abstractHero.skills;

import heroes.abstractHero.hero.AHero;
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

    boolean isReady();

    void reload();

    void reset();

    void install(final Pane parentPane, final AHero parent, final double x, final double y, final boolean invert);

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

    int getReload();

    void setReload(int reload);

    AHero getParent();

    void setActionManager(final ActionManager actionManager);

    int getRequiredLevel();

    void setRequiredLevel(final int requiredLevel);
}
