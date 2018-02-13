package heroes.abstractHero.presentation;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import javafx.animation.FadeTransition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.util.Duration;

import java.util.List;

public final class Presentation {

    @Inject @Named("WIDTH")
    private static int WIDTH;

    @Inject @Named("HEIGHT")
    private static int HEIGHT;

    @Inject @Named("START_OPACITY")
    private static int START_OPACITY;

    @Inject @Named("ANIMATION_TIME")
    private static int ANIMATION_TIME;

    //Info:
    private String deckName;
    private int deckPriority;

    //Graphic:
    private final Pane container;
    private final ImageView backGround;
    private final List<Media> listOfPresentationMedia;

    public Presentation(final ImageView backGround, final List<Media> listOfPresentationMedia, final Pane container) {
        backGround.setFitWidth(WIDTH);
        backGround.setFitHeight(HEIGHT);
        container.getChildren().add(backGround);
        container.setVisible(false);
        container.setOpacity(START_OPACITY);
        this.backGround = backGround;
        this.listOfPresentationMedia = listOfPresentationMedia;
        this.container = container;
    }

    public final void showPresentation() {
        this.container.setVisible(true);
        final FadeTransition fadeTransition = new FadeTransition(Duration.seconds(ANIMATION_TIME), container);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    public final void hidePresentation() {
        final FadeTransition fadeTransition = new FadeTransition(Duration.seconds(ANIMATION_TIME), container);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished(end -> this.container.setVisible(false));
        fadeTransition.play();
    }

    //Getters && setters:
    public final ImageView getBackGround() {
        return backGround;
    }

    public final List<Media> getPresentationMediaList() {
        return listOfPresentationMedia;
    }

    public final Pane getContainer() {
        return container;
    }

    public final String getDeckName() {
        return deckName;
    }

    public final void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    public final int getDeckPriority() {
        return deckPriority;
    }

    public final void setDeckPriority(int deckPriority) {
        this.deckPriority = deckPriority;
    }

    public final void setDeckInfo(final String deckName, final int deckPriority) {
        this.deckName = deckName;
        this.deckPriority = deckPriority;
    }
}
