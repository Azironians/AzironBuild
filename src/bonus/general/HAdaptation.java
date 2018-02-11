package bonus.general;

import bonus.bonuses.Bonus;
import heroes.abstractHero.AHero;
import javafx.scene.image.ImageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HAdaptation extends Bonus {

    private static final Logger log = LoggerFactory.getLogger(HAdaptation.class);

    public HAdaptation(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final AHero currentHero = playerManager.getCurrentTeam().getCurrentPlayer().getHero();
        final double HEATH_SUPPLY_BOOST = currentHero.getAttack();
        currentHero.setHealthSupply(currentHero.getHealthSupply() + HEATH_SUPPLY_BOOST);
        log.info("HEALTH SUPPLY IS INCREASED");
    }
}
