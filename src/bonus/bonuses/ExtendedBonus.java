package bonus.bonuses;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public abstract class ExtendedBonus extends Bonus{

    private Pane container;

    public ExtendedBonus(final String name, final int id, ImageView sprite) {
        super(name, id, sprite);
    }

    protected final void installContainer(final Node... nodes){
        this.container = new Pane(){{
            getChildren().add(sprite);
            getChildren().addAll(nodes);
        }};
    }

    public final Pane getContainer(){
        return container;
    }
}
