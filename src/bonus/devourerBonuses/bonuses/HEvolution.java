package bonus.devourerBonuses.bonuses;

import bonus.bonuses.Bonus;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import managment.actionManagement.actions.ActionEvent;
import managment.actionManagement.service.components.HandleComponent;
import managment.actionManagement.service.engine.DynamicHandleService;
import managment.playerManagement.Player;

public final class HEvolution extends Bonus implements DynamicHandleService {

    private static final double BOOST_COEFFICIENT = 1.03;

    public HEvolution(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final HandleComponent handleComponent = getHandlerInstance();
        actionManager.getEventEngine().addHandler(handleComponent);
    }

    @Override
    public final HandleComponent getHandlerInstance() {
        return new HandleComponent() {

            private Player player;

            private boolean isWorking;

            private double hitPoints;

            @Override
            public final void setup() {
                this.player = playerManager.getCurrentTeam().getCurrentPlayer();
                this.hitPoints = player.getHero().getHitPoints();
                this.isWorking = true;
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                if (player == actionEvent.getPlayer()) {
                    final Hero hero = player.getHero();
                    final double newHitPoints = hero.getHitPoints();
                    if (actionEvent.getMessage().equals("Regeneration skill")
                            && hitPoints < newHitPoints) {
                        final double healthSupplyBoost = newHitPoints - hitPoints
                                * BOOST_COEFFICIENT;
                        hero.setHealthSupply(hero.getHealthSupply() + healthSupplyBoost);
                        actionManager.getEventEngine().handle();
                    }
                    this.hitPoints = newHitPoints;
                }
            }

            @Override
            public final String getName() {
                return "Evolution";
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

            }
        };
    }
}
