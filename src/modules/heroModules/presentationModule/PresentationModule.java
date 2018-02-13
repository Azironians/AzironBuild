package modules.heroModules.presentationModule;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import heroes.abstractHero.presentation.Presentation;
import security.assistants.PropertiesAssistant;

public final class PresentationModule extends AbstractModule {

    @Override
    protected final void configure() {
        Names.bindProperties(binder(), PropertiesAssistant
                .makeProperties("./src/heroes/presentation/properties/presentation.properties"));
        requestStaticInjection(Presentation.class);
    }
}
