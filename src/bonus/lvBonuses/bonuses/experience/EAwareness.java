package bonus.lvBonuses.bonuses.experience;

import bonus.bonuses.Bonus;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import managment.playerManagement.ATeam;

public final class EAwareness extends Bonus {

    private static final double EXPERIENCE_BOOST_COEFFICIENT = 0.25;

    public EAwareness(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final ATeam team = playerManager.getCurrentTeam();
        final Hero hero = team.getCurrentPlayer().getCurrentHero();
        final double currentHitPoints = hero.getHitPoints();
        final double healthSupply = hero.getHealthSupply();
        final double experienceBoost = (healthSupply - currentHitPoints) * EXPERIENCE_BOOST_COEFFICIENT;
        if (hero.addExperience(experienceBoost)){
            actionManager.getEventEngine().handle();
        }
        actionManager.endTurn(team);
    }
}