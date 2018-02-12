package modules.managerModules;

import com.google.inject.AbstractModule;

public final class MainManagerModule extends AbstractModule {

    @Override
    protected final void configure() {
        install(new PlayerManagerModule());
        install(new BattleManagerModule());
        install(new ActionManagerModule());
        install(new ProfileManagerModule());
    }
}
