package bonus.devourerBonuses.bonuses.health.morphing;

import bonus.bonuses.ExtendedBonus;
import bonus.bonuses.subInterfaces.QuestBonus;
import heroes.abstractHero.hero.Hero;
import heroes.abstractHero.skills.Skill;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public final class HMorphing extends ExtendedBonus implements QuestBonus {

    private static final int START_COUNT = 0;

    private static final int END_COUNT = 7;

    private int count;

    private Text text;

    public HMorphing(String name, int id, ImageView sprite) {
        super(name, id, sprite);
        this.count = START_COUNT;
        this.text = new Text(START_COUNT + "/" + END_COUNT);
        installContainer(text);
    }

    @Override
    public final void use() {
        if (count + 1 >= END_COUNT) {
            changeRegenerationSkills();
            count = START_COUNT;
        } else {
            this.count++;
            text.setText(count + "/" + END_COUNT);
        }
    }

    private void changeRegenerationSkills(){
        final Hero hero = playerManager.getCurrentTeam().getCurrentPlayer().getCurrentHero();
        final List<Skill> skills = hero.getCollectionOfSkills();
        for (int i = 0; i < skills.size(); i++){
            if (skills.get(i).getName().equals("Regeneration")){
                skills.set(i, getMorphingSkillInstance());
            }
        }
    }

    private Skill getMorphingSkillInstance(){
        return new MorphingSkill(new ImageView(), new ImageView(), new ArrayList<>());
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