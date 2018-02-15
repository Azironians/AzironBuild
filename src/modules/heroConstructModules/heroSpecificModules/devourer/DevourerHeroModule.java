package modules.heroConstructModules.heroSpecificModules.devourer;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import security.assistants.PropertyAssistant;

public final class DevourerHeroModule extends AbstractModule {

    @Override
    protected void configure() {
        final String heroPropertyPath = "./src/heroes/devourer/hero/properties/devourer.properties";
        final String skillPropertyPath = "./src/heroes/devourer/skills/properties/skills.properties";
        Names.bindProperties(binder(), PropertyAssistant.makeProperties(heroPropertyPath));
        Names.bindProperties(binder(), PropertyAssistant.makeProperties(skillPropertyPath));
    }
}
