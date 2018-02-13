package heroes.devourer.skills.swapSkills;

import heroes.abstractHero.hero.AHero;
import heroes.abstractHero.skills.swapSkills.AbstractSwapSkill;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import managment.battleManagement.BattleManager;
import managment.playerManagement.Player;
import managment.playerManagement.PlayerManager;

import java.util.Collections;
import java.util.List;

public final class DepletionSkill extends AbstractSwapSkill {

    private static final String NAME = "Depletion";

    private static final int SWAP_RELOAD = 5;

    private static final int SWAP_REQUIRED_LEVEL = 1;

    private static final double SWAP_SKILL_COEFFICIENT = 1.0;

    private static final List<Double> SWAP_SKILL_COEFFICIENTS = Collections.singletonList(SWAP_SKILL_COEFFICIENT);

    public DepletionSkill(final ImageView sprite, final ImageView description, final List<Media> voiceList) {
        super(NAME, SWAP_RELOAD, SWAP_REQUIRED_LEVEL, SWAP_SKILL_COEFFICIENTS, sprite, description, voiceList);
    }

    @Override
    public final void use(BattleManager battleManager, PlayerManager playerManager) {
        final Player opponentPlayer = playerManager.getOpponentATeam().getCurrentPlayer();
        final AHero currentHero = playerManager.getCurrentTeam().getCurrentPlayer().getHero();
        final AHero opponentHero = opponentPlayer.getHero();
        final int levelComparison = opponentHero.getLevel() - currentHero.getLevel();
        final double SKILL_COEFFICIENT = levelComparison > 0 ? (levelComparison + 1) * coefficients.get(0)
                : coefficients.get(0);
        final double DAMAGE = currentHero.getAttack() * SKILL_COEFFICIENT;
        opponentHero.removeExperience(DAMAGE);
    }

    @Override
    public final void showAnimation() {

    }
}