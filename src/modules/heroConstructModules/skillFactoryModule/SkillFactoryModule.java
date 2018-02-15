package modules.heroConstructModules.skillFactoryModule;

import com.google.inject.AbstractModule;
import heroes.abstractHero.skills.factory.SkillFactory;
import heroes.devourer.annotation.DevourerHeroService;
import heroes.devourer.skills.factory.DevourerSkillFactory;
import heroes.lv.annotation.LVHeroService;
import heroes.lv.skills.factory.LVSkillFactory;
import heroes.orcBash.annotation.OrcBashHeroService;
import heroes.orcBash.skills.factory.OrcBashSkillFactory;

public final class SkillFactoryModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(SkillFactory.class).annotatedWith(DevourerHeroService.class).to(DevourerSkillFactory.class);
        bind(SkillFactory.class).annotatedWith(LVHeroService.class).to(LVSkillFactory.class);
        bind(SkillFactory.class).annotatedWith(OrcBashHeroService.class).to(OrcBashSkillFactory.class);
    }
}
