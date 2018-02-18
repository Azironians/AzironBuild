package heroBuildComponent;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import heroes.abstractHero.builder.HeroBuilder;
import heroes.devourer.annotation.DevourerHeroService;
import modules.heroConstructModules.MainHeroBuilderModule;
import modules.managerModules.MainManagerModule;
import modules.managerModules.PlayerManagerModule;
import org.junit.jupiter.api.Test;
public class HeroBuildTestComponent {

    @Inject
    @DevourerHeroService
    private HeroBuilder heroBuilder;

    @Test
    public void buildHeroes(){
        final Injector injectorDevourer = Guice.createInjector(new PlayerManagerModule());
    }
}
