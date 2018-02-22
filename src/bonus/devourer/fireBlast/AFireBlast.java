package bonus.devourer.fireBlast;

import bonus.bonuses.Bonus;
import heroes.abstractHero.skills.Skill;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import managment.actionManagement.actions.ActionEvent;
import managment.actionManagement.service.components.HandleComponent;
import managment.actionManagement.service.engine.RegularHandleService;
import managment.playerManagement.Player;

public final class AFireBlast extends Bonus implements RegularHandleService {

    private SkillProxyComponent skillProxyComponent;

    private Pane proxyFireBlastPane;

    public AFireBlast(String name, int id, ImageView sprite) {
        super(name, id, sprite);
        this.skillProxyComponent = new SkillProxyComponent();
    }

    @Override
    public final void use() {
        final Player player = playerManager.getCurrentTeam().getCurrentPlayer();
        if (skillProxyComponent.packSkill(player, proxyFireBlastPane)) {
            wireActionManager(skillProxyComponent.getJustInTimeFireBlast());
        } else {
            skillProxyComponent.invokeSkill(actionManager.getEventEngine(), playerManager);
        }
    }

    private void wireActionManager(final Skill skill){
        skill.setActionManager(actionManager);
    }

    @Override
    public final HandleComponent getInstallHandlerInstance(final Player player) {
        return new HandleComponent() {

            @Override
            public final void setup() {
                final Pane skillPane = player.getLocation().getSkillPane();
                proxyFireBlastPane = new Pane();
                proxyFireBlastPane.setLayoutX(skillPane.getLayoutX());
                proxyFireBlastPane.setLayoutY(skillPane.getLayoutY());
                proxyFireBlastPane.setMinWidth(skillPane.getMinWidth());
                proxyFireBlastPane.setMinHeight(skillPane.getMinHeight());
            }

            @Override
            public void handle(final ActionEvent actionEvent) {

            }

            @Override
            public String getName() {
                return "fireBlast";
            }

            @Override
            public Player getPlayer() {
                return null;
            }

            @Override
            public boolean isWorking() {
                return true;
            }

            @Override
            public void setAble(boolean able) {

            }
        };
    }

    public final SkillProxyComponent getSkillProxyComponent() {
        return skillProxyComponent;
    }
}