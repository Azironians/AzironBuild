package heroes.lv.skills.superSkills.nightBlades;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import java.util.Collections;
import java.util.List;

@Singleton
public final class NightBladesPropertySkill {

    @Inject
    @Named("NIGHT_BLADES_NAME")
    static String NAME;

    @Inject
    @Named("NIGHT_BLADES_RELOAD")
    static int RELOAD;

    @Inject
    @Named("NIGHT_BLADES_REQUIRED_LEVEL")
    static int REQUIRED_LEVEL;

    @Inject
    @Named("NIGHT_BLADES_ATTACK_BOOST_SKILL_COEFFICIENT")
    private static double ATTACK_BOOST_SKILL_COEFFICIENT;

    static List<Double> getSkillCoefficients(){
        return Collections.singletonList(ATTACK_BOOST_SKILL_COEFFICIENT);
    }
}
