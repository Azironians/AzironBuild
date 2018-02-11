package gui.windows;

import controllers.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import org.jetbrains.annotations.Contract;

import java.io.IOException;

public final class AWindow {
    private Scene scene;
    private Controller controller;

    public AWindow(final FXMLLoader fxmlLoader) {
        try {
            final Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
            //install cursor:
            final String cursorPath = "file:src\\resources\\cursor\\cursor.png";
            final ImageCursor imageCursor = new ImageCursor(new Image(cursorPath));
            scene.setCursor(imageCursor);
            this.scene = scene;
            this.controller = fxmlLoader.getController();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    @Contract(pure = true)
    public final Scene getScene() {
        return scene;
    }

    @Contract(pure = true)
    public final Controller getController() {
        return controller;
    }
}
