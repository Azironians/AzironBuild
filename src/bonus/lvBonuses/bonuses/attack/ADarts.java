package bonus.lvBonuses.bonuses.attack;

import bonus.bonuses.Bonus;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import javafx.util.Pair;
import managment.actionManagement.actions.ActionEvent;
import managment.actionManagement.actions.ActionEventFactory;
import managment.actionManagement.actions.ActionType;
import managment.actionManagement.service.components.handleComponet.HandleComponent;
import managment.actionManagement.service.components.handleComponet.IllegalSwitchOffHandleComponentException;
import managment.actionManagement.service.engine.services.RegularHandleService;
import managment.playerManagement.Player;

public final class ADarts extends Bonus implements RegularHandleService {

    private double allDamage;

    private Player thisPlayer;

    public ADarts(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final Hero opponentHero = playerManager.getOpponentTeam().getCurrentPlayer().getCurrentHero();
        if (opponentHero.getDamage(allDamage)) {
            actionManager.getEventEngine().handle(ActionEventFactory.getAfterDealDamage(thisPlayer, opponentHero
                    , allDamage));
        }
    }

    @Override
    public final HandleComponent getRegularHandlerInstance(final Player player) {
        return new HandleComponent() {

            private Player currentPlayer;

            @Override
            public final void setup() {
                this.currentPlayer = player;
                thisPlayer = player;
                allDamage = 0;
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                if (actionEvent.getActionType() == ActionType.AFTER_DEAL_DAMAGE && actionEvent.getPlayer() == currentPlayer) {
                    final Pair<Hero, Double> heroVsDamage = (Pair) actionEvent.getData();
                    final double damage = heroVsDamage.getValue();
                    allDamage += damage;
                }
            }

            @Override
            public final String getName() {
                return "Darts";
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
            public final void setWorking(final boolean able) throws IllegalSwitchOffHandleComponentException {
                throw new IllegalSwitchOffHandleComponentException();
            }
        };
    }
}