package heroes.devourer.skills.swapSkills.depletion;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import java.util.Collections;
import java.util.List;

@Singleton
public final class DepletionPropertySkill {

    @Inject
    @Named("DEPLETION_NAME")
    static String NAME;

    @Inject
    @Named("DEPLETION_RELOAD")
    static int RELOAD;

    @Inject
    @Named("DEPLETION_REQUIRED_LEVEL")
    static int REQUIRED_LEVEL;

    @Inject
    @Named("DEPLETION_SKILL_COEFFICIENT")
    private static double SKILL_COEFFICIENT;

    static List<Double> getSkillCoefficients(){
        return Collections.singletonList(SKILL_COEFFICIENT);
    }
}
