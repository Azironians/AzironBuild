package bonus.devourerBonuses.bonuses.attack.fusedFromBowel;

import heroes.abstractHero.skills.abstractSkill.AbstractSkill;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import managment.actionManagement.ActionManager;
import managment.actionManagement.actions.ActionEventFactory;
import managment.battleManagement.BattleManager;
import managment.playerManagement.Player;
import managment.playerManagement.PlayerManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ArmageddonSkill extends AbstractSkill {

    private static final String name = "Armageddon";

    private static final int reload = 5;

    private static final int requiredLevel = 1;

    private static final double COEFFICIENT = 2.0;

    private static final List<Double> DAMAGE_COEFFICIENTS = Collections.singletonList(COEFFICIENT);

    private static final ImageView sprite = new ImageView();

    private static final ImageView description = new ImageView();

    private static final List<Media> voiceList = new ArrayList<>();

    ArmageddonSkill(final ActionManager actionManager) {
        super(name, reload, requiredLevel, DAMAGE_COEFFICIENTS, sprite, description, voiceList);
        this.actionManager = actionManager;
    }

    @Override
    public final void use(final BattleManager battleManager, final PlayerManager playerManager) {
        final Player player = playerManager.getCurrentTeam().getCurrentPlayer();
        final Player opponent = playerManager.getOpponentTeam().getCurrentPlayer();
        final double damage = player.getCurrentHero().getAttack() * coefficients.get(0);
        if (player.getCurrentHero().getDamage(damage)) {
            actionManager.getEventEngine().setRepeatHandling(true);
        }
        if (opponent.getCurrentHero().getDamage(damage)) {
            actionManager.getEventEngine().handle(ActionEventFactory.getAfterDealDamage(player, opponent.getCurrentHero(), damage));
        }
    }

    @Override
    public final void showAnimation() {

    }
}