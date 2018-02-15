package modules.heroConstructModules;

import com.google.inject.AbstractModule;
import modules.heroConstructModules.presentationModule.PresentationModule;

public final class MainHeroConstructModule extends AbstractModule{

    @Override
    protected final void configure() {
        install(new PresentationModule());
    }
}
