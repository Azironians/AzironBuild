package main;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import gui.sceneMover.SceneMover;
import gui.windows.AWindow;
import com.google.inject.Guice;
import com.google.inject.Injector;
import gui.windows.WindowType;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import modules.gameModule.AGameModule;
import modules.MainModule;
import org.jetbrains.annotations.Contract;

import java.net.URL;
import java.util.EnumMap;
import java.util.function.Function;

public final class AGame extends Application {

    @Inject
    @Named("icon path")
    private static String iconPath;

    @Inject
    @Named("build number")
    private static String buildNumber;

    private final Injector injector = Guice.createInjector(new MainModule(), new AGameModule(this));

    @Inject
    private Stage stage;

    private EnumMap<WindowType, AWindow> windowMap;

    @Inject
    private SceneMover sceneMover;

    {
        final Function<URL, FXMLLoader> makeFXMLLoader = url -> {
            final FXMLLoader result = new FXMLLoader(url);
            result.setControllerFactory(injector::getInstance);
            return result;
        };

        windowMap = new EnumMap<>(WindowType.class) {{
            put(WindowType.INITIALIZATION, new AWindow(makeFXMLLoader.apply(WindowType.INITIALIZATION.URL())));
            put(WindowType.MENU, new AWindow(makeFXMLLoader.apply(WindowType.MENU.URL())));
            put(WindowType.AUTHORIZATION, new AWindow(makeFXMLLoader.apply(WindowType.AUTHORIZATION.URL())));
            put(WindowType.PROFILE, new AWindow(makeFXMLLoader.apply(WindowType.PROFILE.URL())));
            put(WindowType.CHOICE_BONUS, new AWindow(makeFXMLLoader.apply(WindowType.CHOICE_BONUS.URL())));
            put(WindowType.FAST_CHOICE_HERO, new AWindow(makeFXMLLoader.apply(WindowType.FAST_CHOICE_HERO.URL())));
            put(WindowType.MATCHMAKING, new AWindow(makeFXMLLoader.apply(WindowType.MATCHMAKING.URL())));
        }};
    }

    private void stageInitialization() {
        stage.getIcons().add(new Image(iconPath));
        stage.setResizable(false);
        stage.setTitle(buildNumber);
        stage.show();
    }

    @Override
    public final void start(Stage virtualStage) throws Exception {
        stageInitialization();
        sceneMover.moveToScene(WindowType.INITIALIZATION);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Contract(pure = true)
    public final EnumMap<WindowType, AWindow> getWindowMap() {
        return windowMap;
    }
}