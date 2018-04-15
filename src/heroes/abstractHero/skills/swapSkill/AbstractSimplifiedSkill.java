package heroes.abstractHero.skills.swapSkill;

import heroes.abstractHero.hero.Hero;
import heroes.abstractHero.skills.abstractSkill.AbstractSkill;
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

public abstract class AbstractSimplifiedSkill extends AbstractSkill {

    private final Logger log = LoggerFactory.getLogger(AbstractSimplifiedSkill.class);

    //ActionEvents:
    protected final List<ActionEvent> actionEvents = new ArrayList<>();

    //ActionManager:
    protected ActionManager actionManager;

    protected AbstractSimplifiedSkill(String name, int reload, int requiredLevel, List<Double> coefficients, ImageView sprite, ImageView pictureOfDescription, List<Media> listOfVoices) {
        super(name, reload, requiredLevel, coefficients, sprite, pictureOfDescription, listOfVoices);
    }

    //Swap skill:
    public abstract void use(final BattleManager battleManager, final PlayerManager playerManager);

    @Override
    public final void reload() {
        if (temp + 1 <= reload) {
            log.info("TEMP:" + temp);
            temp++;
        }
    }

    @Override
    public void sendRequest(){}

    public final void install(final Pane parentPane, final Hero parent
            , final double spriteX, final double spriteY
            , final double descriptionX, final double descriptionY
            , final boolean invert) {
        this.parent = parent;
        //init description:
        this.description.setLayoutY(descriptionY); //-127
        this.description.setOpacity(START_OPACITY);
        //init sprite:
        final int inversion = invert ? 1 : -1;
        sprite.setScaleX(inversion);
        sprite.setVisible(true);
        sprite.setOpacity(1);
        parentPane.getChildren().add(sprite);
    }

    @Override
    public final void reset() {
        temp = 1;
    }

    public abstract void showAnimation();
}
