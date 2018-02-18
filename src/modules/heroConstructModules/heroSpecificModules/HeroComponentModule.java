package modules.heroConstructModules.heroSpecificModules;

import com.google.inject.AbstractModule;
import modules.heroConstructModules.heroSpecificModules.devourer.DevourerHeroModule;
import modules.heroConstructModules.heroSpecificModules.lv.LVHeroModule;
import modules.heroConstructModules.heroSpecificModules.orcBash.OrcBashHeroModule;

public final class HeroComponentModule extends AbstractModule {

    @Override
    protected final void configure() {
        install(new DevourerHeroModule());
        install(new LVHeroModule());
        install(new OrcBashHeroModule());
    }
}
