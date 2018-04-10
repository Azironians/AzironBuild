package bonus.devourerBonuses.bonuses.attack.fusedFromBowel;

import bonus.bonuses.Bonus;
import heroes.abstractHero.hero.Hero;
import heroes.abstractHero.skills.Skill;
import javafx.scene.image.ImageView;
import managment.playerManagement.Player;

import java.util.ArrayList;
import java.util.List;

public final class AFusedFromBowel extends Bonus {

    private static final int REQUIRED_LEVEL = 4;

    private static final int ALTERNATIVE_EXPERIENCE_BOOST = 25;

    private static final int ATTACK_BOOST = 50;

    private static final int LEVEL_REVERT = 3;

    private FusedFromBowelSkillProxyComponent fusedFromBowelSkillProxyComponent;

    public AFusedFromBowel(String name, int id, ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final Player player = playerManager.getCurrentTeam().getCurrentPlayer();
        final Hero hero = player.getCurrentHero();
        if (levelReached(hero)) {
            revertLevel(hero);
            changeSkill(hero);
        } else {
            if (hero.addExperience(ALTERNATIVE_EXPERIENCE_BOOST)) {
                actionManager.getEventEngine().setRepeatHandling(true);
            }
        }
    }

    private boolean levelReached(final Hero hero) {
        return hero.getLevel() >= REQUIRED_LEVEL;
    }

    private void revertLevel(final Hero hero) {
        hero.setAttack(hero.getAttack() + ATTACK_BOOST);
        final List<Double> requiredExperienceList = hero.getListOfRequiredExperience();
        final double delta = requiredExperienceList.get(hero.getLevel() - 1)
                - requiredExperienceList.get(hero.getLevel() - LEVEL_REVERT - 1);
        if (hero.removeExperience(delta)) {
            actionManager.getEventEngine().handle();
        }
    }

    private void changeSkill(final Hero hero) {
        final List<Integer> indexes = new ArrayList<>();
        final List<Skill> skills = hero.getCollectionOfSkills();
        for (int i = 0; i < skills.size() - 1; i++) {
            if (skills.get(i).getName().equals("FlameSnakes")) {
                indexes.add(i);
            }
        }
        fusedFromBowelSkillProxyComponent.packSkills(indexes, actionManager);
    }
}