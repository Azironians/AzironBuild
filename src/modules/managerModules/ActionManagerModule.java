package modules.managerModules;

import com.google.inject.AbstractModule;
import managment.actionManagement.ActionManager;

public final class ActionManagerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ActionManager.class).asEagerSingleton();
    }
}
