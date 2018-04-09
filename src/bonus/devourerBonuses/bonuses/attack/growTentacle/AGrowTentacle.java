package bonus.devourerBonuses.bonuses.attack.growTentacle;

import bonus.bonuses.Bonus;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import managment.actionManagement.actions.ActionEvent;
import managment.actionManagement.actions.ActionType;
import managment.actionManagement.service.components.handleComponet.HandleComponent;
import managment.actionManagement.service.components.providerComponent.ProviderComponent;
import managment.actionManagement.service.engine.services.DynamicHandleService;
import managment.bonusManagment.BonusManager;
import managment.playerManagement.ATeam;
import managment.playerManagement.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AGrowTentacle extends Bonus implements DynamicHandleService {

    private static final Logger log = LoggerFactory.getLogger(AGrowTentacle.class);

    private static final int ATTACK_BOOST = 4;

    private static int CUT_TENTACLE_ID;

    public AGrowTentacle(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final Hero currentHero = playerManager.getCurrentTeam().getCurrentPlayer().getHero();
        currentHero.setAttack(currentHero.getAttack() + ATTACK_BOOST);
        log.info("+4 BEFORE_ATTACK");
        actionManager.getEventEngine().addHandler(getHandlerInstance());
    }

    @Override
    public final HandleComponent getHandlerInstance() {
        return new HandleComponent() {

            private Player player;

            private ATeam opponentTeam;

            private int index;

            private boolean isWorking;

            private ProviderComponent<Integer> previousProviderComponent;

            @Override
            public final void setup() {
                this.player = playerManager.getCurrentTeam().getCurrentPlayer();
                this.opponentTeam = playerManager.getOpponentATeam();
                this.isWorking = true;
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                if (actionEvent.getActionType() == ActionType.START_TURN
                        && (actionEvent.getPlayer() == opponentTeam.getCurrentPlayer()
                        || actionEvent.getPlayer() == opponentTeam.getAlternativePlayer())) {
                    final BonusManager bonusManager = battleManager.getBonusManager();
                    this.index = bonusManager.getAvailableProviderComponent();
                    final ProviderComponent<Integer> customProviderComponent = getCustomProviderComponent();
                    this.previousProviderComponent = bonusManager.getProviderComponentList().get(index);
                    bonusManager.setCustomProviderComponent(index, customProviderComponent);
                }
                if ((actionEvent.getActionType() == ActionType.END_TURN
                        || actionEvent.getActionType() == ActionType.SKIP_TURN
                        || actionEvent.getActionType() == ActionType.AFTER_USED_BONUS)
                        && (actionEvent.getPlayer() == opponentTeam.getCurrentPlayer()
                        || actionEvent.getPlayer() == opponentTeam.getAlternativePlayer())) {
                    battleManager.getBonusManager().returnPreviousProviderComponent(index, previousProviderComponent);
                    this.isWorking = false;
                }
            }

            @Override
            public final String getName() {
                return "GrowTentacle";
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

            private ProviderComponent<Integer> getCustomProviderComponent() {
                return new ProviderComponent<>() {

                    private int priority = 0;

                    @Override
                    public final Integer getValue() {
                        return CUT_TENTACLE_ID;
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
        };
    }
}