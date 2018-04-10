package bonus.devourerBonuses.bonuses.health;

import bonus.bonuses.Bonus;
import bonus.generalBonuses.bonuses.health.HStrengthenTheArmor;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import managment.actionManagement.actions.ActionEvent;
import managment.actionManagement.service.components.handleComponet.HandleComponent;
import managment.actionManagement.service.components.handleComponet.IllegalSwitchOffHandleComponentException;
import managment.actionManagement.service.engine.services.RegularHandleService;
import managment.playerManagement.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HRegeneratedTissues extends Bonus implements RegularHandleService {

    private static final Logger log = LoggerFactory.getLogger(HStrengthenTheArmor.class);

    private static final double HEALING = 10.0;

    private HandleComponent handleComponent;

    public HRegeneratedTissues(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        try {
            handleComponent.setWorking(true);
            log.info("Regenerated tissues are activated");
        } catch (final IllegalSwitchOffHandleComponentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public HandleComponent getRegularHandlerInstance(final Player player) {
        return new HandleComponent() {

            private Player currentPlayer;

            private double hitPoints;

            private double healing;

            @Override
            public final void setup() {
                this.currentPlayer = player;
                this.hitPoints = currentPlayer.getCurrentHero().getHitPoints();
                this.healing = 0;
                handleComponent = this;
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                final Hero hero = currentPlayer.getCurrentHero();
                if (this.healing > 0) {
                    log.info("TISSUES HANDLE");
                    final double hitPointsComparison = hitPoints - hero.getHitPoints();
                    if (hitPointsComparison > 0 && hero.getHitPoints() <= 0) {
                        if (hero.getHealing(this.healing)) {
                            actionManager.getEventEngine().setRepeatHandling(true);
                        }
                        log.info("COMPARISON: " + hitPointsComparison);
                        log.info("HP: " + hero.getHitPoints());
                    }
                }
                this.hitPoints = hero.getHitPoints();
            }

            @Override
            public final String getName() {
                return "RegeneratedTissues";
            }

            @Override
            public final Player getCurrentPlayer() {
                return currentPlayer;
            }

            @Override
            public final boolean isWorking() {
                return true;
            }

            @Override
            public final void setWorking(final boolean able) {
                if (able) {
                    this.healing += HEALING;
                } else {
                    this.healing -= HEALING;
                }
            }
        };
    }
}