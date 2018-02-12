package modules.securityModules.loadSuppliers;

import annotations.bindingAnnotations.BonusService;
import annotations.bindingAnnotations.ProfileService;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import managment.profileManagement.Profile;
import security.loadSuppliers.LoadSupplier;
import security.loadSuppliers.bonusSupplier.BonusData;
import security.loadSuppliers.bonusSupplier.BonusLoadSupplier;
import security.loadSuppliers.profileSupplier.ProfileLoadSupplier;

public final class LoadSupplierModule extends AbstractModule {

    @Override
    protected final void configure() {
        final TypeLiteral<LoadSupplier<Profile>> profileLiteral = new TypeLiteral<>(){};
        final TypeLiteral<LoadSupplier<BonusData>> bonusDataLiteral = new TypeLiteral<>(){};
        bind(profileLiteral).annotatedWith(ProfileService.class).to(ProfileLoadSupplier.class);
        bind(bonusDataLiteral).annotatedWith(BonusService.class).to(BonusLoadSupplier.class);
        install(new ProfileLoadSupplierModule());
        install(new BonusLoadSupplierModule());
    }
}