package heroes.devourer.hero;

import heroes.abstractHero.hero.AHero;
import heroes.abstractHero.skills.Skill;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;

import java.util.List;

public final class Devourer extends AHero {

    Devourer(final String name, final Double attack, final Double treatment, final Double hitPoints, final Double supplyHealth
            , final Double currentExperience, final int levelHero, final List<Double> listOfRequiredExperience
            , final List<Double> listOfDamage, final List<Double> listOfTreatment, final List<Double> listOfSupplyHealth
            , final List<Skill> collectionOfSkills, final Skill swapSkill, final ImageView face, final List<Media> listOfAttackVoices
            , final List<Media> listOfTreatmentVoices) {
        super(name, attack, treatment, hitPoints, supplyHealth, currentExperience, levelHero, listOfRequiredExperience
                , listOfDamage, listOfTreatment, listOfSupplyHealth, collectionOfSkills, swapSkill, face, listOfAttackVoices
                , listOfTreatmentVoices);
    }

    @Override
    public final void showAttackAnimation() {

    }

    @Override
    public final void showTreatmentAnimation() {

    }
}
