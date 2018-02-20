package heroes.devourer.hero;

import heroes.abstractHero.hero.Hero;
import heroes.abstractHero.skills.Skill;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;

import java.util.List;

import static heroes.devourer.hero.DevourerProperties.*;

public final class Devourer extends Hero {

    public Devourer(final List<Skill> superSkills, final Skill swapSkill
            , final ImageView face, final List<Media> listOfAttackVoices, final List<Media> listOfTreatmentVoices) {
        super(NAME, START_ATTACK, START_TREATMENT, START_HIT_POINTS, START_SUPPLY_HEALTH, START_EXPERIENCE, START_LEVEL
                , getRequiredExperienceList(), getDamageList(), getTreatmentList(), getSupplyHealthList(), superSkills, swapSkill, face
                , listOfAttackVoices, listOfTreatmentVoices);
    }

    @Override
    public final void showAttackAnimation() {

    }

    @Override
    public final void showTreatmentAnimation() {

    }
}