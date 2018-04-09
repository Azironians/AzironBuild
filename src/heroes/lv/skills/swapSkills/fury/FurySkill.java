package heroes.lv.skills.swapSkills.fury;

import heroes.abstractHero.hero.Hero;
import heroes.abstractHero.skills.swapSkill.AbstractSwapSkill;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import managment.actionManagement.actions.ActionEventFactory;
import managment.battleManagement.BattleManager;
import managment.playerManagement.Player;
import managment.playerManagement.PlayerManager;

import java.util.List;

import static heroes.lv.skills.swapSkills.fury.FuryPropertySkill.*;

public final class FurySkill extends AbstractSwapSkill {

    public FurySkill(final ImageView sprite, final ImageView description, final List<Media> voiceList) {
        super(NAME, SWAP_RELOAD, SWAP_REQUIRED_LEVEL, getSkillCoefficients(), sprite, description, voiceList);
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
            actionEvents.add(ActionEventFactory.getDealDamage(currentPlayer, opponentHero, DAMAGE));
        }
    }

    @Override
    public final void showAnimation() {

    }
}