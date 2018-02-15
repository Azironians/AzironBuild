package bonus.general;

import bonus.bonuses.Bonus;
import bonus.bonuses.HandlerBonus;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import managment.actionManagement.actions.ActionEvent;
import managment.actionManagement.actions.ActionEventFactory;
import managment.actionManagement.actions.ActionType;
import managment.actionManagement.service.bonusEngine.BonusEventEngine;
import managment.playerManagement.ATeam;
import managment.playerManagement.Player;
import managment.processors.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ADoubleInHead extends Bonus implements HandlerBonus {

    private static final Logger log = LoggerFactory.getLogger(ADoubleInHead.class);

    private static final double ATTACK_COEFFICIENT = 2;

    public ADoubleInHead(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    private final Processor attackProcessor = () -> {
        final ATeam attackTeam = playerManager.getCurrentTeam();
        final ATeam victimTeam = playerManager.getOpponentATeam();
        final Player attackPlayer = attackTeam.getCurrentPlayer();
        final Hero attackHero = attackPlayer.getHero();

        final BonusEventEngine bonusEventEngine = actionManager.getBonusEventEngine();

        final double attackValue = attackHero.getAttack() * ATTACK_COEFFICIENT;
        log.info("ATTACK IS DUPLICATED");

        if (victimTeam.getCurrentPlayer().getHero().getDamage(attackValue)) {
            bonusEventEngine.handle(ActionEventFactory.getDealDamage(attackPlayer));
        }

        actionManager.refreshScreen();
        if (battleManager.isEndTurn()) {
            actionManager.endTurn(attackTeam);
        }
    };

    @Override
    public final void use() {
        installCustomAttack();
        actionManager.getBonusEventEngine().addHandler(getHandlerInstance());
    }

    private void installCustomAttack() {
        actionManager.setStandardAttack(false);
        actionManager.setProcessor(attackProcessor);
        log.info("INSTALLED CUSTOM ATTACK PROCESSOR");
    }

    private void installDefaultAttack() {
        actionManager.setDefaultProcessor();
        actionManager.setStandardAttack(true);
        log.info("INSTALLED DEFAULT ATTACK PROCESSOR");
    }

    @Override
    public final GetAHandler getHandlerInstance() {
        return new GetAHandler() {

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
                return "DoubleInHead";
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
