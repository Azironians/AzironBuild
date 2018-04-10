package bonus.lvBonuses.bonuses.health;

import bonus.bonuses.Bonus;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import managment.actionManagement.actions.ActionEvent;
import managment.actionManagement.service.components.handleComponet.HandleComponent;
import managment.actionManagement.service.engine.services.DynamicHandleService;
import managment.playerManagement.Player;

public final class HRegenerationAmulet extends Bonus implements DynamicHandleService {

    private static final double HEALING_BOOST = 75;

    public HRegenerationAmulet(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        actionManager.getEventEngine().addHandler(getHandlerInstance());
    }

    @Override
    public final HandleComponent getHandlerInstance() {
        return new HandleComponent() {

            private Player player;

            private boolean isWorking;

            private boolean isUpgradedHealing;

            @Override
            public final void setup() {
                this.player = playerManager.getCurrentTeam().getCurrentPlayer();
                this.isWorking = true;
                this.isUpgradedHealing = true;
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                if (actionEvent.getPlayer() == player) {
                    final Hero hero = player.getCurrentHero();
                    switch (actionEvent.getActionType()) {
                        case START_TURN:
                            this.isUpgradedHealing = false;
                            break;
                        case BEFORE_TREATMENT:
                            hero.setTreatment(hero.getTreatment() + HEALING_BOOST);
                            this.isUpgradedHealing = true;
                            break;
                        case AFTER_TREATMENT:
                            hero.setTreatment(hero.getTreatment() - HEALING_BOOST);
                            break;
                        case END_TURN:
                        case SKIP_TURN:
                            if (!isUpgradedHealing) {
                                this.isWorking = false;
                            }
                    }
                }
            }

            @Override
            public final String getName() {
                return "RegenerationAmulet";
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