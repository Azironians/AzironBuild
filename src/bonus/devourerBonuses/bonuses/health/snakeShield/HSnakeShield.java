package bonus.devourerBonuses.bonuses.health.snakeShield;

import bonus.bonuses.Bonus;
import heroes.abstractHero.hero.Hero;
import heroes.abstractHero.skills.Skill;
import javafx.scene.image.ImageView;

import java.util.List;

public final class HSnakeShield extends Bonus {

    public HSnakeShield(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final Hero hero = playerManager.getCurrentTeam().getCurrentPlayer().getCurrentHero();
        final List<Skill> skills = hero.getCollectionOfSkills();
        for (final Skill skill : skills){
            if (skill.getName().equals("FlamesSnakes")){

            }
        }
    }
}
