package heroes.devourer.skills.factory;

import com.google.inject.Inject;
import heroes.abstractHero.resourceSupplier.HeroResourceSupplier;
import heroes.abstractHero.skills.Skill;
import heroes.abstractHero.skills.factory.SkillFactory;
import heroes.devourer.annotation.DevourerHeroService;
import heroes.devourer.skills.superSkills.consuming.ConsumingSkill;
import heroes.devourer.skills.superSkills.flameSnakes.FlameSnakesSkill;
import heroes.devourer.skills.superSkills.regeneration.RegenerationSkill;
import heroes.devourer.skills.swapSkills.depletion.DepletionSkill;

import java.util.Arrays;
import java.util.List;

public final class DevourerSkillFactory implements SkillFactory {

    @Inject
    @DevourerHeroService
    private HeroResourceSupplier heroResourceSupplier;

    private static final int FLAME_SNAKES_INDEX = 0;

    private static final int REGENERATION_INDEX = 1;

    private static final int CONSUMING_INDEX = 2;

    @Override
    public final Skill getSwapSkill() {
        final HeroResourceSupplier.GetSkill resource = heroResourceSupplier.getSwapSkillResources();
        return new DepletionSkill(resource.getSprite(), resource.getDescription(), resource.getVoiceList());
    }

    @Override
    public final List<Skill> getSuperSkills() {
        return Arrays.asList(getFlameSnakesSkill(), getRegenerationSkill(), getConsumingSkill());
    }

    private Skill getFlameSnakesSkill(){
        final HeroResourceSupplier.GetSkill resource = heroResourceSupplier.getSuperSkillResources().get(FLAME_SNAKES_INDEX);
        return new FlameSnakesSkill(resource.getSprite(), resource.getDescription(), resource.getVoiceList());
    }

    private Skill getRegenerationSkill(){
        final HeroResourceSupplier.GetSkill resource = heroResourceSupplier.getSuperSkillResources().get(REGENERATION_INDEX);
        return new RegenerationSkill(resource.getSprite(), resource.getDescription(), resource.getVoiceList());
    }

    private Skill getConsumingSkill(){
        final HeroResourceSupplier.GetSkill resource = heroResourceSupplier.getSuperSkillResources().get(CONSUMING_INDEX);
        return new ConsumingSkill(resource.getSprite(), resource.getDescription(), resource.getVoiceList());
    }
}