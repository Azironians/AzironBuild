package bonus.devourerBonuses.bonuses.health;

import bonus.bonuses.Bonus;
import heroes.abstractHero.skills.Skill;
import javafx.scene.image.ImageView;
import managment.actionManagement.actions.ActionEvent;
import managment.actionManagement.actions.ActionType;
import managment.actionManagement.service.components.handleComponet.HandleComponent;
import managment.actionManagement.service.engine.services.DynamicHandleService;
import managment.playerManagement.ATeam;
import managment.playerManagement.Player;

public final class HArhawrath extends Bonus implements DynamicHandleService {

    public HArhawrath(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        actionManager.getEventEngine().addHandler(getHandlerInstance());
    }

    @Override
    public final HandleComponent getHandlerInstance() {
        return new HandleComponent() {

            private ATeam team;

            private Player player;

            private boolean isWorking;

            @Override
            public final void setup() {
                this.player = playerManager.getCurrentTeam().getCurrentPlayer();
                this.team = playerManager.getOpponentTeam();
            }

            @Override
            public final void handle(ActionEvent actionEvent) {
                if (actionEvent.getActionType() == ActionType.BEFORE_ATTACK
                        && (actionEvent.getPlayer() == getCurrentPlayer()
                        || actionEvent.getPlayer() == team.getAlternativePlayer())
                        && player == playerManager.getOpponentTeam().getCurrentPlayer()){
                    for (final Skill skill : player.getCurrentHero().getCollectionOfSkills()){
                        if (skill.getName().equals("FlameSnakes")){
                            skill.setTemp(skill.getReload());
                        }
                    }
                }
                if (actionEvent.getActionType() == ActionType.END_TURN
                        && (actionEvent.getPlayer() == getCurrentPlayer()
                        || actionEvent.getPlayer() == team.getAlternativePlayer())){
                    this.isWorking = false;
                }
            }

            @Override
            public final String getName() {
                return "Arhawrath";
            }

            @Override
            public final Player getCurrentPlayer() {
                return player;
            }

            @Override
            public final boolean isWorking() {
                return isWorking;
            }

            @Override
            public final void setWorking(final boolean able) {
                this.isWorking = able;
            }
        };
    }
}