package heroes.devourer.skills.superSkills;

import heroes.abstractHero.hero.AHero;
import heroes.abstractHero.skills.superSkills.AbstractSkill;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import managment.actionManagement.actions.ActionEventFactory;
import managment.battleManagement.BattleManager;
import managment.playerManagement.Player;
import managment.playerManagement.PlayerManager;

import java.util.Collections;
import java.util.List;

public final class ConsumingSkill extends AbstractSkill {

    private static final String NAME = "FlameSnakes";

    private static final int CONSUMING_RELOAD = 4;

    private static final int CONSUMING_REQUIRED_LEVEL = 6;

    private static final double CONSUMING_SKILL_COEFFICIENT = 2.0;

    private static final List<Double> CONSUMING_SKILL_COEFFICIENTS = Collections
            .singletonList(CONSUMING_SKILL_COEFFICIENT);

    public ConsumingSkill(final ImageView sprite, final ImageView description, final List<Media> voiceList) {
        super(NAME, CONSUMING_RELOAD, CONSUMING_REQUIRED_LEVEL, CONSUMING_SKILL_COEFFICIENTS
                , sprite, description, voiceList);
    }

    @Override
    public final void use(final BattleManager battleManager, final PlayerManager playerManager) {
        final Player currentPlayer = playerManager.getCurrentTeam().getCurrentPlayer();
        final Player opponentPlayer = playerManager.getOpponentATeam().getCurrentPlayer();
        final AHero opponentHero = opponentPlayer.getHero();

        final double DAMAGE = opponentHero.getHitPoints() / coefficients.get(0);
        if (opponentHero.getDamage(DAMAGE)) {
            actionEvents.add(ActionEventFactory.getDealDamage(currentPlayer));
        }
    }

    @Override
    public final void showAnimation() {

    }
}
