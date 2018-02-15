package heroes.lv.skills.superSkills;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import heroes.abstractHero.skills.swapSkills.AbstractSwapSkill;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import managment.battleManagement.BattleManager;
import managment.playerManagement.PlayerManager;

import java.util.Collections;
import java.util.List;

public final class ReincarnationSkill extends AbstractSwapSkill {

    @Inject
    @Named("REINCARNATION_NAME")
    private static String NAME = "Reincarnation";

    @Inject
    @Named("REINCARNATION_RELOAD")
    private static int RELOAD;

    @Inject
    @Named("REINCARNATION_REQUIRED_LEVEL")
    private static int REQUIRED_LEVEL;

    @Inject
    @Named("REINCARNATION_SKILL_COEFFICIENT")
    private static double SKILL_COEFFICIENT;

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
