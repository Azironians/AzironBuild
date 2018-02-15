package modules.heroConstructModules.heroSpecificModules.orcBash;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import heroes.orcBash.hero.OrcBashProperties;
import heroes.orcBash.skills.superSkills.bash.BashPropertySkill;
import heroes.orcBash.skills.superSkills.favouriteBeater.FavouriteBeaterPropertySkill;
import heroes.orcBash.skills.superSkills.rush.RushPropertySkill;
import heroes.orcBash.skills.swapSkills.healthGain.HealthGainPropertySkill;
import security.assistants.PropertyAssistant;

public final class OrcBashHeroModule extends AbstractModule{

    @Override
    protected final void configure() {
        final String heroPropertyPath = "./src/heroes/orcBash/hero/properties/hero.properties";
        final String skillPropertyPath = "./src/heroes/orcBash/skills/properties/skills.properties";
        Names.bindProperties(binder(), PropertyAssistant.makeProperties(heroPropertyPath));
        Names.bindProperties(binder(), PropertyAssistant.makeProperties(skillPropertyPath));
        requestStaticInjection(OrcBashProperties.class);
        requestStaticInjection(HealthGainPropertySkill.class);
        requestStaticInjection(BashPropertySkill.class);
        requestStaticInjection(FavouriteBeaterPropertySkill.class);
        requestStaticInjection(RushPropertySkill.class);
    }
}
