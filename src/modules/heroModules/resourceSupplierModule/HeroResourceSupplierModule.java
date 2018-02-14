package modules.heroModules.resourceSupplierModule;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import heroes.abstractHero.resourceSupplier.HeroResourceSupplier;
import heroes.devourer.annotation.DevourerHeroService;
import heroes.devourer.recourceSupplier.DevourerResourceSupplier;
import heroes.lv.annotation.LVHeroService;
import heroes.lv.resourceSupplier.LVResourceSupplier;
import heroes.orcBash.annotation.OrcBashHeroService;
import heroes.orcBash.resourceSupplier.OrcBashResourceSupplier;
import security.assistants.PropertiesAssistant;

public final class HeroResourceSupplierModule extends AbstractModule {

    @Override
    protected final void configure() {
        bindHeroServices();
        bindProperties();
    }

    private void bindHeroServices(){
        bind(HeroResourceSupplier.class).annotatedWith(DevourerHeroService.class).to(DevourerResourceSupplier.class);
        bind(HeroResourceSupplier.class).annotatedWith(LVHeroService.class).to(LVResourceSupplier.class);
        bind(HeroResourceSupplier.class).annotatedWith(OrcBashHeroService.class).to(OrcBashResourceSupplier.class);
    }

    private void bindProperties(){
        final String path = "./src/heroes/abstractHero/resourceSupplier/properties/resource.properties";
        Names.bindProperties(binder(), PropertiesAssistant.makeProperties(path));
    }
}
