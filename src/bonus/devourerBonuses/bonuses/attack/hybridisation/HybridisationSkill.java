package bonus.devourerBonuses.bonuses.attack.hybridisation;

import heroes.abstractHero.skills.abstractSkill.AbstractSkill;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import managment.actionManagement.actions.ActionEvent;
import managment.actionManagement.actions.ActionEventFactory;
import managment.actionManagement.actions.ActionType;
import managment.actionManagement.service.components.handleComponet.HandleComponent;
import managment.battleManagement.BattleManager;
import managment.playerManagement.Player;
import managment.playerManagement.PlayerManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class HybridisationSkill extends AbstractSkill {

    private static final String NAME = "Hybridisation";

    private static final int RELOAD = 1;

    private static final int REQUIRED_LEVEL = 1;

    private static final double START_COEFFICIENT = 0.25;

    private static final int START_HYDRA_AGGRESSION = 1;

    private static boolean IS_HYDRA_DESTROYED(final int countHydraAggression){
        return countHydraAggression == 4;
    }

    private static final List<Double> COEFFICIENTS = Collections.singletonList(START_COEFFICIENT);

    private static final ImageView SPRITE = new ImageView();

    private static final ImageView DESCRIPTION = new ImageView();

    private static final List<Media> VOICE_LIST = new ArrayList<>();

    private final HybridisationSkillProxyComponent hybridisationSkillProxyComponent;

    HybridisationSkill(final HybridisationSkillProxyComponent hybridisationSkillProxyComponent) {
        super(NAME, RELOAD, REQUIRED_LEVEL, COEFFICIENTS, SPRITE, DESCRIPTION, VOICE_LIST);
        this.hybridisationSkillProxyComponent = hybridisationSkillProxyComponent;
    }

    @Override
    public final void use(final BattleManager battleManager, final PlayerManager playerManager) {
        actionManager.getEventEngine().addHandler(getHandlerComponent(playerManager));
        destroy();
    }

    private HandleComponent getHandlerComponent(final PlayerManager playerManager){
        return new HandleComponent() {
            
            private Player currentPlayer;
            
            private double coefficient;
            
            private int hydraAggression;

            private boolean isWorking;
            
            @Override
            public final void setup() {
                this.currentPlayer = playerManager.getCurrentTeam().getCurrentPlayer();
                this.coefficient = START_COEFFICIENT;
                this.hydraAggression = START_HYDRA_AGGRESSION;
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                if (actionEvent.getActionType() == ActionType.START_TURN && actionEvent.getPlayer() == currentPlayer
                        && currentPlayer == playerManager.getCurrentTeam().getCurrentPlayer()){
                    final Player opponent = playerManager.getOpponentTeam().getCurrentPlayer();
                    final double damage = currentPlayer.getCurrentHero().getAttack() * hydraAggression * coefficient;
                    if (opponent.getCurrentHero().getDamage(damage)){
                        actionManager.getEventEngine().handle(ActionEventFactory.getAfterDealDamage(currentPlayer
                                , opponent.getCurrentHero(), damage));
                    }
                }
                if (actionEvent.getActionType() == ActionType.BEFORE_ATTACK
                        && currentPlayer == playerManager.getOpponentTeam().getCurrentPlayer()){
                    if (IS_HYDRA_DESTROYED(hydraAggression + 1)){
                        this.isWorking = false;
                        this.hydraAggression = 0;
                        actionManager.getEventEngine().handle();
                    } else {
                        this.hydraAggression++;
                    }
                }
            }

            @Override
            public final String getName() {
                return "Hydra";
            }

            @Override
            public final Player getCurrentPlayer() {
                return currentPlayer;
            }

            @Override
            public final boolean isWorking() {
                return isWorking;
            }

            @Override
            public final void setWorking(final boolean able) {
                throw new UnsupportedOperationException();
            }
        };
    }

    private void destroy(){
        hybridisationSkillProxyComponent.destroy(this);
    }

    @Override
    public final void showAnimation() {

    }
}