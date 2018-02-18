package heroes.orcBash.skills.superSkills.bash;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
@Singleton
public final class BashPropertySkill {

    @Inject
    @Named("BASH_NAME")
    static String NAME;

    @Inject
    @Named("BASH_RELOAD")
    static int RELOAD;

    @Inject
    @Named("BASH_REQUIRED_LEVEL")
    static int REQUIRED_LEVEL;

    @Inject
    @Named("BASH_DAMAGE_SKILL_COEFFICIENT")
    private static double DAMAGE_SKILL_COEFFICIENT;

    static List<Double> getSkillCoefficients(){
        return Collections.singletonList(DAMAGE_SKILL_COEFFICIENT);
    }
}
