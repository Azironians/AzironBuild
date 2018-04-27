package bonus.devourerBonuses.bonuses.attack.fireBlast;

import gui.service.locations.ALocation;
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

final class FireBlastSkillProxyComponent {

    private Player player;

    private Pane proxyFireBlastPane;

    //Skill HashCode vs Pair: place in hero skill collection & place in proxy container;
    private final Map<Integer, Pair<Integer, Integer>> hashVsPairMap;

    private final DuplexMap<Skill, Skill> skillVsProxyMap;

    private Skill justInTimeFireBlastSkill;

    FireBlastSkillProxyComponent(final Player player) {
        this.player = player;
        this.initProxyPane(player);
        this.hashVsPairMap = new HashMap<>();
        this.skillVsProxyMap = new DuplexMap<>();
    }

    private void initProxyPane(final Player player){
        final Pane skillPane = player.getCurrentHero().getLocation().getSkillPane();
        this.proxyFireBlastPane = new Pane();
        this.proxyFireBlastPane.setLayoutX(skillPane.getLayoutX());
        this.proxyFireBlastPane.setLayoutY(skillPane.getLayoutY());
        this.proxyFireBlastPane.setMinWidth(skillPane.getMinWidth());
        this.proxyFireBlastPane.setMinHeight(skillPane.getMinHeight());
    }

    final boolean packSkill() {
        final Hero currentHero = player.getCurrentHero();
        final List<Skill> skills = currentHero.getCollectionOfSkills();
        final Pair<Skill, Integer> availableSkill = getFirstAvailableSkill(skills);
        if (availableSkill != null){
            //Unwrapping pair:
            final Skill currentSkill = availableSkill.getKey();
            final Integer containerIndex = availableSkill.getValue();
            //Get main super skill location:
            final ALocation location = player.getCurrentHero().getLocation();
            final ObservableList<Node> mainSkillContainers = location.getSkillPane().getChildren();
            final Node skillNode = mainSkillContainers.get(containerIndex);
            //Creating custom skill:
            final AbstractSkill fireBlastSkill = new FireBlastSkill(this);
            final Pane fireBlastContainer = fireBlastSkill.getContainer();
            fireBlastContainer.setLayoutX(skillNode.getLayoutX());
            fireBlastContainer.setLayoutY(skillNode.getLayoutY());
            skills.add(fireBlastSkill);
            this.proxyFireBlastPane.getChildren().add(fireBlastContainer);
            this.skillVsProxyMap.bind(currentSkill, fireBlastSkill);
            this.hashVsPairMap.put(fireBlastSkill.hashCode()
                    , new Pair<>(skills.size() - 1, proxyFireBlastPane.getChildren().size() - 1));
            this.justInTimeFireBlastSkill = fireBlastSkill;
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

    final void invokeSkill(final EventEngine eventEngine, final PlayerManager playerManager) {
        final Player player = playerManager.getCurrentTeam().getCurrentPlayer();
        final Player opponent = playerManager.getOpponentTeam().getCurrentPlayer();
        final double damage = FireBlastAssistant.formDamage(player.getCurrentHero().getCollectionOfSkills());
        if (opponent.getCurrentHero().getDamage(damage)) {
            eventEngine.handle(ActionEventFactory.getAfterDealDamage(player, opponent.getCurrentHero(), damage));
        }
    }

    final Skill getJustInTimeFireBlastSkill() {
        final Skill fireBlastSkill = this.justInTimeFireBlastSkill;
        this.justInTimeFireBlastSkill = null;
        return fireBlastSkill;
    }

    final void destroy(final Skill skill) {
        final Pair<Integer, Integer> indexPair = hashVsPairMap.get(skill.hashCode());
        final int heroSkillCollectionIndex = indexPair.getKey();
        final int proxySkillContainerIndex = indexPair.getValue();
        this.player.getCurrentHero().getCollectionOfSkills().remove(heroSkillCollectionIndex);
        this.proxyFireBlastPane.getChildren().remove(proxySkillContainerIndex);
        this.skillVsProxyMap.remove(skill);
    }

    final DuplexMap<Skill, Skill> getSkillVsProxyMap() {
        return skillVsProxyMap;
    }
}