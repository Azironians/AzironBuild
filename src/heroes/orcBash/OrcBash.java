package heroes.orcBash;

import bonus.bonuses.Bonus;
import heroes.abstractHero.AHero;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;

import java.util.List;

public  final class OrcBash extends AHero {
    OrcBash (final Double attack, final Double treatment, final Double hitPoints, final Double supplyHealth
            , final Double currentExperience, final int levelHero, final List<Double> listOfRequiredExperience
            , final List<Double> listOfDamage, final List<Double> listOfTreatment, final List<Double> listOfSupplyHealth
            , final List<Skill> collectionOfSkills, final Skill swapSkill, final ImageView face, final List<Media> listOfAttackVoices
            , final List<Media> listOfTreatmentVoices, final Presentation presentation
            , final List<Bonus> currentCollection) {
        super(attack, treatment, hitPoints, supplyHealth, currentExperience, levelHero, listOfRequiredExperience
                , listOfDamage, listOfTreatment, listOfSupplyHealth, collectionOfSkills, swapSkill, face, listOfAttackVoices
                , listOfTreatmentVoices, presentation, currentCollection);
    }

    @Override
    public void showAttackAnimation() {

    }

    @Override
    public void showTreatmentAnimation() {

    }
}
