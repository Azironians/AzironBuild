package bonus.bonuses;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public abstract class ExtendedBonus extends Bonus{

    protected Pane container;

    public ExtendedBonus(String name, int id, ImageView sprite) {
        super(name, id, sprite);
    }

    public final void installContainer(final Node... nodes){
        this.container = new Pane(){{
            getChildren().add(sprite);
            getChildren().addAll(nodes);
        }};
    }

    public final Pane getContainer(){
        return container;
    }
}
