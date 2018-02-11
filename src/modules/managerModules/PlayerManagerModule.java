package modules.managerModules;

import com.google.inject.AbstractModule;
import managment.playerManagement.PlayerManager;

public final class PlayerManagerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(PlayerManager.class).asEagerSingleton();
    }
}
