package bonus.devourerBonuses.bonuses.attack.fromFireIntoTheFire;

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

public final class AFromFireIntoTheFire extends Bonus implements DynamicHandleService {

    private static final Logger LOG = LoggerFactory.getLogger(AFromFireIntoTheFire.class);

    private Processor previousProcessor;

    public AFromFireIntoTheFire(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        this.installCustomTreatmentProcessor();
        actionManager.getEventEngine().addHandler(getHandlerInstance());
    }

    private void installCustomTreatmentProcessor() {
        this.previousProcessor = actionManager.getTreatmentProcessor();
        final Processor treatmentProcessor = new FromFireIntoTheFireProcessor(actionManager, battleManager
                , playerManager);
        try {
            actionManager.setTreatmentProcessor(treatmentProcessor);
            LOG.info("INSTALLED CUSTOM BEFORE_TREATMENT PROCESSOR");
        } catch (final ActionManager.UnsupportedProcessorException e) {
            e.printStackTrace();
        }
    }

    private void installDefaultTreatment() {
        try {
            actionManager.setTreatmentProcessor(previousProcessor);
            LOG.info("INSTALLED DEFAULT BEFORE_TREATMENT PROCESSOR");
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
            public void handle(final ActionEvent actionEvent) {
                final ActionType actionType = actionEvent.getActionType();
                if (actionType == ActionType.END_TURN) {
                    installDefaultTreatment();
                    isWorking = false;
                }
            }

            @Override
            public final String getName() {
                return "FromFireIntoTheFire";
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