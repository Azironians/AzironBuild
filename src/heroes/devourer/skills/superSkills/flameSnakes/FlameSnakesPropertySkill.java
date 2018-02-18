package heroes.devourer.skills.superSkills.flameSnakes;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import java.util.Collections;
import java.util.List;

@Singleton
public final class FlameSnakesPropertySkill {

    @Inject
    @Named("FLAME_SNAKES_NAME")
    static String NAME;

    @Inject
    @Named("FLAME_SNAKES_RELOAD")
    static int RELOAD;

    @Inject
    @Named("FLAME_SNAKES_REQUIRED_LEVEL")
    static int REQUIRED_LEVEL;

    @Inject
    @Named("FLAME_SNAKES_DAMAGE_SKILL_COEFFICIENT")
    private static double DAMAGE_SKILL_COEFFICIENT;

    static List<Double> getSkillCoefficients(){
        return Collections.singletonList(DAMAGE_SKILL_COEFFICIENT);
    }
}