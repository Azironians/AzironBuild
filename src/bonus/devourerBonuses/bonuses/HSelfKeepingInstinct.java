package bonus.devourerBonuses.bonuses;

import bonus.bonuses.Bonus;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import managment.actionManagement.actions.ActionEvent;
import managment.actionManagement.actions.ActionType;
import managment.actionManagement.service.components.HandleComponent;
import managment.actionManagement.service.engine.DynamicHandleService;
import managment.playerManagement.ATeam;
import managment.playerManagement.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HSelfKeepingInstinct extends Bonus implements DynamicHandleService {

    private static final Logger log = LoggerFactory.getLogger(HSelfKeepingInstinct.class);

    private static final double HEALING = 10;

    public HSelfKeepingInstinct(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final HandleComponent handleComponent = getHandlerInstance();
        actionManager.getEventEngine().addHandler(handleComponent);
        log.info(name + " is activated");
    }

    @Override
    public final HandleComponent getHandlerInstance() {
        return new HandleComponent() {

            private Player player;

            private ATeam opponentTeam;

            private boolean isWorking;

            @Override
            public final void setup() {
                this.player = playerManager.getCurrentTeam().getCurrentPlayer();
                this.opponentTeam = playerManager.getOpponentATeam();
                this.isWorking = true;
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                final ActionType actionType = actionEvent.getActionType();
                final Player opponent = actionEvent.getPlayer();
                if (opponent == opponentTeam.getCurrentPlayer()
                        && actionType == ActionType.USED_SKILL){
                    if (player.getHero().getHealing(HEALING)){
                        log.info("+10 HP");
                        actionManager.getEventEngine().handle();
                    }
                }
            }

            @Override
            public final String getName() {
                return "SelfKeepingInstinct";
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
            public final void setAble(final boolean able) {
                throw new UnsupportedOperationException();
            }
        };
    }
}
