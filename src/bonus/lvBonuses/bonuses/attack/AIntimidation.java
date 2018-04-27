package bonus.lvBonuses.bonuses.attack;

import bonus.bonuses.Bonus;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;

public final class AIntimidation extends Bonus {

    private static final double LIMIT_HEALTH = 100;

    private static final double ATTACK_BOOST = 3;

    public AIntimidation(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final Hero currentHero = playerManager.getCurrentTeam().getCurrentPlayer().getCurrentHero();
        final Hero opponentHero = playerManager.getOpponentTeam().getCurrentPlayer().getCurrentHero();
        if (opponentHero.getHitPoints() < LIMIT_HEALTH){
            currentHero.setAttack(currentHero.getAttack() + ATTACK_BOOST);
        }
    }
}
