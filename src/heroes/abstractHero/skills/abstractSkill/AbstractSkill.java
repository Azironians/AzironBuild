package heroes.abstractHero.skills.abstractSkill;

import heroes.abstractHero.hero.Hero;
import heroes.abstractHero.skills.Skill;
import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.util.Duration;
import managment.actionManagement.ActionManager;
import managment.actionManagement.actions.ActionEvent;
import managment.battleManagement.BattleManager;
import managment.playerManagement.PlayerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSkill implements Skill {

    private static final Logger log = LoggerFactory.getLogger(AbstractSkill.class);

    protected static final int START_OPACITY = 0;

    private final String name;

    protected int temp = 1;
    protected int reload;
    protected int requiredLevel;
    protected List<Double> coefficients;

    protected ImageView description;
    protected ImageView sprite;

    protected Pane container;
    private Media animationSound;
    private final List<Media> listOfVoices;

    //Access:
    private boolean skillAccess;

    //ActionEvents:
    protected final List<ActionEvent> actionEvents = new ArrayList<>();

    //Parent:
    protected Hero parent;

    //ActionManager:
    protected ActionManager actionManager;

    protected AbstractSkill(final String name, final int reload, final int requiredLevel, final List<Double> coefficients
            , final ImageView sprite, final ImageView description, final List<Media> listOfVoices) {
        this.name = name;
        this.reload = reload;
        this.requiredLevel = requiredLevel;
        this.coefficients = coefficients;
        this.skillAccess = true;

        sprite.setOnMouseEntered(event -> showDescription());
        sprite.setOnMouseExited(event -> hideDescription());
        sprite.setOnMouseClicked(event -> sendRequest());
        sprite.setVisible(false);
        this.description = description;
        this.sprite = sprite;
        this.listOfVoices = listOfVoices;
    }

    public void sendRequest() {
        if (parent != null) {
            log.info(" skill request");
            actionManager.setSkillRequest(parent, this);
        }
    }

    public abstract void use(final BattleManager battleManager, final PlayerManager playerManager);

    public void reload() {
        if (parent != null) {
            if (parent.getLevel() >= requiredLevel) {
                temp++;
            } else {
                temp = 1;
            }
        }
    }

    public void reset() {
        temp = temp % reload;
        if (sprite != null) {
            log.info("RESET_SKILL!!!!!!!!!");
            sprite.setVisible(false);
        }
    }

    private void showDescription() {
        log.info("show description");
        final FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), description);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    private void hideDescription() {
        log.info("hide description");
        final FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), description);
        fadeTransition.setToValue(0);
        fadeTransition.play();
    }

    public void install(final Pane parentPane, final Hero parent
            , final double spriteX, final double spriteY
            , final double descriptionX, final double descriptionY
            , final boolean invert) {
        this.parent = parent;
        this.description.setLayoutY(descriptionY); //-127
        this.description.setOpacity(START_OPACITY);
        //init sprite:
        final int inversion = invert ? -1 : 1;
        this.container = new Pane(){{
            setLayoutX(spriteX);
            setLayoutY(spriteY);
            setScaleX(inversion);
            final ObservableList<Node> elements = getChildren();
//            elements.add(description);
            elements.add(sprite);
        }};
        parentPane.getChildren().add(container);
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

    public final void setTemp(final int temp){
        this.temp = temp;
    }

    public int getReload() {
        return reload;
    }

    public void setReload(int reload) {
        this.reload = reload;
    }

    public Hero getParent() {
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

    public Pane getContainer() {
        return container;
    }
}
