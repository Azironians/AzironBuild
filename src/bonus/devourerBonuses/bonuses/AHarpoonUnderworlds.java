package bonus.devourerBonuses.bonuses;

import bonus.bonuses.Bonus;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import managment.playerManagement.Player;

public final class AHarpoonUnderworlds extends Bonus {

    private static final double DAMAGE = 25;

    public AHarpoonUnderworlds(String name, int id, ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final Player player = playerManager.getCurrentTeam().getCurrentPlayer();
        final Hero opponentHero = playerManager.getOpponentATeam().getCurrentPlayer().getHero();
        final double damage = player.getHero().getLevel() * DAMAGE;
        if (opponentHero.getDamage(damage)){
            actionManager.getEventEngine().handle();
        }
    }
}