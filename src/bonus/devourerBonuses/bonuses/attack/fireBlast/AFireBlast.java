package bonus.devourerBonuses.bonuses.attack.fireBlast;

import bonus.bonuses.Bonus;
import heroes.abstractHero.skills.Skill;
import javafx.scene.image.ImageView;
import lib.duplexMap.DuplexMap;
import managment.actionManagement.actions.ActionEvent;
import managment.actionManagement.service.components.handleComponet.HandleComponent;
import managment.actionManagement.service.components.handleComponet.IllegalSwitchOffHandleComponentException;
import managment.actionManagement.service.engine.services.RegularHandleService;
import managment.playerManagement.Player;

import java.util.ArrayList;
import java.util.List;

public final class AFireBlast extends Bonus implements RegularHandleService {

    private FireBlastSkillProxyComponent fireBlastSkillProxyComponent;

    public AFireBlast(String name, int id, ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        if (fireBlastSkillProxyComponent.packSkill()) {
            wireActionManager(fireBlastSkillProxyComponent.getJustInTimeFireBlastSkill());
        } else {
            fireBlastSkillProxyComponent.invokeSkill(actionManager.getEventEngine(), playerManager);
        }
    }

    private void wireActionManager(final Skill skill){
        skill.setActionManager(actionManager);
    }

    @Override
    public final HandleComponent getRegularHandlerInstance(final Player player) {
        return new HandleComponent() {

            private Player currentPlayer;

            @Override
            public final void setup() {
                this.currentPlayer = player;
                fireBlastSkillProxyComponent = new FireBlastSkillProxyComponent(currentPlayer);
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                final List<Skill> garbageList = new ArrayList<>();
                for (final Skill skill: currentPlayer.getCurrentHero().getCollectionOfSkills()){
                    if (skill.isReady()){
                        final DuplexMap<Skill, Skill> skillVsProxyMap
                                = fireBlastSkillProxyComponent.getSkillVsProxyMap();
                        final Skill fireBlast = skillVsProxyMap.getProxy(skill);
                        if (fireBlast != null){
                            garbageList.add(fireBlast);
                        }
                    }
                }
                garbageList.forEach(fireBlastSkillProxyComponent::destroy);
            }

            @Override
            public final String getName() {
                return "FireBlast";
            }

            @Override
            public final Player getCurrentPlayer() {
                return currentPlayer;
            }

            @Override
            public final boolean isWorking() {
                return true;
            }

            @Override
            public final void setWorking(boolean able) throws IllegalSwitchOffHandleComponentException {
                throw new IllegalSwitchOffHandleComponentException("FireBlast handler " +
                        "component always must work in EventEngine");
            }
        };
    }
}