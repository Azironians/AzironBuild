package heroes.lv.skills.factory;

import com.google.inject.Inject;
import heroes.abstractHero.resourceSupplier.HeroResourceSupplier;
import heroes.abstractHero.skills.Skill;
import heroes.abstractHero.skills.factory.SkillFactory;
import heroes.lv.annotation.LVHeroService;
import heroes.lv.skills.superSkills.cannibalism.CannibalismSkill;
import heroes.lv.skills.superSkills.nightBlades.NightBladesSkill;
import heroes.lv.skills.superSkills.reincarnation.ReincarnationSkill;
import heroes.lv.skills.swapSkills.fury.FurySkill;

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
        return new CannibalismSkill(resource.getSprite(), resource.getDescription(), resource.getVoiceList());
    }

    private Skill getNightBladesSkill(){
        final HeroResourceSupplier.GetSkill resource = heroResourceSupplier.getSuperSkillResources().get(NIGHT_BLADES_INDEX);
        return new NightBladesSkill(resource.getSprite(), resource.getDescription(), resource.getVoiceList());
    }

    private Skill getReincarnationSkill(){
        final HeroResourceSupplier.GetSkill resource = heroResourceSupplier.getSuperSkillResources().get(REINCARNATION_INDEX);
        return new ReincarnationSkill(resource.getSprite(), resource.getDescription(), resource.getVoiceList());
    }
}