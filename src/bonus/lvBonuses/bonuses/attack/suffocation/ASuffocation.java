package bonus.lvBonuses.bonuses.attack.suffocation;

import bonus.bonuses.Bonus;
import heroes.abstractHero.bonusManagement.BonusManager;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import javafx.util.Pair;
import managment.actionManagement.actions.ActionEvent;
import managment.actionManagement.actions.ActionEventFactory;
import managment.actionManagement.actions.ActionType;
import managment.actionManagement.service.components.handleComponet.HandleComponent;
import managment.actionManagement.service.components.handleComponet.IllegalSwitchOffHandleComponentException;
import managment.actionManagement.service.components.providerComponent.ProviderComponent;
import managment.actionManagement.service.engine.EventEngine;
import managment.actionManagement.service.engine.services.RegularHandleService;
import managment.playerManagement.Player;

import java.util.*;

import static bonus.lvBonuses.bonuses.attack.suffocation.DestroySnatch.ID;

public final class ASuffocation extends Bonus implements RegularHandleService {

    private static final int TURN = 1;

    private List<Pair<Hero, Stack<Integer>>> listHeroVsDamage;

    private Map<Hero, Pair<Integer, ProviderComponent<Integer>>> mapHeroVsPreviousBonusIndex;

    public ASuffocation(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
        this.listHeroVsDamage = new ArrayList<>();
        this.mapHeroVsPreviousBonusIndex = new HashMap<>();
    }

    @Override
    public final void use() {
        final Hero opponentHero = playerManager.getOpponentTeam().getCurrentPlayer().getCurrentHero();
        for (final Pair<Hero, Stack<Integer>> heroVsDamage : listHeroVsDamage){
            if (heroVsDamage.getKey() == opponentHero){
                final Stack<Integer> damageStack = heroVsDamage.getValue();
                damageStack.push(TURN);
                return; //stop
            }
        }
        final Pair<Hero, Stack<Integer>> heroStackPair = new Pair<>(opponentHero, new Stack<>());
        final BonusManager bonusManager = opponentHero.getBonusManager();
        final int index = bonusManager.getAvailableProviderComponent();
        final List<ProviderComponent<Integer>> providerComponentList = bonusManager.getProviderComponentList();
        final ProviderComponent<Integer> previousProviderComponent = providerComponentList.get(index);
        this.mapHeroVsPreviousBonusIndex.put(opponentHero, new Pair<>(index, previousProviderComponent));
        this.listHeroVsDamage.add(heroStackPair);
        final ProviderComponent<Integer> customProviderComponent
                = getCustomProviderComponent(previousProviderComponent.getPriority());
        bonusManager.setCustomProviderComponent(index, customProviderComponent);
    }

    private ProviderComponent<Integer> getCustomProviderComponent(final int inputPriority) {
        return new ProviderComponent<>() {

            private int priority = inputPriority;

            @Override
            public final Integer getValue() {
                return ID;
            }

            @Override
            public int getPriority() {
                return priority;
            }

            @Override
            public void setPriority(final int priority) {
                this.priority = priority;
            }
        };
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
                    listHeroVsDamage.forEach(heroVsStackPair -> {
                        final Hero opponentHero = heroVsStackPair.getKey();
                        final Stack<Integer> turnDamageStack = heroVsStackPair.getValue();
                        for (int i = 0; i < turnDamageStack.size(); i++) {
                            final int damage = turnDamageStack.get(i);
                            final EventEngine engine = actionManager.getEventEngine();
                            engine.handle(ActionEventFactory.getBeforeDealDamage(currentPlayer, opponentHero, damage));
                            if (opponentHero.getDamage(damage)) {
                                engine.handle(ActionEventFactory.getAfterDealDamage(currentPlayer, opponentHero
                                        , damage));
                            }
                            turnDamageStack.setElementAt(damage + TURN, i);
                        }
                    });
                }
                if (actionEvent.getActionType() == ActionType.AFTER_USED_BONUS){
                    final Player victimPlayer = actionEvent.getPlayer();
                    final Object data = actionEvent.getData();
                    if (data instanceof String){
                        final String message = (String) data;
                        if (message.equals("DestroySnatch")){
                            final Hero hero = victimPlayer.getCurrentHero();
                            for (final Pair<Hero, Stack<Integer>> heroStackPair : listHeroVsDamage){
                                if (hero == heroStackPair.getKey()){
                                    final Stack<Integer> damageStack = heroStackPair.getValue();
                                    if (!damageStack.isEmpty()){
                                        damageStack.pop();
                                    } else {
                                        final Pair<Integer, ProviderComponent<Integer>> previousProviderComponentPair
                                                = mapHeroVsPreviousBonusIndex.remove(hero);
                                        //FIXME TO BE CAREFUL THAT TO NOT CATCH CONCURRENT_MODIFICATION_EXCEPTION!
                                        listHeroVsDamage.remove(heroStackPair);
                                        final int index = previousProviderComponentPair.getKey();
                                        final ProviderComponent<Integer> previousProviderComponent
                                                = previousProviderComponentPair.getValue();
                                        final BonusManager bonusManager = hero.getBonusManager();
                                        bonusManager.returnPreviousProviderComponent(index, previousProviderComponent);
                                    }
                                }
                            }
                        }
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
            public final void setWorking(final boolean able) throws IllegalSwitchOffHandleComponentException {
                throw new IllegalSwitchOffHandleComponentException();
            }
        };
    }
}