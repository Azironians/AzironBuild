package bonus.devourerBonuses.bonuses.attack;

import bonus.bonuses.Bonus;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import managment.actionManagement.actions.ActionEventFactory;
import managment.playerManagement.Player;

public final class AHarpoonUnderworlds extends Bonus {

    private static final double DAMAGE = 25;

    public AHarpoonUnderworlds(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final Player player = playerManager.getCurrentTeam().getCurrentPlayer();
        final Player opponentPlayer = playerManager.getOpponentTeam().getCurrentPlayer();
        final Hero opponentHero = opponentPlayer.getCurrentHero();
        final double damage = player.getCurrentHero().getLevel() * DAMAGE;
        if (opponentHero.getDamage(damage)){
            actionManager.getEventEngine().handle(ActionEventFactory.getAfterDealDamage(player, opponentHero, damage));
        }
    }
}