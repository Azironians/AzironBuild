package bonus.lvBonuses.bonuses.attack;

import bonus.bonuses.Bonus;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;

public final class ALossOfControl extends Bonus {

    private static final double SELF_DAMAGE  = 40;

    private static final double ATTACK_BOOST = 4;

    public ALossOfControl(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final Hero hero = playerManager.getCurrentTeam().getCurrentPlayer().getCurrentHero();
        if (hero.getDamage(SELF_DAMAGE)){
            actionManager.getEventEngine().handle();
        }
        hero.setAttack(hero.getAttack() + ATTACK_BOOST);
    }
}
