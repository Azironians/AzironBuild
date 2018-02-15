package heroes.orcBash.builder;

import com.google.inject.Inject;
import heroes.abstractHero.builder.HeroBuilder;
import heroes.abstractHero.resourceSupplier.HeroResourceSupplier;
import heroes.abstractHero.skills.factory.SkillFactory;
import heroes.orcBash.annotation.OrcBashHeroService;
import heroes.orcBash.hero.OrcBash;

public final class OrcBashBuilder implements HeroBuilder {

    @Inject
    @OrcBashHeroService
    private SkillFactory skillFactory;

    @Inject
    @OrcBashHeroService
    private HeroResourceSupplier resourceSupplier;

    @Override
    public final SkillFactory getSkillFactory() {
        return skillFactory;
    }

    @Override
    public final HeroResourceSupplier getHeroResourceSupplier() {
        return resourceSupplier;
    }

    @Override
    public final Class getHeroClass() {
        return OrcBash.class;
    }
}