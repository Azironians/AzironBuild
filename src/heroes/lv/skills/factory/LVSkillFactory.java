package heroes.lv.skills.factory;

import com.google.inject.Inject;
import heroes.abstractHero.resourceSupplier.HeroResourceSupplier;
import heroes.abstractHero.skills.Skill;
import heroes.abstractHero.skills.factory.SkillFactory;
import heroes.devourer.skills.superSkills.ConsumingSkill;
import heroes.devourer.skills.superSkills.FlameSnakesSkill;
import heroes.devourer.skills.superSkills.RegenerationSkill;
import heroes.lv.annotation.LVHeroService;
import heroes.lv.skills.swapSkills.FurySkill;

import java.util.Arrays;
import java.util.List;

public final class LVSkillFactory implements SkillFactory{

    @Inject
    @LVHeroService
    private HeroResourceSupplier heroResourceSupplier;

    private static final int CANNIBALISM_INDEX = 0;

    private static final int NIGHT_BLADES_INDEX = 1;

    private static final int REINCARNATION_INDEX = 2;

    @Override
    public final Skill getSwapSkill() {
        final HeroResourceSupplier.GetSkill resource = heroResourceSupplier.getSwapSkillResources();
        return new FurySkill(resource.getSprite(), resource.getDescription(), resource.getVoiceList());
    }

    @Override
    public final List<Skill> getSuperSkills() {
        return Arrays.asList(getCannibalismSkill(), getNightBladesSkill(), getReincarnationSkill());
    }

    private Skill getCannibalismSkill(){
        final HeroResourceSupplier.GetSkill resource = heroResourceSupplier.getSuperSkillResources().get(CANNIBALISM_INDEX);
        return new FlameSnakesSkill(resource.getSprite(), resource.getDescription(), resource.getVoiceList());
    }

    private Skill getNightBladesSkill(){
        final HeroResourceSupplier.GetSkill resource = heroResourceSupplier.getSuperSkillResources().get(NIGHT_BLADES_INDEX);
        return new RegenerationSkill(resource.getSprite(), resource.getDescription(), resource.getVoiceList());
    }

    private Skill getReincarnationSkill(){
        final HeroResourceSupplier.GetSkill resource = heroResourceSupplier.getSuperSkillResources().get(REINCARNATION_INDEX);
        return new ConsumingSkill(resource.getSprite(), resource.getDescription(), resource.getVoiceList());
    }
}
