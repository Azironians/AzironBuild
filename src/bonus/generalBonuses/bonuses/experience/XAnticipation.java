package bonus.generalBonuses.bonuses.experience;

import bonus.bonuses.Bonus;
import managment.actionManagement.service.components.handleComponet.HandleComponent;
import managment.actionManagement.service.engine.services.DynamicHandleService;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import managment.actionManagement.actions.ActionEvent;
import managment.actionManagement.actions.ActionType;
import managment.playerManagement.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class XAnticipation extends Bonus implements DynamicHandleService {

    private static final Logger log = LoggerFactory.getLogger(XAnticipation.class);

    private static final int EXPERIENCE_BOOST = 3;

    public XAnticipation(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final HandleComponent handler = getHandlerInstance();
        actionManager.getEventEngine().addHandler(handler);
        log.info("ANTICIPATION IS ACTIVATED");
    }

    @Override
    public final HandleComponent getHandlerInstance() {
        return new HandleComponent() {

            private Player player;

            private Player opponent;

            private Player alternativeOpponent;

            private boolean isWorking = true;

            @Override
            public final void setup() {
                this.player = playerManager.getCurrentTeam().getCurrentPlayer();
                this.opponent = playerManager.getOpponentTeam().getCurrentPlayer();
                this.alternativeOpponent = playerManager.getOpponentTeam().getAlternativePlayer();
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                final Player victimPlayer = playerManager.getOpponentTeam().getCurrentPlayer();
                final Player attackPlayer = actionEvent.getPlayer();

                if (actionEvent.getActionType() == ActionType.BEFORE_ATTACK && (attackPlayer == opponent
                        || attackPlayer == alternativeOpponent) && player == victimPlayer) {
                    final Hero victimHero = player.getCurrentHero();
                    victimHero.addExperience(EXPERIENCE_BOOST);
                    log.info("+3 XP");
                }
            }

            @Override
            public final String getName() {
                return "Anticipation";
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
