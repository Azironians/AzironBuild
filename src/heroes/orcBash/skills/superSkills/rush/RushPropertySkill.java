package heroes.orcBash.skills.superSkills.rush;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import java.util.Collections;
import java.util.List;
@Singleton
public final class RushPropertySkill {

    @Inject
    @Named("RUSH_NAME")
    static String NAME;

    @Inject
    @Named("RUSH_RELOAD")
    static int RELOAD;

    @Inject
    @Named("RUSH_REQUIRED_LEVEL")
    static int REQUIRED_LEVEL;

    @Inject
    @Named("RUSH_DAMAGE_SKILL_COEFFICIENT")
    private static double DAMAGE_SKILL_COEFFICIENT;

    static List<Double> getSkillCoefficients(){
        return Collections.singletonList(DAMAGE_SKILL_COEFFICIENT);
    }
}
