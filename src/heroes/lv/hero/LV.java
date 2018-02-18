package heroes.lv.hero;

import heroes.abstractHero.hero.Hero;
import heroes.abstractHero.skills.Skill;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;

import java.util.List;

import static heroes.lv.hero.LVProperties.*;

public final class LV extends Hero {

    public LV(final List<Skill> superSkills, final Skill swapSkill
            , final ImageView face, final List<Media> listOfAttackVoices, final List<Media> listOfTreatmentVoices) {
        super(NAME, START_ATTACK, START_TREATMENT, START_HIT_POINTS, START_SUPPLY_HEALTH, START_EXPERIENCE, START_LEVEL
                , getRequiredExperienceList(), getDamageList(), getTreatmentList(), getSupplyHealthList(), superSkills, swapSkill, face
                , listOfAttackVoices, listOfTreatmentVoices);
    }

    @Override
    public void showAttackAnimation() {

    }

    @Override
    public void showTreatmentAnimation() {

    }
}
