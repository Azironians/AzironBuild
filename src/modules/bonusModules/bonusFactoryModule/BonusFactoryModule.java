package modules.bonusModules.bonusFactoryModule;

import annotations.bindingAnnotations.BonusServiceComponent;
import bonus.bonuses.factory.BonusFactory;
import bonus.bonuses.factory.provider.BonusFactoryProvider;
import com.google.inject.AbstractModule;

public final class BonusFactoryModule extends AbstractModule {
    
    @Override
    protected final void configure() {
        bind(BonusFactory.class).annotatedWith(BonusServiceComponent.class).toProvider(new BonusFactoryProvider());
    }
}
