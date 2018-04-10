package bonus.lvBonuses.bonuses.attack.suffocation;

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

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public final class ASuffocation extends Bonus implements RegularHandleService {

    private static final int TURN = 1;

    private Set<Pair<Hero, Stack<Integer>>> setHeroVsDamage;

    private Bonus destroySnatchBonus;

    public ASuffocation(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
        this.setHeroVsDamage = new HashSet<>();
        this.destroySnatchBonus = new DestroySnatch();
    }

    @Override
    public final void use() {
        final var opponentHero = playerManager.getOpponentATeam().getCurrentPlayer().getCurrentHero();
        for (final Pair<Hero, Stack<Integer>> heroVsDamage : setHeroVsDamage){
            if (heroVsDamage.getKey() == opponentHero){
                final Stack<Integer> damageStack = heroVsDamage.getValue();
                damageStack.push(TURN);
                return; //stop
            }
        }
        this.setHeroVsDamage.add(new Pair<>(opponentHero, new Stack<>()));
    }

    @Override
    public final HandleComponent getRegularHandlerInstance(final Player player) {
        return new HandleComponent() {

            private Player currentPlayer;

            @Override
            public final void setup() {
                this.currentPlayer = player;
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                if (actionEvent.getPlayer() == currentPlayer && actionEvent.getActionType() == ActionType.END_TURN) {
                    setHeroVsDamage.forEach(heroVsStackPair -> {
                        final Hero opponentHero = heroVsStackPair.getKey();
                        final Stack<Integer> turnDamageStack = heroVsStackPair.getValue();
                        for (int i = 0; i < turnDamageStack.size(); i++) {
                            final int damage = turnDamageStack.get(i);
                            final var engine = actionManager.getEventEngine();
                            engine.handle(ActionEventFactory.getBeforeDealDamage(currentPlayer, opponentHero, damage));
                            if (opponentHero.getDamage(damage)) {
                                engine.handle(ActionEventFactory.getAfterDealDamage(currentPlayer, opponentHero
                                        , damage));
                            }
                            turnDamageStack.setElementAt(damage + TURN, i);
                        }
                    });
                }
                if ()
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