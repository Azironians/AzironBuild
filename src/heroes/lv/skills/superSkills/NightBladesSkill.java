package heroes.lv.skills.superSkills;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import heroes.abstractHero.skills.abstractSkill.AbstractSkill;
import heroes.devourer.skills.superSkills.FlameSnakesSkill;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import managment.battleManagement.BattleManager;
import managment.playerManagement.PlayerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

public final class NightBladesSkill extends AbstractSkill {

    private static final Logger log = LoggerFactory.getLogger(FlameSnakesSkill.class);

    @Inject
    @Named("NIGHT_BLADES_NAME")
    private static String NAME;

    @Inject
    @Named("NIGHT_BLADES_RELOAD")
    private static int RELOAD;

    @Inject
    @Named("NIGHT_BLADES_REQUIRED_LEVEL")
    private static int REQUIRED_LEVEL;

    @Inject
    @Named("NIGHT_BLADES_ATTACK_BOOST_SKILL_COEFFICIENT")
    private static double ATTACK_BOOST_SKILL_COEFFICIENT;

    private static final List<Double> SKILL_COEFFICIENTS = Collections.singletonList(ATTACK_BOOST_SKILL_COEFFICIENT);

    public NightBladesSkill(final ImageView sprite, final ImageView description, final List<Media> voiceList) {
        super(NAME, RELOAD, REQUIRED_LEVEL, SKILL_COEFFICIENTS
                , sprite, description, voiceList);
    }

    @Override
    public final void use(final BattleManager battleManager, final PlayerManager playerManager) {
        final double attackValue = parent.getAttack() * coefficients.get(0);
        parent.setAttack(attackValue);
    }

    @Override
    public final void showAnimation() {

    }
}
