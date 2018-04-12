package bonus.lvBonuses.bonuses.attack.hunt;

import bonus.bonuses.Bonus;
import javafx.scene.image.ImageView;

public final class StepBack extends Bonus {

    private static final String name = "StepBack";

    static final int ID = -1;

    private static final ImageView imageView = new ImageView(); //not configure

    public StepBack() {
        super(name, ID, imageView);
    }

    @Override
    public final void use() {
        //Hunt bonus will make all work
    }
}
