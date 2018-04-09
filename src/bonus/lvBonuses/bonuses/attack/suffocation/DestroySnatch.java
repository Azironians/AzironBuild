package bonus.lvBonuses.bonuses.attack.suffocation;

import bonus.bonuses.Bonus;
import javafx.scene.image.ImageView;

import java.util.Stack;

public final class DestroySnatch extends Bonus {

    private static final String NAME = "";

    private static final int ID = 22; //for example;

    private static final ImageView imageView = new ImageView();

    private final Stack<Integer> turnDamageStack;

    public DestroySnatch(final Stack<Integer> turnDamageStack) {
        super(NAME, ID, imageView);
        this.turnDamageStack = turnDamageStack;
    }

    @Override
    public final void use() {
        this.turnDamageStack.pop();
    }
}