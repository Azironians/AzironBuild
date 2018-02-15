package heroes.devourer.skills.swapSkills.depletion;

import heroes.abstractHero.hero.Hero;
import heroes.abstractHero.skills.swapSkills.AbstractSwapSkill;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import managment.battleManagement.BattleManager;
import managment.playerManagement.Player;
import managment.playerManagement.PlayerManager;

import java.util.List;

import static heroes.devourer.skills.swapSkills.depletion.DepletionPropertySkill.*;

public final class DepletionSkill extends AbstractSwapSkill {



    public DepletionSkill(final ImageView sprite, final ImageView description, final List<Media> voiceList) {
        super(NAME, RELOAD, REQUIRED_LEVEL, SKILL_COEFFICIENTS, sprite, description, voiceList);
    }

    @Override
    public final void use(final BattleManager battleManager, final PlayerManager playerManager) {
        final Player opponentPlayer = playerManager.getOpponentATeam().getCurrentPlayer();
        final Hero currentHero = playerManager.getCurrentTeam().getCurrentPlayer().getHero();
        final Hero opponentHero = opponentPlayer.getHero();
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