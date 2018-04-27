package bonus.devourerBonuses.bonuses.attack;

import bonus.bonuses.Bonus;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import managment.actionManagement.actions.ActionEvent;
import managment.actionManagement.actions.ActionEventFactory;
import managment.actionManagement.service.components.handleComponet.HandleComponent;
import managment.actionManagement.service.engine.services.DynamicHandleService;
import managment.playerManagement.ATeam;
import managment.playerManagement.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AEruption extends Bonus implements DynamicHandleService{

    private static final Logger log = LoggerFactory.getLogger(AEruption.class);

    private static final double DAMAGE = 10;

    public AEruption(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final HandleComponent handler = getHandlerInstance();
        actionManager.getEventEngine().addHandler(handler);
        log.info("ERUPTION IS ACTIVATED");
    }

    @Override
    public final HandleComponent getHandlerInstance() {
        return new HandleComponent() {

            private Player player;

            private ATeam opponentTeam;

            private double hitPoints;

            private boolean isWorking = true;

            @Override
            public final void setup() {
                this.player = playerManager.getCurrentTeam().getCurrentPlayer();
                this.hitPoints = player.getCurrentHero().getHitPoints();
                this.opponentTeam = playerManager.getOpponentTeam();
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                final Hero currentHero = player.getCurrentHero();
                final double comparison = hitPoints - currentHero.getHitPoints();
                if (comparison > 0 && currentHero.getHitPoints() < 0) {
                    log.info("COMPARISON: " + comparison);
                    final Player opponentPlayer = opponentTeam.getCurrentPlayer();
                    if (opponentPlayer.getCurrentHero().getDamage(DAMAGE)){
                        actionManager.getEventEngine().handle(ActionEventFactory.getAfterDealDamage(player
                                , opponentPlayer.getCurrentHero(), DAMAGE));
                    }
                }
                this.hitPoints = currentHero.getHitPoints();
            }

            @Override
            public final String getName() {
                return "Eruption";
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
            public final void setWorking(final boolean able) {
                this.isWorking = able;
            }
        };
    }
}