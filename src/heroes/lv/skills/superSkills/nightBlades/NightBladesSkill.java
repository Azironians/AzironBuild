package heroes.lv.skills.superSkills.nightBlades;

import heroes.abstractHero.skills.abstractSkill.AbstractSkill;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import managment.battleManagement.BattleManager;
import managment.playerManagement.PlayerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static heroes.lv.skills.superSkills.nightBlades.NightBladesPropertySkill.*;

public final class NightBladesSkill extends AbstractSkill {

    private static final Logger log = LoggerFactory.getLogger(NightBladesSkill.class);

    public NightBladesSkill(final ImageView sprite, final ImageView description, final List<Media> voiceList) {
        super(NAME, RELOAD, REQUIRED_LEVEL, getSkillCoefficients()
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
