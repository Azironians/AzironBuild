package bonus.generalBonuses.bonuses.attack;

import bonus.bonuses.Bonus;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import managment.actionManagement.actions.ActionEventFactory;
import managment.playerManagement.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public final class ABerserk extends Bonus {

    private static final Logger log = LoggerFactory.getLogger(ABerserk.class);

    public ABerserk(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final Player currentPlayer = playerManager.getCurrentTeam().getCurrentPlayer();
        final Hero currentHero = currentPlayer.getHero();
        final Player opponentPlayer = playerManager.getOpponentATeam().getCurrentPlayer();
        final Hero opponentHero = opponentPlayer.getHero();

        opponentHero.getDamage(currentHero.getAttack());
        log.info("DEAL " + currentHero.getAttack() + " DAMAGE TO OPPONENT PLAYER");
        currentHero.getDamage(opponentHero.getAttack());
        log.info("DEAL " + opponentHero.getAttack() + " DAMAGE TO CURRENT PLAYER");
        actionEvents.addAll(Arrays.asList
                (ActionEventFactory.getDealDamage(currentPlayer, opponentHero, currentHero.getAttack())
                        , ActionEventFactory.getDealDamage(opponentPlayer, currentHero, opponentHero.getAttack())));
    }
}
