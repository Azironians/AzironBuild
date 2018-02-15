package heroes.devourer.skills.superSkills;

import bonus.bonuses.HandlerBonus;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import heroes.abstractHero.hero.Hero;
import heroes.abstractHero.skills.swapSkills.AbstractSwapSkill;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import managment.actionManagement.actions.ActionEvent;
import managment.actionManagement.actions.ActionType;
import managment.battleManagement.BattleManager;
import managment.playerManagement.Player;
import managment.playerManagement.PlayerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

public final class RegenerationSkill extends AbstractSwapSkill {

    private static final Logger log = LoggerFactory.getLogger(RegenerationSkill.class);

    @Inject
    @Named("REGENERATION_NAME")
    private static final String NAME = "Regeneration";

    @Inject
    @Named("REGENERATION_RELOAD")
    private static int RELOAD;

    @Inject
    @Named("REGENERATION_REQUIRED_LEVEL")
    private static int REQUIRED_LEVEL;

    @Inject
    @Named("REGENERATION_HEALING_SKILL_COEFFICIENT")
    private static double HEALING_SKILL_COEFFICIENT;

    private static final List<Double> SKILL_COEFFICIENTS = Collections.singletonList(HEALING_SKILL_COEFFICIENT);

    public RegenerationSkill(final ImageView sprite, final ImageView description, final List<Media> voiceList) {
        super(NAME, RELOAD, REQUIRED_LEVEL, SKILL_COEFFICIENTS
                , sprite, description, voiceList);
    }

    @Override
    public final void use(final BattleManager battleManager, final PlayerManager playerManager) {
        final Player currentPlayer = playerManager.getCurrentTeam().getCurrentPlayer();
        getEffect(currentPlayer, coefficients.get(0));
        actionManager.getBonusEventEngine().addHandler(getHandlerInstance(currentPlayer));
        log.info("skill added");
    }

    private void getEffect(final Player currentPlayer, final double coefficient){
        final double HEALING = getParent().getTreatment() * coefficient;
        final Hero currentHero = currentPlayer.getHero();
        if (currentHero.getHealing(HEALING)){
            actionManager.getBonusEventEngine().handle();
        }
    }

    private HandlerBonus.GetAHandler getHandlerInstance(final Player currentPlayer){
        return new HandlerBonus.GetAHandler() { // FIXME: 14.02.2018 make skillEventEngine

            private Player player;

            private double coefficient;

            private boolean isWorking = true;

            @Override
            public final void setup() {
                this.player = currentPlayer;
                this.coefficient = coefficients.get(0);
                log.info("Setup was successful");
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                if (actionEvent.getActionType() == ActionType.START_TURN && actionEvent.getPlayer() == player){
                    getEffect(player, coefficient);
                    setAble(false);
                    log.info("Second healing");
                }
            }

            @Override
            public final String getName() {
                return "Regeneration skill";
            }

            @Override
            public final Player getPlayer() {
                return player;
            }

            @Override
            public final boolean isWorking() {
                return isWorking;
            }

            @Override
            public final void setAble(boolean able) {
                this.isWorking = false;
            }
        };
    }

    @Override
    public final void showAnimation() {

    }
}
