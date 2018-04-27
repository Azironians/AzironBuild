package bonus.devourerBonuses.bonuses.health;

import bonus.bonuses.Bonus;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;

public final class HSoulPiercing extends Bonus {

    private static final double DESTROY_TREATMENT_COEFFICIENT = 0.95;

    public HSoulPiercing(String name, int id, ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final Hero currentHero = playerManager.getCurrentTeam().getCurrentPlayer()
                .getCurrentHero();
        final Hero opponentHero = playerManager.getOpponentTeam().getCurrentPlayer()
                .getCurrentHero();
        final double treatmentDecreasing = currentHero.getAttack()
                * DESTROY_TREATMENT_COEFFICIENT;
        opponentHero.setTreatment(treatmentDecreasing);
    }
}
