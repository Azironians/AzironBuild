package heroes.abstractHero.skills.swapSkills;

import heroes.abstractHero.hero.AHero;
import heroes.abstractHero.skills.Skill;
import heroes.abstractHero.skills.superSkills.AbstractSkill;
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

public abstract class AbstractSwapSkill extends AbstractSkill{

    private final Logger log = LoggerFactory.getLogger(AbstractSwapSkill.class);

    //Access:
    private boolean skillAccess;

    //ActionEvents:
    protected final List<ActionEvent> actionEvents = new ArrayList<>();

    //Parent:
    protected AHero parent;

    //ActionManager:
    protected ActionManager actionManager;

    protected AbstractSwapSkill(String name, int reload, int requiredLevel, List<Double> coefficients, ImageView sprite, ImageView pictureOfDescription, List<Media> listOfVoices) {
        super(name, reload, requiredLevel, coefficients, sprite, pictureOfDescription, listOfVoices);
    }

    //Swap skill:


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

    public abstract void showAnimation();
}
