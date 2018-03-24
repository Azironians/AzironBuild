package bonus.devourerBonuses.bonuses;

import bonus.bonuses.Bonus;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import managment.playerManagement.Player;

public final class AHarpoonUnderworlds extends Bonus {

    private static final double DAMAGE = 25;

    public AHarpoonUnderworlds(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final Player player = playerManager.getCurrentTeam().getCurrentPlayer();
        final Hero opponentHero = playerManager.getOpponentATeam().getCurrentPlayer().getHero();
        final double damage = player.getHero().getLevel() * DAMAGE;
        if (opponentHero.getDamage(damage)){
            actionManager.getEventEngine().setRepeatHandling(true);
        }
    }
}