package modules.heroConstructModules.heroSpecificModules.devourer;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import heroes.devourer.hero.DevourerProperties;
import heroes.devourer.skills.superSkills.consuming.ConsumingPropertySkill;
import heroes.devourer.skills.superSkills.flameSnakes.FlameSnakesPropertySkill;
import heroes.devourer.skills.superSkills.regeneration.RegenerationPropertySkill;
import heroes.devourer.skills.swapSkills.depletion.DepletionPropertySkill;
import security.assistants.PropertyAssistant;

public final class DevourerHeroModule extends AbstractModule {

    @Override
    protected final void configure() {
        final String heroPropertyPath = "./src/heroes/devourer/hero/properties/hero.properties";
        final String skillPropertyPath = "./src/heroes/devourer/skills/properties/skills.properties";
        Names.bindProperties(binder(), PropertyAssistant.makeProperties(heroPropertyPath));
        Names.bindProperties(binder(), PropertyAssistant.makeProperties(skillPropertyPath));
        requestStaticInjection(DevourerProperties.class);
        requestStaticInjection(DepletionPropertySkill.class);
        requestStaticInjection(FlameSnakesPropertySkill.class);
        requestStaticInjection(RegenerationPropertySkill.class);
        requestStaticInjection(ConsumingPropertySkill.class);
    }
}
