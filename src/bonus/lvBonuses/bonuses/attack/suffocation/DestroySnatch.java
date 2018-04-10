package bonus.lvBonuses.bonuses.attack.suffocation;

import bonus.bonuses.Bonus;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import javafx.util.Pair;

import java.util.Set;
import java.util.Stack;

public final class DestroySnatch extends Bonus {

    private static final String NAME = "";

    private static final int ID = 22; //for example;

    private static final ImageView imageView = new ImageView();

    private final Pair<Hero, Stack<Integer>> heroStackPair;

    DestroySnatch(final Pair<Hero, Stack<Integer>> heroStackPair) {
        super(NAME, ID, imageView);
        this.heroStackPair = heroStackPair;
    }

    @Override
    public final void use() {
        final Hero hero = playerManager.getCurrentTeam().getCurrentPlayer().getCurrentHero();

    }
}