package bonus.lvBonuses.bonuses.attack.hunt;

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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static bonus.lvBonuses.bonuses.attack.hunt.StepBack.ID;

public final class AHunt extends Bonus implements RegularHandleService {

    private static final double DAMAGE = 75;

    private Pair<Integer, ProviderComponent<Integer>> indexVsCurrentPreviousProviderComponent;

    private Pair<Integer, ProviderComponent<Integer>> indexVsOpponentPreviousProviderComponent;

    private Set<Hero> enemyHeroBlackSet;

    public AHunt(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
        this.enemyHeroBlackSet = new HashSet<>();
    }

    @Override
    public final void use() {
        final Hero opponentHero = playerManager.getOpponentTeam().getCurrentPlayer().getCurrentHero();
        final BonusManager bonusManager = opponentHero.getBonusManager();
        final int index = bonusManager.getAvailableProviderComponent();
        final List<ProviderComponent<Integer>> providerComponents = bonusManager.getProviderComponentList();
        this.indexVsOpponentPreviousProviderComponent = new Pair<>(index, providerComponents.get(index));
        final ProviderComponent<Integer> customProviderComponent
                = getCustomStepBackProviderComponent(indexVsOpponentPreviousProviderComponent.getValue().getPriority());
        bonusManager.setCustomProviderComponent(index, customProviderComponent);
        this.enemyHeroBlackSet.add(opponentHero);
    }

    private ProviderComponent<Integer> getCustomStepBackProviderComponent(final int priority){
        return new ProviderComponent<>() {

            private int currentPriority = priority;

            @Override
            public final Integer getValue() {
                return ID;
            }

            @Override
            public final int getPriority() {
                return currentPriority;
            }

            @Override
            public final void setPriority(final int priority) {
                this.currentPriority = priority;
            }
        };
    }

    private ProviderComponent<Integer> getCustomHuntProviderComponent(final int priority){
        return new ProviderComponent<>() {

            private int currentPriority = priority;

            @Override
            public final Integer getValue() {
                return getId();
            }

            @Override
            public final int getPriority() {
                return currentPriority;
            }

            @Override
            public final void setPriority(final int priority) {
                this.currentPriority = priority;
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
                final ActionType actionType = actionEvent.getActionType();
                final Hero hero = actionEvent.getPlayer().getCurrentHero();
                if (actionType == ActionType.AFTER_USED_BONUS && enemyHeroBlackSet.contains(hero)){
                    final Object data = actionEvent.getData();
                    if (data instanceof String){
                        final String message = (String) data;
                        if (message.equals("StepBack")){
                            //Setup Hunt bonus:
                            final Hero currentHero = currentPlayer.getCurrentHero();
                            final BonusManager bonusManager = currentHero.getBonusManager();
                            final int index = bonusManager.getAvailableProviderComponent();
                            final List<ProviderComponent<Integer>> providerComponents
                                    = bonusManager.getProviderComponentList();
                            indexVsCurrentPreviousProviderComponent = new Pair<>(index, providerComponents.get(index));
                            final ProviderComponent<Integer> customProviderComponent
                                    = getCustomHuntProviderComponent(indexVsCurrentPreviousProviderComponent.getValue()
                                    .getPriority());
                            bonusManager.setCustomProviderComponent(index, customProviderComponent);

                            //Remove enemy bonus:
                            final BonusManager opponentBonusManager = hero.getBonusManager();
                            final int enemyIndex = indexVsOpponentPreviousProviderComponent.getKey();
                            final ProviderComponent<Integer> enemyProviderComponent
                                    = indexVsCurrentPreviousProviderComponent.getValue();
                            opponentBonusManager.returnPreviousProviderComponent(enemyIndex, enemyProviderComponent);
                            enemyHeroBlackSet.remove(hero);
                        } else {
                            final EventEngine eventEngine = actionManager.getEventEngine();
                            eventEngine.handle(ActionEventFactory.getBeforeDealDamage(currentPlayer, hero, DAMAGE));
                            if (hero.getDamage(DAMAGE)){
                                actionManager.getEventEngine().handle(ActionEventFactory
                                        .getAfterDealDamage(currentPlayer, hero, DAMAGE));
                            }
                        }
                    }
                }
            }

            @Override
            public String getName() {
                return "Hunt";
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
            public void setWorking(final boolean able) throws IllegalSwitchOffHandleComponentException {
                throw new IllegalSwitchOffHandleComponentException();
            }
        };
    }
}