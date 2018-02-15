package heroes.devourer.skills.superSkills;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import heroes.abstractHero.hero.AHero;
import heroes.abstractHero.skills.abstractSkill.AbstractSkill;
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

    @Inject
    @Named("FLAME_SNAKES_NAME")
    private static String NAME;

    @Inject
    @Named("FLAME_SNAKES_RELOAD")
    private static int RELOAD;

    @Inject
    @Named("FLAME_SNAKES_REQUIRED_LEVEL")
    private static int REQUIRED_LEVEL;

    @Inject
    @Named("FLAME_SNAKES_DAMAGE_SKILL_COEFFICIENT")
    private static double DAMAGE_SKILL_COEFFICIENT;

    private static final List<Double> SKILL_COEFFICIENTS = Collections.singletonList(DAMAGE_SKILL_COEFFICIENT);

    public FlameSnakesSkill(final ImageView sprite, final ImageView description, final List<Media> voiceList) {
        super(NAME, RELOAD, REQUIRED_LEVEL, SKILL_COEFFICIENTS
                , sprite, description, voiceList);
    }

    @Override
    public final void use(final BattleManager battleManager, final PlayerManager playerManager) {
        final double damage = getParent().getAttack() * coefficients.get(0);
        log.info("FLAME_SNAKES_DAMAGE : " + damage);
        final Player currentPlayer = playerManager.getCurrentTeam().getCurrentPlayer();
        final Player opponentPlayer = playerManager.getOpponentATeam().getCurrentPlayer();
        final AHero opponentHero = opponentPlayer.getHero();
        if (opponentHero.getDamage(damage)) {
            actionEvents.add(ActionEventFactory.getDealDamage(currentPlayer));
        }
    }

    @Override
    public final void showAnimation() {

    }
}
