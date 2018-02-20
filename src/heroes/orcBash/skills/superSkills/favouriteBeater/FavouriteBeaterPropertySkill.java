package heroes.orcBash.skills.superSkills.favouriteBeater;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
@Singleton
public final class FavouriteBeaterPropertySkill {

    @Inject
    @Named("BEATER_NAME")
    static String NAME;

    @Inject
    @Named("BEATER_RELOAD")
    static int RELOAD;

    @Inject
    @Named("BEATER_REQUIRED_LEVEL")
    static int REQUIRED_LEVEL;

    @Inject
    @Named("BEATER_DAMAGE_SKILL_COEFFICIENT")
    private static double DAMAGE_SKILL_COEFFICIENT;

    @Inject
    @Named("BEATER_FIX_HEALTH_SUPPLY_COEFFICIENT")
    private static double FIX_HEALTH_SUPPLY_COEFFICIENT;

    static List<Double> getSkillCoefficients(){
        return Arrays.asList(DAMAGE_SKILL_COEFFICIENT, FIX_HEALTH_SUPPLY_COEFFICIENT);
    }
}
