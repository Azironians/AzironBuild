package bonus.generalBonuses.bonuses.attack.doubleInHead;

import bonus.bonuses.Bonus;
import managment.actionManagement.ActionManager;
import managment.actionManagement.actionProccessors.AttackProcessor;
import managment.actionManagement.service.components.handleComponet.HandleComponent;
import managment.actionManagement.service.engine.services.DynamicHandleService;
import javafx.scene.image.ImageView;
import managment.actionManagement.actions.ActionEvent;
import managment.actionManagement.actions.ActionType;
import managment.playerManagement.Player;
import managment.processors.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ADoubleInHead extends Bonus implements DynamicHandleService {

    static final Logger LOG = LoggerFactory.getLogger(ADoubleInHead.class);

    static final double ATTACK_COEFFICIENT = 2;

    private Processor previousProcessor;

    public ADoubleInHead(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        this.installCustomAttackProcessor();
        actionManager.getEventEngine().addHandler(getHandlerInstance());
    }

    private void installCustomAttackProcessor() {
        try {
            final Processor attackProcessor = new DoubleInHeadProcessor(actionManager, battleManager, playerManager);
            this.previousProcessor = actionManager.getAttackProcessor();
            this.actionManager.setAttackProcessor(attackProcessor);
            LOG.info("INSTALLED CUSTOM BEFORE_ATTACK PROCESSOR");
        } catch (final ActionManager.UnsupportedProcessorException e) {
            e.printStackTrace();
        }
    }

    private void installDefaultAttack() {
        try {
            actionManager.setAttackProcessor(previousProcessor);
            LOG.info("INSTALLED DEFAULT BEFORE_ATTACK PROCESSOR");
        } catch (ActionManager.UnsupportedProcessorException e) {
            e.printStackTrace();
        }
    }

    @Override
    public final HandleComponent getHandlerInstance() {
        return new HandleComponent() {

            private boolean isWorking = true;

            private Player player;

            @Override
            public final void setup() {
                this.player = playerManager.getCurrentTeam().getCurrentPlayer();
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                final ActionType actionType = actionEvent.getActionType();
                if (actionType == ActionType.END_TURN) {
                    installDefaultAttack();
                    this.isWorking = false;
                }
            }

            @Override
            public final String getName() {
                return "DoubleInHead";
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