package modules.heroConstructModules.heroSpecificModules.orcBash;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import heroes.orcBash.hero.OrcBash;
import heroes.orcBash.skills.superSkills.BashSkill;
import heroes.orcBash.skills.superSkills.FavouriteBeaterSkill;
import heroes.orcBash.skills.superSkills.RushSkill;
import heroes.orcBash.skills.swapSkills.HealthGainSkill;
import security.assistants.PropertyAssistant;

public final class OrcBashHeroModule extends AbstractModule{

    @Override
    protected final void configure() {
        final String heroPropertyPath = "./src/heroes/orcBash/hero/properties/hero.properties";
        final String skillPropertyPath = "./src/heroes/orcBash/skills/properties/skills.properties";
        Names.bindProperties(binder(), PropertyAssistant.makeProperties(heroPropertyPath));
        Names.bindProperties(binder(), PropertyAssistant.makeProperties(skillPropertyPath));
        requestStaticInjection(OrcBash.class);
        requestStaticInjection(HealthGainSkill.class);
        requestStaticInjection(BashSkill.class);
        requestStaticInjection(FavouriteBeaterSkill.class);
        requestStaticInjection(RushSkill.class);
    }
}
