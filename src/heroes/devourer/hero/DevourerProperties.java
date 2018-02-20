package heroes.devourer.hero;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import java.util.Arrays;
import java.util.List;
@Singleton
public final class DevourerProperties {
    //Name:
    @Inject
    @Named("DEVOURER_NAME")
    static String NAME;

    //Characteristics:
    @Inject
    @Named("DEVOURER_START_ATTACK")
    static double START_ATTACK;

    @Inject
    @Named("DEVOURER_START_TREATMENT")
    static double START_TREATMENT;

    @Inject
    @Named("DEVOURER_START_HIT_POINTS")
    static double START_HIT_POINTS;

    @Inject
    @Named("DEVOURER_START_SUPPLY_HEALTH")
    static double START_SUPPLY_HEALTH;

    @Inject
    @Named("DEVOURER_START_EXPERIENCE")
    static double START_EXPERIENCE;

    @Inject
    @Named("DEVOURER_START_LEVEL")
    static int START_LEVEL;

    //Required level list:
    @Inject
    @Named("DEVOURER_EXP_LEVEL_2")
    private static double EXP_LEVEL_2;

    @Inject
    @Named("DEVOURER_EXP_LEVEL_3")
    private static double EXP_LEVEL_3;

    @Inject
    @Named("DEVOURER_EXP_LEVEL_4")
    private static double EXP_LEVEL_4;

    @Inject
    @Named("DEVOURER_EXP_LEVEL_5")
    private static double EXP_LEVEL_5;

    @Inject
    @Named("DEVOURER_EXP_LEVEL_6")
    private static double EXP_LEVEL_6;

    @Inject
    @Named("DEVOURER_EXP_LEVEL_7")
    private static double EXP_LEVEL_7;

    @Inject
    @Named("DEVOURER_EXP_LEVEL_8")
    private static double EXP_LEVEL_8;

    @Inject
    @Named("DEVOURER_EXP_LEVEL_9")
    private static double EXP_LEVEL_9;

    @Inject
    @Named("DEVOURER_EXP_LEVEL_10")
    private static double EXP_LEVEL_10;

    static List<Double> getRequiredExperienceList(){
        return  Arrays.asList(EXP_LEVEL_2, EXP_LEVEL_3, EXP_LEVEL_4
                , EXP_LEVEL_5, EXP_LEVEL_6, EXP_LEVEL_7, EXP_LEVEL_8, EXP_LEVEL_9, EXP_LEVEL_10);
    }

    //Attack level boost:
    @Inject
    @Named("DEVOURER_DAMAGE_LEVEL_2")
    private static double DAMAGE_LEVEL_2;

    @Inject
    @Named("DEVOURER_DAMAGE_LEVEL_3")
    private static double DAMAGE_LEVEL_3;

    @Inject
    @Named("DEVOURER_DAMAGE_LEVEL_4")
    private static double DAMAGE_LEVEL_4;

    @Inject
    @Named("DEVOURER_DAMAGE_LEVEL_5")
    private static double DAMAGE_LEVEL_5;

    @Inject
    @Named("DEVOURER_DAMAGE_LEVEL_6")
    private static double DAMAGE_LEVEL_6;

    @Inject
    @Named("DEVOURER_DAMAGE_LEVEL_7")
    private static double DAMAGE_LEVEL_7;

    @Inject
    @Named("DEVOURER_DAMAGE_LEVEL_8")
    private static double DAMAGE_LEVEL_8;

    @Inject
    @Named("DEVOURER_DAMAGE_LEVEL_9")
    private static double DAMAGE_LEVEL_9;

    @Inject
    @Named("DEVOURER_DAMAGE_LEVEL_10")
    private static double DAMAGE_LEVEL_10;

    static List<Double> getDamageList(){
        return Arrays.asList(DAMAGE_LEVEL_2, DAMAGE_LEVEL_3, DAMAGE_LEVEL_4
                , DAMAGE_LEVEL_5, DAMAGE_LEVEL_6, DAMAGE_LEVEL_7, DAMAGE_LEVEL_8, DAMAGE_LEVEL_9, DAMAGE_LEVEL_10);
    }

    //Treatment level boost:
    @Inject
    @Named("DEVOURER_TREAT_LEVEL_2")
    private static double TREAT_LEVEL_2;

    @Inject
    @Named("DEVOURER_TREAT_LEVEL_3")
    private static double TREAT_LEVEL_3;

    @Inject
    @Named("DEVOURER_TREAT_LEVEL_4")
    private static double TREAT_LEVEL_4;

    @Inject
    @Named("DEVOURER_TREAT_LEVEL_5")
    private static double TREAT_LEVEL_5;

    @Inject
    @Named("DEVOURER_TREAT_LEVEL_6")
    private static double TREAT_LEVEL_6;

    @Inject
    @Named("DEVOURER_TREAT_LEVEL_7")
    private static double TREAT_LEVEL_7;

    @Inject
    @Named("DEVOURER_TREAT_LEVEL_8")
    private static double TREAT_LEVEL_8;

    @Inject
    @Named("DEVOURER_TREAT_LEVEL_9")
    private static double TREAT_LEVEL_9;

    @Inject
    @Named("DEVOURER_TREAT_LEVEL_10")
    private static double TREAT_LEVEL_10;

    static List<Double> getTreatmentList(){
        return Arrays.asList(TREAT_LEVEL_2, TREAT_LEVEL_3, TREAT_LEVEL_4
                , TREAT_LEVEL_5, TREAT_LEVEL_6, TREAT_LEVEL_7, TREAT_LEVEL_8, TREAT_LEVEL_9, TREAT_LEVEL_10);
    }

    //Healing level boost:
    @Inject
    @Named("DEVOURER_HP_LEVEL_2")
    private static double HP_LEVEL_2;

    @Inject
    @Named("DEVOURER_HP_LEVEL_3")
    private static double HP_LEVEL_3;

    @Inject
    @Named("DEVOURER_HP_LEVEL_4")
    private static double HP_LEVEL_4;

    @Inject
    @Named("DEVOURER_HP_LEVEL_5")
    private static double HP_LEVEL_5;

    @Inject
    @Named("DEVOURER_HP_LEVEL_6")
    private static double HP_LEVEL_6;

    @Inject
    @Named("DEVOURER_HP_LEVEL_7")
    private static double HP_LEVEL_7;

    @Inject
    @Named("DEVOURER_HP_LEVEL_8")
    private static double HP_LEVEL_8;

    @Inject
    @Named("DEVOURER_HP_LEVEL_9")
    private static double HP_LEVEL_9;

    @Inject
    @Named("DEVOURER_HP_LEVEL_10")
    private static double HP_LEVEL_10;

    static List<Double> getSupplyHealthList(){
        return Arrays.asList(HP_LEVEL_2, HP_LEVEL_3, HP_LEVEL_4, HP_LEVEL_5
                , HP_LEVEL_6, HP_LEVEL_7, HP_LEVEL_8, HP_LEVEL_9, HP_LEVEL_10);
    }
}