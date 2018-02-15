package heroes.orcBash.skills.swapSkills.healthGain;

import heroes.abstractHero.hero.Hero;
import heroes.abstractHero.skills.swapSkills.AbstractSwapSkill;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import managment.battleManagement.BattleManager;
import managment.playerManagement.PlayerManager;

import java.util.List;

import static heroes.orcBash.skills.swapSkills.healthGain.HealthGainPropertySkill.*;

public final class HealthGainSkill extends AbstractSwapSkill {

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

