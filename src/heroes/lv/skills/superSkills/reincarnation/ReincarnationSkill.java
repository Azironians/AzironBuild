package heroes.lv.skills.superSkills.reincarnation;

import heroes.abstractHero.skills.abstractSkill.AbstractSkill;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import managment.battleManagement.BattleManager;
import managment.playerManagement.PlayerManager;

import java.util.List;

import static heroes.lv.skills.superSkills.reincarnation.ReincarnationPropertySkill.*;

public final class ReincarnationSkill extends AbstractSkill {

    public ReincarnationSkill(final ImageView sprite, final ImageView description, final List<Media> voiceList) {
        super(NAME, RELOAD, REQUIRED_LEVEL, getSkillCoefficients()
                , sprite, description, voiceList);
    }

    @Override
    public final void use(final BattleManager battleManager, final PlayerManager playerManager) {
            final double hitPoints = parent.getHitPoints();
            parent.setHitPoints(Math.abs(hitPoints) * coefficients.get(0));
    }

    @Override
    public final void showAnimation() {

    }
}
