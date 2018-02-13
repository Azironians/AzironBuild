package heroes.abstractHero.skills;

import java.util.List;

public interface SkillFactory {

    Skill getSwapSkill();

    List<Skill> getSuperSkills();
}
