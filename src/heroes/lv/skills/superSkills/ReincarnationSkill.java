package heroes.lv.skills.superSkills;

import heroes.abstractHero.skills.swapSkills.AbstractSwapSkill;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import managment.battleManagement.BattleManager;
import managment.playerManagement.PlayerManager;

import java.util.Collections;
import java.util.List;

public final class ReincarnationSkill extends AbstractSwapSkill {

    private static final String NAME = "Reincarnation";

    private static final int RELOAD = 9;

    private static final int REQUIRED_LEVEL = 5;

    private static final double SKILL_COEFFICIENT = 1.0;

    private static final List<Double> SKILL_COEFFICIENTS = Collections.singletonList(SKILL_COEFFICIENT);

    public ReincarnationSkill(final ImageView sprite, final ImageView description, final List<Media> voiceList) {
        super(NAME, RELOAD, REQUIRED_LEVEL, SKILL_COEFFICIENTS
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
