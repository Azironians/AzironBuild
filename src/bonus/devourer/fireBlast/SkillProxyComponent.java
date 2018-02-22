package bonus.devourer.fireBlast;

import heroes.abstractHero.hero.Hero;
import heroes.abstractHero.skills.Skill;
import heroes.abstractHero.skills.abstractSkill.AbstractSkill;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.Pair;
import lib.duplexMap.DuplexMap;
import managment.actionManagement.actions.ActionEventFactory;
import managment.actionManagement.service.engine.EventEngine;
import managment.playerManagement.Player;
import managment.playerManagement.PlayerManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class SkillProxyComponent {

    //Skill HashCode vs Pair: place in hero skill collection & place in proxy container;
    private final Map<Integer, Pair<Integer, Integer>> hashVsPairMap;

    private final DuplexMap<Skill, Skill> skillVsProxyMap;

    private Skill justInTimeFireBlast;

    SkillProxyComponent() {
        this.hashVsPairMap = new HashMap<>();
        this.skillVsProxyMap = new DuplexMap<>();
    }

    final boolean packSkill(final Player player, final Pane proxyPane) {
        final Hero currentHero = player.getHero();
        final List<Skill> skills = currentHero.getCollectionOfSkills();
        final Pair<Skill, Integer> availableSkill = getFirstAvailableSkill(skills);
        if (availableSkill != null){
            //Unwrapping pair:
            final Skill currentSkill = availableSkill.getKey();
            final Integer containerIndex = availableSkill.getValue();
            //Get main super skill location:
            final ObservableList<Node> skillContainers = player.getLocation().getSkillPane().getChildren();
            final Node skillNode = skillContainers.get(containerIndex);
            //Creating custom skill:
            final AbstractSkill fireBlastSkill = new FireBlastSkill(this);
            final Pane fireBlastContainer = fireBlastSkill.getContainer();
            fireBlastContainer.setLayoutX(skillNode.getLayoutX());
            fireBlastContainer.setLayoutY(skillNode.getLayoutY());
            proxyPane.getChildren().add(fireBlastContainer);
            skills.add(fireBlastSkill);
            this.skillVsProxyMap.bind(currentSkill, fireBlastSkill);
            this.hashVsPairMap.put(fireBlastSkill.hashCode()
                    , new Pair<>(skills.size() - 1, proxyPane.getChildren().size() - 1));
            this.justInTimeFireBlast = fireBlastSkill;
            return true;
        }
        return false;
    }

    private Pair<Skill, Integer> getFirstAvailableSkill(final List<Skill> skills){
        for (int i = skills.size() - 1; i >= 0; i--) {
            final Skill currentSkill = skills.get(i);
            if (!currentSkill.isReady() && !skillVsProxyMap.contains(currentSkill)) {
                return new Pair<>(currentSkill, i);
            }
        }
        return null;
    }

    final void destroy(final Skill skill) {
        final int index =  hashVsPairMap.get(skill.hashCode());
    }

    final void invokeSkill(final EventEngine eventEngine, final PlayerManager playerManager) {
        final Player player = playerManager.getCurrentTeam().getCurrentPlayer();
        final Player opponent = playerManager.getOpponentATeam().getCurrentPlayer();
        final double damage = FireBlastCounter.formDamage(player.getHero().getCollectionOfSkills());
        if (opponent.getHero().getDamage(damage)) {
            eventEngine.handle(ActionEventFactory.getDealDamage(player));
        }
    }

    public final Skill getJustInTimeFireBlast() {
        return justInTimeFireBlast;
    }
}