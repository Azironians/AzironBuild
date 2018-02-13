package heroes.devourer.hero;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import heroes.abstractHero.hero.AHero;
import heroes.abstractHero.skills.Skill;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;

import java.util.Arrays;
import java.util.List;

public final class Devourer extends AHero {

    //Name:
    @Inject @Named("DEVOURER_NAME") private static String NAME;

    //Characteristics:
    @Inject @Named("DEVOURER_START_ATTACK") private static double START_ATTACK;
    @Inject @Named("DEVOURER_START_TREATMENT") private static double START_TREATMENT;
    @Inject @Named("DEVOURER_START_HIT_POINTS") private static double START_HIT_POINTS;
    @Inject @Named("DEVOURER_START_SUPPLY_HEALTH") private static double START_SUPPLY_HEALTH;
    @Inject @Named("DEVOURER_START_EXPERIENCE") private static double START_EXPERIENCE;
    @Inject @Named("DEVOURER_START_LEVEL") private static int START_LEVEL;

    //Required level list:
    @Inject @Named("DEVOURER_EXP_LEVEL_2") private static double EXP_LEVEL_2;
    @Inject @Named("DEVOURER_EXP_LEVEL_3") private static double EXP_LEVEL_3;
    @Inject @Named("DEVOURER_EXP_LEVEL_4") private static double EXP_LEVEL_4;
    @Inject @Named("DEVOURER_EXP_LEVEL_5") private static double EXP_LEVEL_5;
    @Inject @Named("DEVOURER_EXP_LEVEL_6") private static double EXP_LEVEL_6;
    @Inject @Named("DEVOURER_EXP_LEVEL_7") private static double EXP_LEVEL_7;
    @Inject @Named("DEVOURER_EXP_LEVEL_8") private static double EXP_LEVEL_8;
    @Inject @Named("DEVOURER_EXP_LEVEL_9") private static double EXP_LEVEL_9;
    @Inject @Named("DEVOURER_EXP_LEVEL_10") private static double EXP_LEVEL_10;

    private static final List<Double> REQUIRED_EXPERIENCE_LIST = Arrays.asList(EXP_LEVEL_2, EXP_LEVEL_3, EXP_LEVEL_4
            , EXP_LEVEL_5, EXP_LEVEL_6, EXP_LEVEL_7, EXP_LEVEL_8, EXP_LEVEL_9, EXP_LEVEL_10);

    //Attack level boost:
    @Inject @Named("DEVOURER_DAMAGE_LEVEL_2") private static double DAMAGE_LEVEL_2;
    @Inject @Named("DEVOURER_DAMAGE_LEVEL_3") private static double DAMAGE_LEVEL_3;
    @Inject @Named("DEVOURER_DAMAGE_LEVEL_4") private static double DAMAGE_LEVEL_4;
    @Inject @Named("DEVOURER_DAMAGE_LEVEL_5") private static double DAMAGE_LEVEL_5;
    @Inject @Named("DEVOURER_DAMAGE_LEVEL_6") private static double DAMAGE_LEVEL_6;
    @Inject @Named("DEVOURER_DAMAGE_LEVEL_7") private static double DAMAGE_LEVEL_7;
    @Inject @Named("DEVOURER_DAMAGE_LEVEL_8") private static double DAMAGE_LEVEL_8;
    @Inject @Named("DEVOURER_DAMAGE_LEVEL_9") private static double DAMAGE_LEVEL_9;
    @Inject @Named("DEVOURER_DAMAGE_LEVEL_10") private static double DAMAGE_LEVEL_10;

    private static final List<Double> DAMAGE_LIST = Arrays.asList(DAMAGE_LEVEL_2, DAMAGE_LEVEL_3, DAMAGE_LEVEL_4
            , DAMAGE_LEVEL_5, DAMAGE_LEVEL_6, DAMAGE_LEVEL_7, DAMAGE_LEVEL_8, DAMAGE_LEVEL_9, DAMAGE_LEVEL_10);

    //Treatment level boost:
    @Inject @Named("DEVOURER_TREAT_LEVEL_2") private static double TREAT_LEVEL_2;
    @Inject @Named("DEVOURER_TREAT_LEVEL_3") private static double TREAT_LEVEL_3;
    @Inject @Named("DEVOURER_TREAT_LEVEL_4") private static double TREAT_LEVEL_4;
    @Inject @Named("DEVOURER_TREAT_LEVEL_5") private static double TREAT_LEVEL_5;
    @Inject @Named("DEVOURER_TREAT_LEVEL_6") private static double TREAT_LEVEL_6;
    @Inject @Named("DEVOURER_TREAT_LEVEL_7") private static double TREAT_LEVEL_7;
    @Inject @Named("DEVOURER_TREAT_LEVEL_8") private static double TREAT_LEVEL_8;
    @Inject @Named("DEVOURER_TREAT_LEVEL_9") private static double TREAT_LEVEL_9;
    @Inject @Named("DEVOURER_TREAT_LEVEL_10") private static double TREAT_LEVEL_10;

    private static final List<Double> TREATMENT_LIST = Arrays.asList(TREAT_LEVEL_2, TREAT_LEVEL_3, TREAT_LEVEL_4
            , TREAT_LEVEL_5, TREAT_LEVEL_6, TREAT_LEVEL_7, TREAT_LEVEL_8, TREAT_LEVEL_9, TREAT_LEVEL_10);

    //Healing level boost:
    @Inject @Named("DEVOURER_HP_LEVEL_2") private static double HP_LEVEL_2;
    @Inject @Named("DEVOURER_HP_LEVEL_3") private static double HP_LEVEL_3;
    @Inject @Named("DEVOURER_HP_LEVEL_4") private static double HP_LEVEL_4;
    @Inject @Named("DEVOURER_HP_LEVEL_5") private static double HP_LEVEL_5;
    @Inject @Named("DEVOURER_HP_LEVEL_6") private static double HP_LEVEL_6;
    @Inject @Named("DEVOURER_HP_LEVEL_7") private static double HP_LEVEL_7;
    @Inject @Named("DEVOURER_HP_LEVEL_8") private static double HP_LEVEL_8;
    @Inject @Named("DEVOURER_HP_LEVEL_9") private static double HP_LEVEL_9;
    @Inject @Named("DEVOURER_HP_LEVEL_10") private static double HP_LEVEL_10;

    private static final List<Double> SUPPLY_HEATH_LIST = Arrays.asList(HP_LEVEL_2, HP_LEVEL_3, HP_LEVEL_4, HP_LEVEL_5
            , HP_LEVEL_6, HP_LEVEL_7, HP_LEVEL_8, HP_LEVEL_9, HP_LEVEL_10);

    public Devourer(final List<Skill> superSkills, final Skill swapSkill
            , final ImageView face, final List<Media> listOfAttackVoices, final List<Media> listOfTreatmentVoices) {
        super(NAME, START_ATTACK, START_TREATMENT, START_HIT_POINTS, START_SUPPLY_HEALTH, START_EXPERIENCE, START_LEVEL
                , REQUIRED_EXPERIENCE_LIST, DAMAGE_LIST, TREATMENT_LIST, SUPPLY_HEATH_LIST, superSkills, swapSkill, face
                , listOfAttackVoices, listOfTreatmentVoices);
    }

    @Override
    public final void showAttackAnimation() {

    }

    @Override
    public final void showTreatmentAnimation() {

    }
}
