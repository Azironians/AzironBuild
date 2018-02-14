package heroes.orcBash.skills.superSkills;

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

public final class FavouriteBeaterSkill extends AbstractSkill {

    private static final Logger log = LoggerFactory.getLogger(FavouriteBeaterSkill.class);

    private static final String NAME = "FavouriteBeater";

    private static final int RELOAD = 8;

    private static final int REQUIRED_LEVEL = 5;

    private static final double DAMAGE_SKILL_COEFFICIENT = 2.5;

    private static final List<Double> SKILL_COEFFICIENTS = Collections.singletonList(DAMAGE_SKILL_COEFFICIENT);

    public FavouriteBeaterSkill(final ImageView sprite, final ImageView description, final List<Media> voiceList) {
        super(NAME, RELOAD, REQUIRED_LEVEL, SKILL_COEFFICIENTS
                , sprite, description, voiceList);
    }

    @Override
    public final void use(final BattleManager battleManager, final PlayerManager playerManager) {
        final double damage = getParent().getAttack() * coefficients.get(0);
        final double fixHealthSupply = getParent().getAttack() * coefficients.get(1);
        final Player currentPlayer = playerManager.getCurrentTeam().getCurrentPlayer();
        final Player opponentPlayer = playerManager.getOpponentATeam().getCurrentPlayer();
        final AHero opponentHero = opponentPlayer.getHero();
        if (opponentHero.getDamage(damage)) {
            actionEvents.add(ActionEventFactory.getDealDamage(currentPlayer));
        }
        opponentHero.setHealthSupply(opponentHero.getHealthSupply() - fixHealthSupply);
    }

    @Override
    public final void showAnimation() {

    }
}
