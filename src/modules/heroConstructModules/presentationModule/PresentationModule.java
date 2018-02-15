package modules.heroConstructModules.presentationModule;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import heroes.abstractHero.presentation.Presentation;
import security.assistants.PropertyAssistant;

public final class PresentationModule extends AbstractModule {

    @Override
    protected final void configure() {
        Names.bindProperties(binder(), PropertyAssistant
                .makeProperties("./src/heroes/presentation/properties/presentation.properties"));
        requestStaticInjection(Presentation.class);
    }
}
