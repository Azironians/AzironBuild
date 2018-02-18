package heroes.lv.skills.superSkills.cannibalism;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import java.util.Arrays;
import java.util.List;
@Singleton
public final class CannibalismPropertySkill {

    @Inject
    @Named("CANNIBALISM_NAME")
    static String NAME;

    @Inject
    @Named("CANNIBALISM_RELOAD")
    static int RELOAD;

    @Inject
    @Named("CANNIBALISM_REQUIRED_LEVEL")
    static int REQUIRED_LEVEL;

    @Inject
    @Named("CANNIBALISM_DAMAGE_SKILL_COEFFICIENT")
    private static double DAMAGE_SKILL_COEFFICIENT;

    @Inject
    @Named("CANNIBALISM_HEALING_SKILL_COEFFICIENT")
    private static double HEALING_SKILL_COEFFICIENT;

    static List<Double> getSkillCoefficients(){
        return Arrays.asList(DAMAGE_SKILL_COEFFICIENT, HEALING_SKILL_COEFFICIENT);
    }
}
