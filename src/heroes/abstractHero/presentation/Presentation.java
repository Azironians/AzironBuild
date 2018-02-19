package heroes.abstractHero.presentation;

import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.List;

import static heroes.abstractHero.presentation.PresentationProperties.*;

public final class Presentation {

    //Info:
    private Text deckNameText;
    private Text deckPriorityText;

    //Graphic:
    private final Pane container;
    private final ImageView backGround;
    private final List<Media> listOfPresentationMedia;

    public Presentation(final String deckName, final int deckPriority
            , final ImageView backGround, final List<Media> listOfPresentationMedia) {
        backGround.setFitWidth(WIDTH);
        backGround.setFitHeight(HEIGHT);
        this.backGround = backGround;
        this.listOfPresentationMedia = listOfPresentationMedia;
        this.deckNameText = new Text(deckName);
        this.deckPriorityText = new Text(deckPriority + "");
        this.container = new Pane(){{
            final ObservableList<Node> elements = getChildren();
            elements.add(backGround);
            elements.add(deckNameText);
            elements.add(deckPriorityText);
//            setOpacity(START_OPACITY);
        }};
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
    public final Text getDeckNameText() {
        return deckNameText;
    }

    public final Pane getContainer() {
        return container;
    }
}