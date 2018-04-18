package bonus.devourerBonuses.bonuses.health.regenerationRoot;

import bonus.bonuses.Bonus;
import javafx.scene.image.ImageView;
import managment.actionManagement.ActionManager;
import managment.actionManagement.actions.ActionEvent;
import managment.actionManagement.actions.ActionType;
import managment.actionManagement.service.components.handleComponet.HandleComponent;
import managment.actionManagement.service.engine.services.DynamicHandleService;
import managment.playerManagement.Player;
import managment.processors.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HRegenerationRoot extends Bonus implements DynamicHandleService {

    private static final Logger log = LoggerFactory.getLogger(HRegenerationRoot.class);

    public HRegenerationRoot(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    private Processor previousProcessor;

    @Override
    public final void use() {
        installCustomAttack();
        actionManager.getEventEngine().addHandler(getHandlerInstance());
    }

    private void installCustomAttack() {
        this.previousProcessor = actionManager.getAttackProcessor();
        final Processor attackProcessor = new RegenerationRootProcessor(actionManager, battleManager, playerManager);
        try {
            actionManager.setAttackProcessor(attackProcessor);
            log.info("INSTALLED CUSTOM BEFORE_ATTACK PROCESSOR");
        } catch (final ActionManager.UnsupportedProcessorException e) {
            e.printStackTrace();
        }
    }

    private void installDefaultAttack() {
        try {
            actionManager.setAttackProcessor(previousProcessor );
            log.info("INSTALLED DEFAULT BEFORE_ATTACK PROCESSOR");
        } catch (final ActionManager.UnsupportedProcessorException e) {
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
            public void handle(final ActionEvent actionEvent) {
                final ActionType actionType = actionEvent.getActionType();
                if (actionType == ActionType.END_TURN) {
                    installDefaultAttack();
                    isWorking = false;
                }
            }

            @Override
            public final String getName() {
                return "RegenerationRoot";
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