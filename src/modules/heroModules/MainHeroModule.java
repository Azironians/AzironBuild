package modules.heroModules;

import com.google.inject.AbstractModule;
import modules.heroModules.presentationModule.PresentationModule;

public final class MainHeroModule extends AbstractModule{

    @Override
    protected final void configure() {
        install(new PresentationModule());
    }
}
