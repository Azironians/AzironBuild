package modules.bonusModules.bonusFactoryModule;

import annotations.bindingAnnotations.BonusService;
import bonus.bonuses.factory.BonusFactory;
import bonus.bonuses.factory.provider.BonusFactoryProvider;
import com.google.inject.AbstractModule;

public final class BonusFactoryModule extends AbstractModule {
    
    @Override
    protected final void configure() {
        bind(BonusFactory.class).annotatedWith(BonusService.class).toProvider(new BonusFactoryProvider());
    }
}
