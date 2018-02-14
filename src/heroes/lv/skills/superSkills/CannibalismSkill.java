package heroes.lv.skills.superSkills;

import heroes.abstractHero.hero.AHero;
import heroes.abstractHero.skills.swapSkills.AbstractSwapSkill;
import heroes.devourer.skills.superSkills.FlameSnakesSkill;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import managment.actionManagement.actions.ActionEventFactory;
import managment.battleManagement.BattleManager;
import managment.playerManagement.Player;
import managment.playerManagement.PlayerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public final class CannibalismSkill extends AbstractSwapSkill {

    private static final Logger log = LoggerFactory.getLogger(FlameSnakesSkill.class);

    private static final String NAME = "Cannibalism";

    private static final int RELOAD = 5;

    private static final int REQUIRED_LEVEL = 1;

    private static final double DAMAGE_SKILL_COEFFICIENT = 2.5;

    private static final double HEALING_SKILL_COEFFICIENT = 2.5;

    private static final List<Double> SKILL_COEFFICIENTS = Arrays.asList
            (DAMAGE_SKILL_COEFFICIENT, HEALING_SKILL_COEFFICIENT);

    public CannibalismSkill(final ImageView sprite, final ImageView description, final List<Media> voiceList) {
        super(NAME, RELOAD, REQUIRED_LEVEL, SKILL_COEFFICIENTS
                , sprite, description, voiceList);
    }

    @Override
    public final void use(BattleManager battleManager, PlayerManager playerManager) {
        final double DAMAGE = getParent().getAttack() * coefficients.get(0);
        final double HEALING = getParent().getAttack() * coefficients.get(1);
        final Player currentPlayer = playerManager.getCurrentTeam().getCurrentPlayer();
        final Player opponentPlayer = playerManager.getOpponentATeam().getCurrentPlayer();
        final AHero currentHero = currentPlayer.getHero();
        final AHero opponentHero = opponentPlayer.getHero();
        if (opponentHero.getDamage(DAMAGE)) {
            actionEvents.add(ActionEventFactory.getDealDamage(currentPlayer));
        }
        currentHero.getHealing(HEALING);
    }

    @Override
    public final void showAnimation() {

    }
}
