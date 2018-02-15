package heroes.orcBash.skills.swapSkills;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import heroes.abstractHero.hero.Hero;
import heroes.abstractHero.skills.swapSkills.AbstractSwapSkill;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import managment.battleManagement.BattleManager;
import managment.playerManagement.PlayerManager;

import java.util.Collections;
import java.util.List;

public final class HealthGainSkill extends AbstractSwapSkill {

    @Inject
    @Named("HEALTH_GAIN_NAME")
    private static String NAME;

    @Inject
    @Named("HEALTH_GAIN_SWAP_RELOAD")
    private static int SWAP_RELOAD;

    @Inject
    @Named("HEALTH_GAIN_SWAP_REQUIRED_LEVEL")
    private static int SWAP_REQUIRED_LEVEL;

    @Inject
    @Named("HEALTH_GAIN_SWAP_SKILL_COEFFICIENT")
    private static double SWAP_SKILL_COEFFICIENT;

    private static final List<Double> SWAP_SKILL_COEFFICIENTS = Collections.singletonList(SWAP_SKILL_COEFFICIENT);

    public HealthGainSkill(final ImageView sprite, final ImageView description, final List<Media> voiceList) {
        super(NAME, SWAP_RELOAD, SWAP_REQUIRED_LEVEL, SWAP_SKILL_COEFFICIENTS, sprite, description, voiceList);
    }

    @Override
    public final void use(final BattleManager battleManager, final PlayerManager playerManager) {
        final Hero currentHero = playerManager.getCurrentTeam().getCurrentPlayer().getHero();
        final Hero opponentHero = playerManager.getOpponentATeam().getCurrentPlayer().getHero();
        final int levelComparison = opponentHero.getLevel() - currentHero.getLevel();
        final double skillCoefficient = levelComparison > 0 ? (levelComparison + 1) * coefficients.get(0)
                : coefficients.get(0);
        final double healthBoost = currentHero.getAttack() * skillCoefficient;
        currentHero.setHealthSupply(currentHero.getHealthSupply() + healthBoost);
        currentHero.setHitPoints(currentHero.getHitPoints() + healthBoost);
    }

    @Override
    public final void showAnimation() {

    }
}

