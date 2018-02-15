package bonus.general;

import bonus.bonuses.Bonus;
import bonus.bonuses.HandlerBonus;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import managment.actionManagement.actions.ActionEvent;
import managment.actionManagement.actions.ActionType;
import managment.playerManagement.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class XAnticipation extends Bonus implements HandlerBonus {

    private static final Logger log = LoggerFactory.getLogger(XAnticipation.class);

    private static final int EXPERIENCE_BOOST = 3;

    public XAnticipation(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final GetAHandler handler = getHandlerInstance();
        actionManager.getBonusEventEngine().addHandler(handler);
        log.info("ANTICIPATION IS ACTIVATED");
    }

    @Override
    public final GetAHandler getHandlerInstance() {
        return new GetAHandler() {

            private Player player;

            private Player opponent;

            private Player alternativeOpponent;

            private boolean isWorking = true;

            @Override
            public final void setup() {
                this.player = playerManager.getCurrentTeam().getCurrentPlayer();
                this.opponent = playerManager.getOpponentATeam().getCurrentPlayer();
                this.alternativeOpponent = playerManager.getOpponentATeam().getAlternativePlayer();
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                final Player victimPlayer = playerManager.getOpponentATeam().getCurrentPlayer();
                final Player attackPlayer = actionEvent.getPlayer();

                if (actionEvent.getActionType() == ActionType.ATTACK && (attackPlayer == opponent
                        || attackPlayer == alternativeOpponent) && player == victimPlayer) {
                    final Hero victimHero = player.getHero();
                    victimHero.addExperience(EXPERIENCE_BOOST);
                    log.info("+3 XP");
                }
            }

            @Override
            public final String getName() {
                return "Anticipation";
            }

            @Override
            public final Player getPlayer() {
                return player;
            }

            @Override
            public final boolean isWorking() {
                return isWorking;
            }

            @Override
            public final void setAble(final boolean able) {
                this.isWorking = able;
            }
        };
    }
}
