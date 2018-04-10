package bonus.generalBonuses.bonuses.health;

import bonus.bonuses.Bonus;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HElixirOfLife extends Bonus {

    private static final Logger log = LoggerFactory.getLogger(HElixirOfLife.class);

    private static final double HEALING_BOOST = 50;

    public HElixirOfLife(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final Hero currentHero = playerManager.getCurrentTeam().getCurrentPlayer().getCurrentHero();
        if (currentHero.getHealing(HEALING_BOOST)) {
            log.info("+50 HP");
            actionManager.getEventEngine().setRepeatHandling(true);
        }
    }
}
