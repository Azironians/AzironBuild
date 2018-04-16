package bonus.devourerBonuses.bonuses.attack;

import bonus.bonuses.ExtendedBonus;
import bonus.bonuses.subInterfaces.QuestBonus;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public final class AUpgradeHooks extends ExtendedBonus implements QuestBonus {

    private static final int START_COUNT = 0;

    private static final int END_COUNT = 4;

    private static final double ATTACK_BOOST = 10;

    private int count;

    private Text text;

    public AUpgradeHooks(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
        this.count = START_COUNT;
        this.text = new Text(START_COUNT + "/" + END_COUNT);
        installContainer(text);
    }

    @Override
    public final void use() {
        if (count + 1 >= END_COUNT){
            final Hero currentHero = playerManager.getCurrentTeam().getCurrentPlayer().getCurrentHero();
            currentHero.setAttack(currentHero.getAttack() + ATTACK_BOOST);
            count = START_COUNT;
        } else {
            this.count++;
            text.setText(count + "/" + END_COUNT);
        }
    }

    @Override
    public final int getProgress() {
        return count;
    }

    @Override
    public final void setProgress(final int progress) {
        this.count = progress;
    }
}