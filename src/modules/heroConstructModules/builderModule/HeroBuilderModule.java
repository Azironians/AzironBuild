package modules.heroConstructModules.builderModule;

import com.google.inject.AbstractModule;
import heroes.abstractHero.builder.HeroBuilder;
import heroes.devourer.annotation.DevourerHeroService;
import heroes.devourer.builder.DevourerBuilder;
import heroes.lv.annotation.LVHeroService;
import heroes.lv.builder.LVBuilder;
import heroes.orcBash.annotation.OrcBashHeroService;
import heroes.orcBash.builder.OrcBashBuilder;

public final class HeroBuilderModule extends AbstractModule {

    @Override
    protected final void configure() {
        //Classic binding:
        bind(HeroBuilder.class).annotatedWith(DevourerHeroService.class).to(DevourerBuilder.class);
        bind(HeroBuilder.class).annotatedWith(LVHeroService.class).to(LVBuilder.class);
        bind(HeroBuilder.class).annotatedWith(OrcBashHeroService.class).to(OrcBashBuilder.class);
    }
}

// MultiBinder:
//    final Multibinder<HeroBuilder> multiBinder = Multibinder.newSetBinder(binder(), HeroBuilder.class);
//        multiBinder.addBinding().to(DevourerBuilder.class).asEagerSingleton();
//        multiBinder.addBinding().to(LVBuilder.class).asEagerSingleton();
//        multiBinder.addBinding().to(OrcBashBuilder.class).asEagerSingleton();