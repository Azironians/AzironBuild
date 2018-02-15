package heroes.lv.skills.superSkills.cannibalism;

import heroes.abstractHero.hero.Hero;
import heroes.abstractHero.skills.swapSkills.AbstractSwapSkill;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import managment.actionManagement.actions.ActionEventFactory;
import managment.battleManagement.BattleManager;
import managment.playerManagement.Player;
import managment.playerManagement.PlayerManager;

import java.util.List;

import static heroes.lv.skills.superSkills.cannibalism.CannibalismPropertySkill.*;

public final class CannibalismSkill extends AbstractSwapSkill {



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
        final Hero currentHero = currentPlayer.getHero();
        final Hero opponentHero = opponentPlayer.getHero();
        if (opponentHero.getDamage(DAMAGE)) {
            actionEvents.add(ActionEventFactory.getDealDamage(currentPlayer));
        }
        currentHero.getHealing(HEALING);
    }

    @Override
    public final void showAnimation() {

    }
}
