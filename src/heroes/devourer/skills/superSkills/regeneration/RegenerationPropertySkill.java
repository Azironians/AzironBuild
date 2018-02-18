package heroes.devourer.skills.superSkills.regeneration;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import java.util.Collections;
import java.util.List;

@Singleton
public final class RegenerationPropertySkill {

    @Inject
    @Named("REGENERATION_NAME")
    static String NAME;

    @Inject
    @Named("REGENERATION_RELOAD")
    static int RELOAD;

    @Inject
    @Named("REGENERATION_REQUIRED_LEVEL")
    static int REQUIRED_LEVEL;

    @Inject
    @Named("REGENERATION_HEALING_SKILL_COEFFICIENT")
    private static double HEALING_SKILL_COEFFICIENT;

    static List<Double> getSkillCoefficients(){
        return Collections.singletonList(HEALING_SKILL_COEFFICIENT);
    }
}
