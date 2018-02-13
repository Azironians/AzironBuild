package heroes.devourer.hero;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import heroes.abstractHero.hero.AHero;
import heroes.abstractHero.skills.Skill;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;

import java.util.List;

public final class Devourer extends AHero {

    //Start characteristics:
    @Inject
    @Named("DEVOURER_NAME")
    private static String NAME;

    @Inject
    @Named("DEVOURER_START_ATTACK")
    private static double START_ATTACK;

    @Inject
    @Named("DEVOURER_START_TREATMENT")
    private static double START_TREATMENT;

    @Inject
    @Named("DEVOURER_START_HIT_POINTS")
    private static double START_HIT_POINTS;

    @Inject
    @Named("DEVOURER_START_SUPPLY_HEALTH")
    private static double START_SUPPLY_HEALTH;

    @Inject
    @Named("DEVOURER_START_EXPERIENCE")
    private static double START_EXPERIENCE;

    @Inject
    @Named("DEVOURER_START_LEVEL")
    private static int START_LEVEL;

    Devourer(final List<Double> listOfRequiredExperience
            , final List<Double> listOfDamage, final List<Double> listOfTreatment, final List<Double> listOfSupplyHealth
            , final List<Skill> collectionOfSkills, final Skill swapSkill, final ImageView face, final List<Media> listOfAttackVoices
            , final List<Media> listOfTreatmentVoices) {
        super(NAME, START_ATTACK, START_TREATMENT, START_HIT_POINTS, START_SUPPLY_HEALTH, START_EXPERIENCE, START_LEVEL
                , listOfRequiredExperience, listOfDamage, listOfTreatment, listOfSupplyHealth, collectionOfSkills, swapSkill, face, listOfAttackVoices
                , listOfTreatmentVoices);
    }

    @Override
    public final void showAttackAnimation() {

    }

    @Override
    public final void showTreatmentAnimation() {

    }
}
