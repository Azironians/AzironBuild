package modules.heroModules.presentationModule;

import com.google.inject.AbstractModule;

public final class MainHeroModule extends AbstractModule{

    @Override
    protected final void configure() {
        install(new PresentationModule());
    }
}
