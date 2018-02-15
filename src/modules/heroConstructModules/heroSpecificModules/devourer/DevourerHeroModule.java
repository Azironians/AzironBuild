package modules.heroConstructModules.heroSpecificModules.devourer;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import heroes.devourer.hero.Devourer;
import heroes.devourer.skills.superSkills.ConsumingSkill;
import heroes.devourer.skills.superSkills.FlameSnakesSkill;
import heroes.devourer.skills.superSkills.RegenerationSkill;
import heroes.devourer.skills.swapSkills.DepletionSkill;
import security.assistants.PropertyAssistant;

public final class DevourerHeroModule extends AbstractModule {

    @Override
    protected final void configure() {
        final String heroPropertyPath = "./src/heroes/devourer/hero/properties/hero.properties";
        final String skillPropertyPath = "./src/heroes/devourer/skills/properties/skills.properties";
        Names.bindProperties(binder(), PropertyAssistant.makeProperties(heroPropertyPath));
        Names.bindProperties(binder(), PropertyAssistant.makeProperties(skillPropertyPath));
        requestStaticInjection(Devourer.class);
        requestStaticInjection(DepletionSkill.class);
        requestStaticInjection(FlameSnakesSkill.class);
        requestStaticInjection(RegenerationSkill.class);
        requestStaticInjection(ConsumingSkill.class);
    }
}
