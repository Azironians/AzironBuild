package heroes.devourer.skills.superSkills.consuming;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import java.util.Collections;
import java.util.List;

@Singleton
public final class ConsumingPropertySkill {

    @Inject
    @Named("CONSUMING_NAME")
    static String NAME;

    @Inject
    @Named("CONSUMING_RELOAD")
    static int RELOAD;

    @Inject
    @Named("CONSUMING_REQUIRED_LEVEL")
    static int REQUIRED_LEVEL;

    @Inject
    @Named("CONSUMING_DAMAGE_SKILL_COEFFICIENT")
    private static double DAMAGE_SKILL_COEFFICIENT;

    static List<Double> getSkillCoefficients(){
        return Collections.singletonList(DAMAGE_SKILL_COEFFICIENT);
    }
}
