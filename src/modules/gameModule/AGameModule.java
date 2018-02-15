package modules.gameModule;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import javafx.stage.Stage;
import main.AGame;

public final class AGameModule extends AbstractModule {

    private final AGame aGame;

    public AGameModule(final AGame aGame) {
        this.aGame = aGame;
    }

    @Override
    protected final void configure() {
        bind(AGame.class).toInstance(aGame);
        bind(Stage.class).asEagerSingleton();
        bindConstant().annotatedWith(Names.named("icon path")).to("file:src\\resources\\Triggers\\Icon.png");
        bindConstant().annotatedWith(Names.named("build number")).to( "Build 1.0.0.0.0.0.8");
        requestStaticInjection(AGame.class);
    }
}
