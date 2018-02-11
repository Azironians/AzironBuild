package modules.managerModules;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import managment.battleManagement.BattleManager;

public final class BattleManagerModule extends AbstractModule{
    
    @Override
    protected final void configure() {
        bind(BattleManager.class).asEagerSingleton();
        bindConstant().annotatedWith(Names.named("turn")).to(0);
        bindConstant().annotatedWith(Names.named("end turn condition")).to(true);
        bindConstant().annotatedWith(Names.named("skip turn condition")).to(false);
        bindConstant().annotatedWith(Names.named("start time")).to(480); //480
        requestStaticInjection(BattleManager.class);
    }
}
