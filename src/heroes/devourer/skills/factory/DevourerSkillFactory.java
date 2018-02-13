package heroes.devourer.skills.factory;

import heroes.abstractHero.skills.Skill;
import heroes.abstractHero.skills.factory.SkillFactory;

import java.util.List;

public class DevourerSkillFactory implements SkillFactory {

    @Override
    public Skill getSwapSkill() {
        return null;
    }

    @Override
    public List<Skill> getSuperSkills() {
        return null;
    }
}
