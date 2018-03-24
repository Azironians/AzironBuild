package bonus.devourerBonuses.bonuses;

import bonus.bonuses.Bonus;
import bonus.generalBonuses.bonuses.HStrengthenTheArmor;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import managment.actionManagement.actions.ActionEvent;
import managment.actionManagement.service.components.handleComponet.HandleComponent;
import managment.actionManagement.service.engine.services.DynamicHandleService;
import managment.playerManagement.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HRegeneratedTissues extends Bonus implements DynamicHandleService{

    private static final Logger log = LoggerFactory.getLogger(HStrengthenTheArmor.class);

    private static final double HEALING = 10.0;

    public HRegeneratedTissues(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final HandleComponent handler = getHandlerInstance();
        actionManager.getEventEngine().addHandler(handler);
        log.info("Regenerated tissues are activated");
    }

    @Override
    public final HandleComponent getHandlerInstance() {
        return new HandleComponent() {

            private Player player;

            private double hitPoints;

            private boolean isWorking = true;

            private boolean isAbleToHealing;

            @Override
            public final void setup() {
                this.player = playerManager.getCurrentTeam().getCurrentPlayer();
                this.hitPoints = player.getHero().getHitPoints();
                this.isAbleToHealing = false;
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                final Hero currentHero = player.getHero();
                final double hitPointsComparison = hitPoints - currentHero.getHitPoints();
                log.info("TISSUES HANDLE");
                if (!isAbleToHealing){
                    if (hitPointsComparison > 0 && currentHero.getHitPoints() <= 0) {
                        actionManager.getEventEngine().setRepeatHandling(true);
                    }
                } else {

                }


                log.info("COMPARISON: " + hitPointsComparison);
                currentHero.setHitPoints(currentHero.getHitPoints() + HEALING);
                log.info("ARMOR: " + ARMOR);



                this.hitPoints = currentHero.getHitPoints();
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

            }
        };
    }
}
