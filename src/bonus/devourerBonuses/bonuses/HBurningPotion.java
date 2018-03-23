package bonus.devourerBonuses.bonuses;

import bonus.bonuses.Bonus;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HBurningPotion extends Bonus{

    private static final Logger log = LoggerFactory.getLogger(HBurningPotion.class);

    private static final int DAMAGE = 100;

    private static final int HEALING = 120;

    public HBurningPotion(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final Hero currentHero = playerManager.getCurrentTeam().getCurrentPlayer()
                .getHero();
        if (currentHero.getDamage(DAMAGE)){
            actionManager.getEventEngine().handle();
            log.info("-100 damage");
        }
        if (currentHero.getHealing(HEALING)){
            actionManager.getEventEngine().handle();
            log.info("+120 damage");
        }
    }
}
