package bonus.lvBonuses.bonuses.attack.suffocation;

import bonus.bonuses.Bonus;
import javafx.scene.image.ImageView;
import javafx.util.Pair;
import managment.actionManagement.actions.ActionEvent;
import managment.actionManagement.actions.ActionEventFactory;
import managment.actionManagement.actions.ActionType;
import managment.actionManagement.service.components.handleComponet.HandleComponent;
import managment.actionManagement.service.components.handleComponet.IllegalSwitchOffHandleComponentException;
import managment.actionManagement.service.engine.services.RegularHandleService;
import managment.playerManagement.Player;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public final class ASuffocation extends Bonus implements RegularHandleService {

    private static final int TURN = 1;

    private Set<Pair<Player, Stack<Integer>>> setPlayerVsDamage;

    public ASuffocation(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
        this.setPlayerVsDamage = new HashSet<>();
    }

    @Override
    public final void use() {
        final var opponentPlayer = playerManager.getOpponentATeam().getCurrentPlayer();
        for (final Pair<Player, Stack<Integer>> playerVsDamage : setPlayerVsDamage){
            if (playerVsDamage.getKey() == opponentPlayer){
                final Stack<Integer> damageStack = playerVsDamage.getValue();
                damageStack.push(TURN);
            }
            return;
        }
    }

    @Override
    public final HandleComponent getRegularHandlerInstance(final Player player) {
        return new HandleComponent() {

            private Player currentPlayer;

            @Override
            public final void setup() {
                this.currentPlayer = player;
                opponentPlayer = playerManager.getOpponentATeam().getCurrentPlayer();
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                if (actionEvent.getPlayer() == currentPlayer && actionEvent.getActionType() == ActionType.END_TURN) {
                    for (int i = 0; i < turnDamageStack.size(); i++) {
                        final int damage = turnDamageStack.get(i);
                        final var opponent = opponentPlayer.getHero();
                        final var engine = actionManager.getEventEngine();
                        final var opponentHero = opponentPlayer.getHero();
                        engine.handle(ActionEventFactory.getBeforeDealDamage(currentPlayer, opponentHero, damage));
                        if (opponent.getDamage(damage)) {
                            engine.handle(ActionEventFactory.getAfterDealDamage(currentPlayer, opponentPlayer.getHero()
                                    , damage));
                        }
                        turnDamageStack.setElementAt(damage + TURN, i);
                    }
                }
            }

            @Override
            public final String getName() {
                return "Suffocation";
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
            public final void setWorking(boolean able) throws IllegalSwitchOffHandleComponentException {
                throw new IllegalSwitchOffHandleComponentException();
            }
        };
    }
}