package bonus.general;

import bonus.bonuses.Bonus;
import bonus.bonuses.HandlerBonus;
import javafx.scene.image.ImageView;
import managment.actionManagement.actions.ActionEvent;
import managment.actionManagement.actions.ActionType;
import managment.playerManagement.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class XStepByStep extends Bonus implements HandlerBonus {

    private static final Logger log = LoggerFactory.getLogger(XStepByStep.class);

    private static final double EXPERIENCE_COEFFICIENT = 0.1;

    public XStepByStep(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final GetAHandler handler = getHandlerInstance();
        actionManager.getBonusEventEngine().addHandler(handler);
        log.info("EXPERIENCE IS INCREASED BY 10% IN DURING 3 TURNS");
    }

    @Override
    public final GetAHandler getHandlerInstance() {
        return new GetAHandler() {

            private int count = 3;

            private Player player;

            private double experience;

            @Override
            public final void setup() {
                this.player = playerManager.getCurrentTeam().getCurrentPlayer();
                this.experience = player.getHero().getCurrentExperience();
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                final double newExperience = player.getHero().getCurrentExperience();
                final double comparison = newExperience - experience;
                if (comparison > 0) {
                    log.info("EXPERIENCE WAS INCREASED");
                    final double EXPERIENCE_BOOST = comparison * EXPERIENCE_COEFFICIENT;
                    if (player.getHero().addExperience(EXPERIENCE_BOOST)) {
                        log.info("EXPERIENCE WAS ADDED BY 10%");
                        this.experience = player.getHero().getCurrentExperience();
                        actionManager.getBonusEventEngine().handle();
                    }
                }
                if (actionEvent.getActionType() == ActionType.END_TURN && actionEvent.getPlayer() == player) {
                    if (isWorking()) {
                        count--;
                        log.info("COUNTDOWN: " + count);
                    }
                }
            }

            @Override
            public final String getName() {
                return "StepByStep";
            }

            @Override
            public final Player getPlayer() {
                return player;
            }

            @Override
            public final boolean isWorking() {
                return count > 0;
            }

            @Override
            public final void setAble(final boolean able) {
                throw new UnsupportedOperationException();
            }
        };
    }
}
