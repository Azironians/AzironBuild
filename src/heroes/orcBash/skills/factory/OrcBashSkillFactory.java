package heroes.orcBash.skills.factory;

import com.google.inject.Inject;
import heroes.abstractHero.resourceSupplier.HeroResourceSupplier;
import heroes.abstractHero.skills.Skill;
import heroes.abstractHero.skills.factory.SkillFactory;
import heroes.orcBash.annotation.OrcBashHeroService;
import heroes.orcBash.skills.superSkills.bash.BashSkill;
import heroes.orcBash.skills.superSkills.favouriteBeater.FavouriteBeaterSkill;
import heroes.orcBash.skills.superSkills.rush.RushSkill;
import heroes.orcBash.skills.swapSkills.healthGain.HealthGainSkill;

import java.util.Arrays;
import java.util.List;

public final class OrcBashSkillFactory implements SkillFactory{

    @Inject
    @OrcBashHeroService
    private HeroResourceSupplier heroResourceSupplier;

    private static final int BASH_INDEX = 0;

    private static final int FAVOURITE_BEATER_INDEX = 1;

    private static final int RUSH_INDEX = 2;

    @Override
    public final Skill getSwapSkill() {
        final HeroResourceSupplier.GetSkill resource = heroResourceSupplier.getSwapSkillResources();
        return new HealthGainSkill(resource.getSprite(), resource.getDescription(), resource.getVoiceList());
    }

    @Override
    public final List<Skill> getSuperSkills() {
        return Arrays.asList(getFavouriteBeaterSkill(), getBashSkill(), getRushSkill());
    }

    private Skill getBashSkill(){
        final HeroResourceSupplier.GetSkill resource = heroResourceSupplier.getSuperSkillResources().get(BASH_INDEX);
        return new BashSkill(resource.getSprite(), resource.getDescription(), resource.getVoiceList());
    }

    private Skill getFavouriteBeaterSkill(){
        final HeroResourceSupplier.GetSkill resource = heroResourceSupplier.getSuperSkillResources().get(FAVOURITE_BEATER_INDEX);
        return new FavouriteBeaterSkill(resource.getSprite(), resource.getDescription(), resource.getVoiceList());
    }

    private Skill getRushSkill(){
        final HeroResourceSupplier.GetSkill resource = heroResourceSupplier.getSuperSkillResources().get(RUSH_INDEX);
        return new RushSkill(resource.getSprite(), resource.getDescription(), resource.getVoiceList());
    }
}