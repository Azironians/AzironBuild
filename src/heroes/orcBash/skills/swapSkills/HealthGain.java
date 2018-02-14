package heroes.orcBash.skills.swapSkills;

import heroes.abstractHero.hero.AHero;
import heroes.abstractHero.skills.swapSkills.AbstractSwapSkill;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import managment.battleManagement.BattleManager;
import managment.playerManagement.PlayerManager;

import java.util.Collections;
import java.util.List;

public final class HealthGain extends AbstractSwapSkill {

    private static final String NAME = "HealthGain";

    private static final int SWAP_RELOAD = 5;

    private static final int SWAP_REQUIRED_LEVEL = 1;

    private static final double SWAP_SKILL_COEFFICIENT = 1.0;

    private static final List<Double> SWAP_SKILL_COEFFICIENTS = Collections.singletonList(SWAP_SKILL_COEFFICIENT);

    public HealthGain(final ImageView sprite, final ImageView description, final List<Media> voiceList) {
        super(NAME, SWAP_RELOAD, SWAP_REQUIRED_LEVEL, SWAP_SKILL_COEFFICIENTS, sprite, description, voiceList);
    }

    @Override
    public final void use(final BattleManager battleManager, final PlayerManager playerManager) {
        final AHero currentHero = playerManager.getCurrentTeam().getCurrentPlayer().getHero();
        final AHero opponentHero = playerManager.getOpponentATeam().getCurrentPlayer().getHero();
        final int levelComparison = opponentHero.getLevel() - currentHero.getLevel();
        final double skillCoefficient =  levelComparison > 0 ? (levelComparison + 1) * coefficients.get(0)
                : coefficients.get(0);
        final double healthBoost = currentHero.getAttack() * skillCoefficient;
        currentHero.setHealthSupply(currentHero.getHealthSupply() + healthBoost);
        currentHero.setHitPoints(currentHero.getHitPoints() + healthBoost);
    }

    @Override
    public final void showAnimation() {

    }
}

