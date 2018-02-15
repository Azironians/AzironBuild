package modules.heroConstructModules;

import com.google.inject.AbstractModule;
import modules.heroConstructModules.builderModule.HeroBuilderModule;
import modules.heroConstructModules.heroSpecificModules.HeroSpecificModule;
import modules.heroConstructModules.presentationModule.PresentationModule;
import modules.heroConstructModules.resourceSupplierModule.HeroResourceSupplierModule;
import modules.heroConstructModules.skillFactoryModule.SkillFactoryModule;

public final class MainHeroConstructModule extends AbstractModule{

    @Override
    protected final void configure() {
        install(new HeroSpecificModule());
        install(new HeroResourceSupplierModule());
        install(new SkillFactoryModule());
        install(new PresentationModule());
        install(new HeroBuilderModule());
    }
}
