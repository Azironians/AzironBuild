package modules;

import com.google.inject.AbstractModule;
import modules.bonusModules.bonusFactoryModule.BonusFactoryModule;
import modules.managerModules.MainManagerModule;
import modules.securityModules.gate.AGateModule;
import modules.securityModules.loadSuppliers.LoadSupplierModule;

public final class MainModule extends AbstractModule {

    @Override
    protected final void configure() {
        install(new MainManagerModule());
        install(new LoadSupplierModule());
        install(new AGateModule());
        install(new BonusFactoryModule());
    }
}
