package heroes.devourer.skills.swapSkills.depletion;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.util.Collections;
import java.util.List;

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

    static final List<Double> SKILL_COEFFICIENTS = Collections.singletonList(SKILL_COEFFICIENT);
}
