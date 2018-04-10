package bonus.lvBonuses.bonuses.health;

import bonus.bonuses.Bonus;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;

public final class HSwap extends Bonus {

    private static final double ATTACK_LOST = 25;

    private static final double TREATMENT_BOOST = 25;

    public HSwap(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final Hero hero = playerManager.getCurrentTeam().getCurrentPlayer().getCurrentHero();
        double comparison = hero.getAttack() - ATTACK_LOST;
        if (comparison < 0){
            comparison = 0;
        }
        hero.setAttack(comparison);
        hero.setTreatment(TREATMENT_BOOST);
    }
}
