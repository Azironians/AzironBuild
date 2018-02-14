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

    private static final int RELOAD = 5;

    private static final int REQUIRED_LEVEL = 1;

    private static final double SKILL_COEFFICIENT = 1.0;

    private static final List<Double> SKILL_COEFFICIENTS = Collections.singletonList(SKILL_COEFFICIENT);

    public DepletionSkill(final ImageView sprite, final ImageView description, final List<Media> voiceList) {
        super(NAME, RELOAD, REQUIRED_LEVEL, SKILL_COEFFICIENTS, sprite, description, voiceList);
    }

    @Override
    public final void use(final BattleManager battleManager, final PlayerManager playerManager) {
        final Player opponentPlayer = playerManager.getOpponentATeam().getCurrentPlayer();
        final AHero currentHero = playerManager.getCurrentTeam().getCurrentPlayer().getHero();
        final AHero opponentHero = opponentPlayer.getHero();
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