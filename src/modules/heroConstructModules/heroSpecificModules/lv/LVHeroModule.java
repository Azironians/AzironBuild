package modules.heroConstructModules.heroSpecificModules.lv;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import heroes.lv.hero.LVProperties;
import heroes.lv.skills.superSkills.cannibalism.CannibalismPropertySkill;
import heroes.lv.skills.superSkills.nightBlades.NightBladesPropertySkill;
import heroes.lv.skills.superSkills.reincarnation.ReincarnationPropertySkill;
import heroes.lv.skills.swapSkills.fury.FuryPropertySkill;
import security.assistants.PropertyAssistant;

public final class LVHeroModule extends AbstractModule{

    @Override
    protected final void configure() {
        final String heroPropertyPath = "./src/heroes/lv/hero/properties/hero.properties";
        final String skillPropertyPath = "./src/heroes/lv/skills/properties/skills.properties";
        Names.bindProperties(binder(), PropertyAssistant.makeProperties(heroPropertyPath));
        Names.bindProperties(binder(), PropertyAssistant.makeProperties(skillPropertyPath));
        requestStaticInjection(LVProperties.class);
        requestStaticInjection(FuryPropertySkill.class);
        requestStaticInjection(CannibalismPropertySkill.class);
        requestStaticInjection(NightBladesPropertySkill.class);
        requestStaticInjection(ReincarnationPropertySkill.class);
    }
}
