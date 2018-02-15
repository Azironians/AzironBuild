package modules.heroConstructModules.heroSpecificModules.lv;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import heroes.lv.hero.LV;
import heroes.lv.skills.superSkills.CannibalismSkill;
import heroes.lv.skills.superSkills.NightBladesSkill;
import heroes.lv.skills.superSkills.ReincarnationSkill;
import heroes.lv.skills.swapSkills.FurySkill;
import security.assistants.PropertyAssistant;

public final class LVHeroModule extends AbstractModule{

    @Override
    protected final void configure() {
        final String heroPropertyPath = "./src/heroes/lv/hero/properties/hero.properties";
        final String skillPropertyPath = "./src/heroes/lv/skills/properties/skills.properties";
        Names.bindProperties(binder(), PropertyAssistant.makeProperties(heroPropertyPath));
        Names.bindProperties(binder(), PropertyAssistant.makeProperties(skillPropertyPath));
        requestStaticInjection(LV.class);
        requestStaticInjection(FurySkill.class);
        requestStaticInjection(CannibalismSkill.class);
        requestStaticInjection(NightBladesSkill.class);
        requestStaticInjection(ReincarnationSkill.class);
    }
}
