package bonus.devourerBonuses.bonuses.attack;

import bonus.bonuses.Bonus;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import managment.actionManagement.actions.ActionEvent;
import managment.actionManagement.actions.ActionType;
import managment.actionManagement.service.components.handleComponet.HandleComponent;
import managment.actionManagement.service.engine.services.DynamicHandleService;
import managment.playerManagement.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AEvilBrassKnuckles extends Bonus implements DynamicHandleService{

    private static final Logger log = LoggerFactory.getLogger(AEvilBrassKnuckles.class);

    public AEvilBrassKnuckles(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    private static final double ATTACK_BOOST_COEFFICIENT = 0.15;

    @Override
    public final void use() {
        final Player currentPlayer = playerManager.getCurrentTeam().getCurrentPlayer();
        final Hero currentHero = currentPlayer.getCurrentHero();

        actionManager.getEventEngine().addHandler(getHandlerInstance());
        currentHero.setAttack(currentHero.getAttack() * (1 + ATTACK_BOOST_COEFFICIENT));
        log.info("+15% BEFORE_ATTACK");
    }

    @Override
    public final HandleComponent getHandlerInstance() {
        return new HandleComponent() {

            private Player player;

            private double delta;

            private boolean isWorking = true;

            @Override
            public final void setup() {
                this.player = playerManager.getCurrentTeam().getCurrentPlayer();
                this.delta = player.getCurrentHero().getAttack() * ATTACK_BOOST_COEFFICIENT;
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                final ActionType actionType = actionEvent.getActionType();
                if (actionType == ActionType.END_TURN) {
                    final Hero currentHero = player.getCurrentHero();
                    currentHero.setAttack(currentHero.getAttack() - delta);
                    isWorking = false;
                    log.info("-15% BEFORE_ATTACK");
                    actionManager.getEventEngine().setRepeatHandling(true);
                }
            }

            @Override
            public final String getName() {
                return "EvilBrassKnuckles";
            }

            @Override
            public final Player getCurrentPlayer() {
                return player;
            }

            @Override
            public final boolean isWorking() {
                log.info("BEFORE_ATTACK BONUS: " + isWorking);
                return isWorking;
            }

            @Override
            public final void setWorking(final boolean able) {
                this.isWorking = able;
            }
        };
    }
}