package bonus.devourerBonuses.bonuses;

import bonus.bonuses.Bonus;
import javafx.scene.image.ImageView;
import managment.actionManagement.actions.ActionEvent;
import managment.actionManagement.actions.ActionType;
import managment.actionManagement.service.components.HandleComponent;
import managment.actionManagement.service.engine.DynamicHandleService;
import managment.playerManagement.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ABurnOfKron extends Bonus implements DynamicHandleService{

    private static final Logger log = LoggerFactory.getLogger(ABurnOfKron.class);

    private static final double DAMAGE_COEFFICIENT = 0.05;

    private static final int TURNS = 5;

    public ABurnOfKron(String name, int id, ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final HandleComponent handler = getHandlerInstance();
        actionManager.getEventEngine().addHandler(handler);
//        log.info("EXPERIENCE IS INCREASED BY 10% IN DURING 3 TURNS");
    }

    @Override
    public final HandleComponent getHandlerInstance() {
        return new HandleComponent() {

            private int count = TURNS;

            private Player player;

            private Player opponentPlayer;

            private double damage;

            @Override
            public final void setup() {
                this.player = playerManager.getCurrentTeam().getCurrentPlayer();
                this.damage = player.getHero().getAttack() * DAMAGE_COEFFICIENT;
                this.opponentPlayer = playerManager.getOpponentATeam().getCurrentPlayer();
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                if (actionEvent.getActionType() == ActionType.START_TURN && actionEvent.getPlayer()
                        .equals(opponentPlayer)){
                    if (opponentPlayer.getHero().getDamage(damage)) {
                        actionManager.getEventEngine().handle();
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
                return "BurnOfKron";
            }

            @Override
            public final Player getCurrentPlayer() {
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