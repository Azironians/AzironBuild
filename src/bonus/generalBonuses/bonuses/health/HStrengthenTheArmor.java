package bonus.generalBonuses.bonuses.health;

import bonus.bonuses.Bonus;
import managment.actionManagement.service.components.handleComponet.HandleComponent;
import managment.actionManagement.service.engine.services.DynamicHandleService;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import managment.actionManagement.actions.ActionEvent;
import managment.actionManagement.actions.ActionType;
import managment.playerManagement.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HStrengthenTheArmor extends Bonus implements DynamicHandleService {

    private static final Logger log = LoggerFactory.getLogger(HStrengthenTheArmor.class);

    private static final double ARMOR_COEFFICIENT = 0.4;

    public HStrengthenTheArmor(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final HandleComponent handler = getHandlerInstance();
        actionManager.getEventEngine().addHandler(handler);
        log.info("ARMOR UP");
    }

    @Override
    public final HandleComponent getHandlerInstance() {
        return new HandleComponent() {

            private Player player;

            private Player alternativePlayer;

            private double hitPoints;

            private double healthSupply;

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
                final double healthSupplyComparison = healthSupply - currentHero
                        .getHitPoints();
                log.info("ARMOR HANDLE");
                if (hitPointsComparison > 0) {
                    log.info("COMPARISON: " + hitPointsComparison);
                    final double ARMOR = hitPointsComparison * ARMOR_COEFFICIENT;
                    currentHero.setHitPoints(currentHero.getHitPoints() + ARMOR);
                    log.info("ARMOR: " + ARMOR);
                    if (healthSupplyComparison > 0) {
                        final double supplyArmor = healthSupplyComparison
                                * ARMOR_COEFFICIENT;
                        currentHero.setHealthSupply(currentHero.getHealthSupply()
                                + supplyArmor);
                    }
                }
                this.healthSupply = currentHero.getHealthSupply();
                this.hitPoints = currentHero.getHitPoints();
                if (actionEvent.getActionType() == ActionType.START_TURN
                        && (actionEvent.getPlayer() == player || actionEvent.getPlayer() == alternativePlayer)) {
                    isWorking = false;
                    log.info("ARMOR DOWN");
                }
            }

            @Override
            public final String getName() {
                return "StrengthenTheArmor";
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
