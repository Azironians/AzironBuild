package heroes.abstractHero.skills.swapSkills;

import heroes.abstractHero.hero.AHero;
import heroes.abstractHero.skills.Skill;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import managment.actionManagement.ActionManager;
import managment.actionManagement.actions.ActionEvent;
import managment.battleManagement.BattleManager;
import managment.playerManagement.PlayerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSwapSkill implements Skill {

    private final Logger log = LoggerFactory.getLogger(AbstractSwapSkill.class);

    private final String name;

    protected int temp = 1;
    protected int reload;
    protected int requiredLevel;
    protected List<Double> coefficients;

    private ImageView pictureOfDescription;
    private ImageView sprite;
    private Media animationSound;
    private final List<Media> listOfVoices;

    //Access:
    private boolean skillAccess;

    //ActionEvents:
    protected final List<ActionEvent> actionEvents = new ArrayList<>();

    //Parent:
    protected AHero parent;

    //ActionManager:
    protected ActionManager actionManager;

    //Swap skill:
    protected AbstractSwapSkill(final int reload, final List<Double> coefficients, final List<Media> voiceList) {
        this.name = "swap";
        this.reload = reload;
        this.requiredLevel = 1;
        this.coefficients = coefficients;
        this.skillAccess = true;

        this.listOfVoices = voiceList;
    }

    public abstract void use(final BattleManager battleManager, final PlayerManager playerManager);

    @Override
    public final void reload() {
        if (temp + 1 <= reload){
            log.info("TEMP:" + temp);
            temp++;
        }
    }

    @Override
    public final void reset() {
        temp = 1;
    }

    @Override
    public void install(Pane parentPane, AHero parent, double x, double y, boolean invert) {

    }

    public abstract void showAnimation();

    public final List<ActionEvent> getActionEvents() {
        return actionEvents;
    }

    public final List<Double> getCoefficients() {
        return coefficients;
    }

    public ImageView getSprite() {
        return sprite;
    }

    public String getName() {
        return name;
    }

    public void setCoefficients(List<Double> coefficients) {
        this.coefficients = coefficients;
    }

    public final List<Media> getListOfVoices() {
        return listOfVoices;
    }

    public final Media getAnimationSound() {
        return animationSound;
    }

    public boolean isSkillAccess() {
        return skillAccess;
    }

    public void setSkillAccess(boolean skillAccess) {
        this.skillAccess = skillAccess;
    }

    public int getTemp() {
        return temp;
    }

    public int getReload() {
        return reload;
    }

    public void setReload(int reload) {
        this.reload = reload;
    }

    public AHero getParent() {
        return parent;
    }

    public void setActionManager(final ActionManager actionManager) {
        this.actionManager = actionManager;
    }

    public final int getRequiredLevel() {
        return requiredLevel;
    }

    public final void setRequiredLevel(final int requiredLevel) {
        this.requiredLevel = requiredLevel;
    }
}
