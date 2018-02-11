package modules.securityModules.loadSuppliers;

import annotations.bindingAnnotations.BonusService;
import annotations.bindingAnnotations.ProfileService;
import com.google.inject.AbstractModule;
import security.loadSuppliers.LoadSupplier;
import security.loadSuppliers.bonusSupplier.BonusLoadSupplier;
import security.loadSuppliers.profileSupplier.ProfileLoadSupplier;

public final class LoadSupplierModule extends AbstractModule {

    @Override
    protected final void configure() {
        bind(LoadSupplier.class).annotatedWith(ProfileService.class).to(ProfileLoadSupplier.class);
        bind(LoadSupplier.class).annotatedWith(BonusService.class).to(BonusLoadSupplier.class);
        install(new ProfileLoadSupplierModule());
        install(new BonusLoadSupplierModule());
    }
}
