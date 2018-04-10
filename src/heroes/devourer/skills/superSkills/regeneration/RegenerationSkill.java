package heroes.devourer.skills.superSkills.regeneration;

import managment.actionManagement.service.components.handleComponet.HandleComponent;
import heroes.abstractHero.hero.Hero;
import heroes.abstractHero.skills.abstractSkill.AbstractSkill;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import managment.actionManagement.actions.ActionEvent;
import managment.actionManagement.actions.ActionType;
import managment.battleManagement.BattleManager;
import managment.playerManagement.Player;
import managment.playerManagement.PlayerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static heroes.devourer.skills.superSkills.regeneration.RegenerationPropertySkill.*;

public final class RegenerationSkill extends AbstractSkill {

    private static final Logger log = LoggerFactory.getLogger(RegenerationSkill.class);

    public RegenerationSkill(final ImageView sprite, final ImageView description, final List<Media> voiceList) {
        super(NAME, RELOAD, REQUIRED_LEVEL, getSkillCoefficients()
                , sprite, description, voiceList);
    }

    @Override
    public final void use(final BattleManager battleManager, final PlayerManager playerManager) {
        final Player currentPlayer = playerManager.getCurrentTeam().getCurrentPlayer();
        getEffect(currentPlayer, coefficients.get(0));
        actionManager.getEventEngine().addHandler(getHandlerInstance(currentPlayer));
        log.info("skill added");
    }

    private void getEffect(final Player currentPlayer, final double coefficient){
        final double HEALING = getParent().getTreatment() * coefficient;
        final Hero currentHero = currentPlayer.getCurrentHero();
        if (currentHero.getHealing(HEALING)){
            final ActionEvent actionEvent = new ActionEvent(null
                    , currentPlayer, "Regeneration: " + HEALING);
            actionEvents.add(actionEvent);
        }
    }

    private HandleComponent getHandlerInstance(final Player currentPlayer){
        return new HandleComponent() { // FIXME: 14.02.2018 make skillEventEngine

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
                    isWorking = false;
                    log.info("Second healing");
                }
            }

            @Override
            public final String getName() {
                return NAME;
            }

            @Override
            public final Player getCurrentPlayer() {
                return player;
            }

            @Override
            public final boolean isWorking() {
                return isWorking;
            }

            @Override
            public final void setWorking(boolean able) {
                this.isWorking = false;
            }
        };
    }

    @Override
    public final void showAnimation() {

    }
}
