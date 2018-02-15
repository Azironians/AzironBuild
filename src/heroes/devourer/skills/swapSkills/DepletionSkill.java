package heroes.devourer.skills.swapSkills;

import com.google.inject.Inject;
import com.google.inject.name.Named;
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

    @Inject
    @Named("DEPLETION_NAME")
    private static String NAME;

    @Inject
    @Named("DEPLETION_RELOAD")
    private static int RELOAD;

    @Inject
    @Named("DEPLETION_REQUIRED_LEVEL")
    private static int REQUIRED_LEVEL;

    @Inject
    @Named("DEPLETION_SKILL_COEFFICIENT")
    private static double SKILL_COEFFICIENT;

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