package heroes.devourer.skills.swapSkills.depletion;

import heroes.abstractHero.hero.Hero;
import heroes.abstractHero.skills.swapSkill.AbstractSimplifiedSkill;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import managment.battleManagement.BattleManager;
import managment.playerManagement.Player;
import managment.playerManagement.PlayerManager;

import java.util.List;

import static heroes.devourer.skills.swapSkills.depletion.DepletionPropertySkill.*;

public final class DepletionSkill extends AbstractSimplifiedSkill {

    public DepletionSkill(final ImageView sprite, final ImageView description, final List<Media> voiceList) {
        super(NAME, RELOAD, REQUIRED_LEVEL, getSkillCoefficients()
                , sprite, description, voiceList);
    }

    @Override
    public final void use(final BattleManager battleManager, final PlayerManager playerManager) {
        final Player opponentPlayer = playerManager.getOpponentTeam().getCurrentPlayer();
        final Hero currentHero = playerManager.getCurrentTeam().getCurrentPlayer().getCurrentHero();
        final Hero opponentHero = opponentPlayer.getCurrentHero();
        final int levelComparison = opponentHero.getLevel() - currentHero.getLevel();
        final double experienceConsuming = levelComparison > 0 ? (levelComparison + 1) * coefficients.get(0)
                : coefficients.get(0);
        final double damage = currentHero.getAttack() * experienceConsuming;
        opponentHero.removeExperience(damage);
    }

    @Override
    public final void showAnimation() {

    }
}