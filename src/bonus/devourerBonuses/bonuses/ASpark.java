package bonus.devourerBonuses.bonuses;

import bonus.bonuses.Bonus;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;

public final class ASpark extends Bonus{

    private static final double EXPERIENCE_COEFFICIENT = 0.05;

    public ASpark(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final Hero opponentHero = playerManager.getOpponentATeam().getCurrentPlayer().getHero();
        final double fixExperience = opponentHero.getCurrentExperience() * EXPERIENCE_COEFFICIENT;
        if (opponentHero.removeExperience(fixExperience)) {
            actionManager.getEventEngine().setRepeatHandling(true);
        }
        if (opponentHero.getDamage(fixExperience)){
            actionManager.getEventEngine().setRepeatHandling(true);
        }
    }
}
