package heroes.orcBash.skills.swapSkills.healthGain;

import heroes.abstractHero.hero.Hero;
import heroes.abstractHero.skills.swapSkill.AbstractSimplifiedSkill;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import managment.battleManagement.BattleManager;
import managment.playerManagement.PlayerManager;

import java.util.List;

import static heroes.orcBash.skills.swapSkills.healthGain.HealthGainPropertySkill.*;

public final class HealthGainSkill extends AbstractSimplifiedSkill {

    public HealthGainSkill(final ImageView sprite, final ImageView description, final List<Media> voiceList) {
        super(NAME, SWAP_RELOAD, SWAP_REQUIRED_LEVEL, getSkillCoefficients(), sprite, description, voiceList);
    }

    @Override
    public final void use(final BattleManager battleManager, final PlayerManager playerManager) {
        final Hero currentHero = playerManager.getCurrentTeam().getCurrentPlayer().getCurrentHero();
        final Hero opponentHero = playerManager.getOpponentTeam().getCurrentPlayer().getCurrentHero();
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

