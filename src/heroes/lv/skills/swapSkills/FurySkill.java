package heroes.lv.skills.swapSkills;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import heroes.abstractHero.hero.Hero;
import heroes.abstractHero.skills.swapSkills.AbstractSwapSkill;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import managment.actionManagement.actions.ActionEventFactory;
import managment.battleManagement.BattleManager;
import managment.playerManagement.Player;
import managment.playerManagement.PlayerManager;

import java.util.Collections;
import java.util.List;

public final class FurySkill extends AbstractSwapSkill {

    @Inject
    @Named("FURY_NAME")
    private static String NAME;

    @Inject
    @Named("FURY_SWAP_RELOAD")
    private static int SWAP_RELOAD;

    @Inject
    @Named("FURY_SWAP_REQUIRED_LEVEL")
    private static int SWAP_REQUIRED_LEVEL;

    @Inject
    @Named("FURY_SWAP_SKILL_COEFFICIENT")
    private static double SWAP_SKILL_COEFFICIENT;

    private static final List<Double> SWAP_SKILL_COEFFICIENTS = Collections.singletonList(SWAP_SKILL_COEFFICIENT);

    public FurySkill(final ImageView sprite, final ImageView description, final List<Media> voiceList) {
        super(NAME, SWAP_RELOAD, SWAP_REQUIRED_LEVEL, SWAP_SKILL_COEFFICIENTS, sprite, description, voiceList);
    }

    @Override
    public final void use(final BattleManager battleManager, final PlayerManager playerManager) {
        final Player opponentPlayer = playerManager.getOpponentATeam().getCurrentPlayer();
        final Player currentPlayer = playerManager.getCurrentTeam().getCurrentPlayer();
        final Hero currentHero = currentPlayer.getHero();
        final Hero opponentHero = opponentPlayer.getHero();
        final int levelComparison = opponentHero.getLevel() - currentHero.getLevel();
        final double SKILL_COEFFICIENT = levelComparison > 0 ? (levelComparison + 1) * coefficients.get(0)
                : coefficients.get(0);
        final double DAMAGE = currentHero.getAttack() * SKILL_COEFFICIENT;
        if (opponentHero.getDamage(DAMAGE)){
            actionEvents.add(ActionEventFactory.getDealDamage(currentPlayer));
        }
    }

    @Override
    public final void showAnimation() {

    }
}