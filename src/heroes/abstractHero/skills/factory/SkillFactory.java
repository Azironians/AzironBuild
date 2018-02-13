package heroes.abstractHero.skills.factory;

import heroes.abstractHero.skills.Skill;

import java.util.List;

public interface SkillFactory {

    Skill getSwapSkill();

    List<Skill> getSuperSkills();
}
