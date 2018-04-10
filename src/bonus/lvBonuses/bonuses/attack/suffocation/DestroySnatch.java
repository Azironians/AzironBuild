package bonus.lvBonuses.bonuses.attack.suffocation;

import bonus.bonuses.Bonus;
import javafx.scene.image.ImageView;

public final class DestroySnatch extends Bonus {

    private static final String NAME = "DestroySnatch";

    static final int ID = 22; //for example;

    private static final ImageView imageView = new ImageView();

    DestroySnatch() {
        super(NAME, ID, imageView);
    }

    @Override
    public final void use() {
        //don't worry! Suffocation bonus makes all work =)
    }
}