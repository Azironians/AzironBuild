package heroes.lv.skills.superSkills;

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

import java.util.Arrays;
import java.util.List;

public final class CannibalismSkill extends AbstractSwapSkill {

    @Inject
    @Named("CANNIBALISM_NAME")
    private static String NAME;

    @Inject
    @Named("CANNIBALISM_RELOAD")
    private static int RELOAD;

    @Inject
    @Named("CANNIBALISM_REQUIRED_LEVEL")
    private static int REQUIRED_LEVEL;

    @Inject
    @Named("CANNIBALISM_DAMAGE_SKILL_COEFFICIENT")
    private static double DAMAGE_SKILL_COEFFICIENT;

    @Inject
    @Named("CANNIBALISM_HEALING_SKILL_COEFFICIENT")
    private static double HEALING_SKILL_COEFFICIENT;

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
