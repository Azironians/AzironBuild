package heroes.lv.skills.superSkills;

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

    private static final String NAME = "NightBlades";

    private static final int RELOAD = 8;

    private static final int REQUIRED_LEVEL = 2;

    private static final double ATTACK_BOOST_SKILL_COEFFICIENT = 1.2;

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
