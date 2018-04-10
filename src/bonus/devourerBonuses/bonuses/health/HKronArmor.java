package bonus.devourerBonuses.bonuses.health;

import bonus.bonuses.Bonus;
import bonus.generalBonuses.bonuses.health.HStrengthenTheArmor;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import managment.actionManagement.actions.ActionEvent;
import managment.actionManagement.actions.ActionType;
import managment.actionManagement.service.components.handleComponet.HandleComponent;
import managment.actionManagement.service.engine.services.DynamicHandleService;
import managment.playerManagement.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HKronArmor extends Bonus implements DynamicHandleService{

    public HKronArmor(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    private static final Logger log = LoggerFactory.getLogger(HStrengthenTheArmor.class);

    private static final double HEALING = 120.0;

    private static final double BIG_DAMAGE = 150;

    @Override
    public final void use() {
        final HandleComponent handler = getHandlerInstance();
        actionManager.getEventEngine().addHandler(handler);
        log.info("KRON ARMOR IS ACTIVATED");
    }

    @Override
    public final HandleComponent getHandlerInstance() {
        return new HandleComponent() {

            private Player player;

            private Player alternativePlayer;

            private double hitPoints;

            private boolean isWorking = true;

            @Override
            public final void setup() {
                this.player = playerManager.getCurrentTeam().getCurrentPlayer();
                this.hitPoints = player.getCurrentHero().getHitPoints();
                this.alternativePlayer = playerManager.getCurrentTeam().getAlternativePlayer();
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                final Hero currentHero = player.getCurrentHero();
                final double hitPointsComparison = hitPoints - currentHero.getHitPoints();
                log.info("ARMOR HANDLE");
                final boolean isNecessaryHealing = hitPointsComparison > BIG_DAMAGE;
                if (isNecessaryHealing) {
                    log.info("COMPARISON: " + hitPointsComparison);
                    if (currentHero.getHealing(HEALING)){
                        actionManager.getEventEngine().setRepeatHandling(true);
                    }
                }
                this.hitPoints = currentHero.getHitPoints();
                if (actionEvent.getActionType() == ActionType.START_TURN
                        && (actionEvent.getPlayer() == player
                        || actionEvent.getPlayer() == alternativePlayer)) {
                    isWorking = false;
                    log.info("ARMOR DOWN");
                }
            }

            @Override
            public final String getName() {
                return "KronArmor";
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