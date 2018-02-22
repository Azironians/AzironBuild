package bonus.generalBonuses.bonuses;

import bonus.bonuses.Bonus;
import managment.actionManagement.service.components.HandleComponent;
import managment.actionManagement.service.engine.DynamicHandleService;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import managment.actionManagement.actions.ActionEvent;
import managment.actionManagement.actions.ActionType;
import managment.playerManagement.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class AAttack extends Bonus implements DynamicHandleService {

    private static final Logger log = LoggerFactory.getLogger(AAttack.class);

    public AAttack(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    private static final int ATTACK_BOOST = 10;

    @Override
    public final void use() {
        final Player currentPlayer = playerManager.getCurrentTeam().getCurrentPlayer();
        final Hero currentHero = currentPlayer.getHero();

        currentHero.setAttack(currentHero.getAttack() + ATTACK_BOOST);
        log.info("+10 ATTACK");
        final HandleComponent handler = getHandlerInstance();
        actionManager.getEventEngine().addHandler(handler);
    }

    @Override
    public final HandleComponent getHandlerInstance() {
        return new HandleComponent() {

            private Player player;

            private boolean isWorking = true;

            @Override
            public final void setup() {
                this.player = playerManager.getCurrentTeam().getCurrentPlayer();
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                final ActionType actionType = actionEvent.getActionType();
                if (actionType == ActionType.END_TURN) {
                    final Hero currentHero = player.getHero();
                    currentHero.setAttack(currentHero.getAttack() - ATTACK_BOOST);
                    isWorking = false;
                    log.info("-10 ATTACK");
                }
            }

            @Override
            public final String getName() {
                return "Attack!";
            }

            @Override
            public final Player getCurrentPlayer() {
                return player;
            }

            @Override
            public final boolean isWorking() {
                log.info("ATTACK BONUS: " + isWorking);
                return isWorking;
            }

            @Override
            public final void setAble(final boolean able) {
                this.isWorking = able;
            }
        };
    }
}
