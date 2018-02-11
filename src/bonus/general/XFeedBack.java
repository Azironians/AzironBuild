package bonus.general;

import bonus.bonuses.Bonus;
import bonus.bonuses.HandlerBonus;
import bonus.bonuses.InstallerBonus;
import heroes.abstractHero.AHero;
import javafx.scene.image.ImageView;
import managment.actionManagement.actions.ActionEvent;
import managment.actionManagement.service.bonusEngine.BonusEventEngine;
import managment.playerManagement.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class XFeedBack extends Bonus implements InstallerBonus {

    private static final Logger log = LoggerFactory.getLogger(XFeedBack.class);

    private static final double SKILL_HEALING_COEFFICIENT = 0.5;

    private static final double SKILL_EXPERIENCE_COEFFICIENT = 0.4;

    public XFeedBack(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    private double lastDamage;

    @Override
    public final void use() {
        final AHero currentHero = playerManager.getCurrentTeam().getCurrentPlayer().getHero();
        final AHero opponentHero = playerManager.getOpponentATeam().getCurrentPlayer().getHero();

        final double HEALING_BOOST = lastDamage * SKILL_HEALING_COEFFICIENT;
        final double OPPONENT_EXPERIENCE_BOOST = lastDamage * SKILL_EXPERIENCE_COEFFICIENT;
        log.info("HEALING_BOOST: " + HEALING_BOOST);
        log.info("OPPONENT_XP_BOOST: " + OPPONENT_EXPERIENCE_BOOST);

        final BonusEventEngine engine = actionManager.getBonusEventEngine();
        if (currentHero.getHealing(HEALING_BOOST) | opponentHero.addExperience(OPPONENT_EXPERIENCE_BOOST)) {
            log.info("FEEDBACK WAS SUCCESSFUL");
            engine.handle();
        }
    }

    @Override
    public final HandlerBonus.GetAHandler getInstallHandlerInstance(final Player inputPlayer) {
        return new HandlerBonus.GetAHandler() {

            private Player player;

            private double hitPoints;

            @Override
            public final void setup() {
                this.player = inputPlayer;
                this.hitPoints = player.getHero().getHitPoints();
            }

            @Override
            public synchronized final void handle(final ActionEvent actionEvent) {
                final double newHitPoints = player.getHero().getHitPoints();
                final double comparison = hitPoints - newHitPoints;
                if (comparison > 0) {
                    lastDamage = comparison;
                    log.info("WAS DAMAGE: " + lastDamage);
                    this.hitPoints = newHitPoints;
                }
            }

            @Override
            public final String getName() {
                return "FeedBack";
            }

            @Override
            public final Player getPlayer() {
                return player;
            }

            @Override
            public final boolean isWorking() {
                return true;
            }

            @Override
            public final void setAble(boolean able) {
                throw new UnsupportedOperationException();
            }
        };
    }
}
