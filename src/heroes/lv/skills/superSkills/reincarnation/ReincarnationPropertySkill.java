package heroes.lv.skills.superSkills.reincarnation;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import java.util.Collections;
import java.util.List;

@Singleton
public final class ReincarnationPropertySkill {

    @Inject
    @Named("REINCARNATION_NAME")
    static String NAME;

    @Inject
    @Named("REINCARNATION_RELOAD")
    static int RELOAD;

    @Inject
    @Named("REINCARNATION_REQUIRED_LEVEL")
    static int REQUIRED_LEVEL;

    @Inject
    @Named("REINCARNATION_SKILL_COEFFICIENT")
    private static double SKILL_COEFFICIENT;

    static List<Double> getSkillCoefficients(){
        return Collections.singletonList(SKILL_COEFFICIENT);
    }
}
