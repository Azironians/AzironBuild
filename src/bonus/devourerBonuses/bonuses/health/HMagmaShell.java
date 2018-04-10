package bonus.devourerBonuses.bonuses.health;

import bonus.bonuses.Bonus;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HMagmaShell extends Bonus {

    private static final Logger log = LoggerFactory.getLogger(HMagmaShell.class);

    private static final int FIRST_LEVEL = 1;

    private static final int BIG_HEALING_BOOST = 100;

    private static final int LOW_HEALING_BOOST = 3;

    public HMagmaShell(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final Hero hero = playerManager.getCurrentTeam().getCurrentPlayer().getCurrentHero();
        final double healingBoost;
        if (hero.getLevel() == FIRST_LEVEL){
            healingBoost = BIG_HEALING_BOOST;
        } else {
            healingBoost = LOW_HEALING_BOOST;
        }
        hero.setHealthSupply(hero.getHealthSupply() + healingBoost);
        hero.setHitPoints(hero.getHitPoints() + healingBoost);
        log.info("+" + healingBoost + " HP");
    }
}