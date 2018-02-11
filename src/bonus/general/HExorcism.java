package bonus.general;

import bonus.bonuses.Bonus;
import bonus.bonuses.HandlerBonus;
import heroes.abstractHero.AHero;
import javafx.scene.image.ImageView;
import managment.actionManagement.actions.ActionEvent;
import managment.actionManagement.actions.ActionType;
import managment.playerManagement.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HExorcism extends Bonus implements HandlerBonus {

    private static final Logger log = LoggerFactory.getLogger(HExorcism.class);

    private static final double HEALING_BOOST = 2;

    public HExorcism(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final GetAHandler handler = getHandlerInstance();
        actionManager.getBonusEventEngine().addHandler(handler);
    }

    @Override
    public final GetAHandler getHandlerInstance() {
        return new GetAHandler() {

            private boolean isWorking = true;

            private Player player;

            @Override
            public final void setup() {
                this.player = playerManager.getCurrentTeam().getCurrentPlayer();
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                final ActionType actionType = actionEvent.getActionType();

                if (actionType == ActionType.END_TURN && player == actionEvent.getPlayer()) {
                    final AHero currentHero = player.getHero();

                    if (currentHero.getHealing(HEALING_BOOST)) {
                        log.info("+2 HP");
                        actionManager.getBonusEventEngine().handle();
                    }
                }
            }

            @Override
            public final String getName() {
                return "Exorcism";
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
            public final void setAble(final boolean able) {
                this.isWorking = able;
            }
        };
    }
}
