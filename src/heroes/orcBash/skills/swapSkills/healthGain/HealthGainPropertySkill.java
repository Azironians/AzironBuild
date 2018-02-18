package heroes.orcBash.skills.swapSkills.healthGain;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import java.util.Collections;
import java.util.List;

@Singleton
public final class HealthGainPropertySkill {

    @Inject
    @Named("HEALTH_GAIN_NAME")
    static String NAME;

    @Inject
    @Named("HEALTH_GAIN_SWAP_RELOAD")
    static int SWAP_RELOAD;

    @Inject
    @Named("HEALTH_GAIN_SWAP_REQUIRED_LEVEL")
    static int SWAP_REQUIRED_LEVEL;

    @Inject
    @Named("HEALTH_GAIN_SWAP_SKILL_COEFFICIENT")
    private static double SWAP_SKILL_COEFFICIENT;

    static List<Double> getSkillCoefficients(){
        return Collections.singletonList(SWAP_SKILL_COEFFICIENT);
    }
}
