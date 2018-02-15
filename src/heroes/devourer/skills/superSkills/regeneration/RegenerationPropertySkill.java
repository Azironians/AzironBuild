package heroes.devourer.skills.superSkills.regeneration;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.util.Collections;
import java.util.List;

public final class RegenerationPropertySkill {

    @Inject
    @Named("REGENERATION_NAME")
    static final String NAME = "Regeneration";

    @Inject
    @Named("REGENERATION_RELOAD")
    static int RELOAD;

    @Inject
    @Named("REGENERATION_REQUIRED_LEVEL")
    static int REQUIRED_LEVEL;

    @Inject
    @Named("REGENERATION_HEALING_SKILL_COEFFICIENT")
    private static double HEALING_SKILL_COEFFICIENT;

    static final List<Double> SKILL_COEFFICIENTS = Collections.singletonList(HEALING_SKILL_COEFFICIENT);
}
