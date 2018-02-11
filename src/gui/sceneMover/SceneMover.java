package gui.sceneMover;

import controllers.Controller;
import gui.windows.AWindow;
import gui.windows.WindowType;
import javafx.stage.Stage;
import main.AGame;
import com.google.inject.Inject;
import javafx.scene.Scene;

public final class SceneMover {

    @Inject
    private AGame aGame;

    @Inject
    private Stage stage;

    public final void moveToScene(final WindowType windowType) {
        final AWindow aWindow = aGame.getWindowMap().get(windowType);
        final Scene scene = aWindow.getScene();
        final Controller controller = aWindow.getController();
        controller.appearance();
        stage.setScene(scene);
    }
}
