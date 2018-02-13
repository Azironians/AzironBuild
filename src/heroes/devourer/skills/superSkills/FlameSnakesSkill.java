package heroes.devourer.skills.superSkills;

import heroes.abstractHero.hero.AHero;
import heroes.abstractHero.resourceSupplier.HeroResourceSupplier;
import heroes.abstractHero.skills.superSkills.AbstractSkill;
import heroes.devourer.builder.DevourerBuilder;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import managment.actionManagement.actions.ActionEventFactory;
import managment.battleManagement.BattleManager;
import managment.playerManagement.Player;
import managment.playerManagement.PlayerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

public final class FlameSnakesSkill extends AbstractSkill {

    private static final Logger log = LoggerFactory.getLogger(FlameSnakesSkill.class);

    private static final String NAME = "FlameSnakes";

    private static final int FLAME_SNAKES_RELOAD = 5;

    private static final int FLAME_SNAKES_REQUIRED_LEVEL = 1;

    private static final double FLAME_SNAKES_SKILL_COEFFICIENT = 5;

    private static final List<Double> FLAME_SNAKES_SKILL_COEFFICIENTS = Collections
            .singletonList(FLAME_SNAKES_SKILL_COEFFICIENT);

    public FlameSnakesSkill(final ImageView sprite, final ImageView description, final List<Media> voiceList) {
        super(NAME, FLAME_SNAKES_RELOAD, FLAME_SNAKES_REQUIRED_LEVEL, FLAME_SNAKES_SKILL_COEFFICIENTS
                , sprite, description, voiceList);
    }

    @Override
    public void use(BattleManager battleManager, PlayerManager playerManager) {
        final double DAMAGE = getParent().getAttack() * coefficients.get(0);
        log.info("FLAME_SNAKES_DAMAGE : " + DAMAGE);
        final Player currentPlayer = playerManager.getCurrentTeam().getCurrentPlayer();
        final Player opponentPlayer = playerManager.getOpponentATeam().getCurrentPlayer();
        final AHero opponentHero = opponentPlayer.getHero();
        if (opponentHero.getDamage(DAMAGE)) {
            actionEvents.add(ActionEventFactory.getDealDamage(currentPlayer));
        }
    }

    @Override
    public void showAnimation() {

    }
}
