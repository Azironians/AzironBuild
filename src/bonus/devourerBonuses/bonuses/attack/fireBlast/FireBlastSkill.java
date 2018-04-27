package bonus.devourerBonuses.bonuses.attack.fireBlast;

import heroes.abstractHero.skills.abstractSkill.AbstractSkill;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import managment.actionManagement.actions.ActionEventFactory;
import managment.battleManagement.BattleManager;
import managment.playerManagement.Player;
import managment.playerManagement.PlayerManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class FireBlastSkill extends AbstractSkill {

    private static final String name = "FireBlast";

    private static final int reload = 1;

    private static final int requiredLevel = 1;

    private static final double coefficient = 1.0;

    private static final List<Double> coefficients = Collections.singletonList(coefficient);

    private static final ImageView sprite = new ImageView();

    private static final ImageView description = new ImageView();

    private static final List<Media> voiceList = new ArrayList<>();

    private FireBlastSkillProxyComponent fireBlastSkillProxyComponent;

    FireBlastSkill(final FireBlastSkillProxyComponent fireBlastSkillProxyComponent) {
        super(name, reload, requiredLevel, coefficients, sprite, description, voiceList);
        this.fireBlastSkillProxyComponent = fireBlastSkillProxyComponent;
    }

    @Override
    public final void use(final BattleManager battleManager, final PlayerManager playerManager) {
        final Player player = playerManager.getCurrentTeam().getCurrentPlayer();
        final Player opponent = playerManager.getOpponentTeam().getCurrentPlayer();
        final double damage = FireBlastAssistant.formDamage(player.getCurrentHero().getCollectionOfSkills());
        if (opponent.getCurrentHero().getDamage(damage)) {
            actionManager.getEventEngine().handle(ActionEventFactory.getAfterDealDamage(player, opponent.getCurrentHero(), damage));
        }
        destroy();
    }

    private void destroy(){
        fireBlastSkillProxyComponent.destroy(this);
    }

    @Override
    public void showAnimation() {

    }
}