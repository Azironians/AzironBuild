package bonus.devourerBonuses.bonuses.attack.hybridisation;

import gui.service.locations.ALocation;
import heroes.abstractHero.hero.Hero;
import heroes.abstractHero.skills.Skill;
import heroes.abstractHero.skills.abstractSkill.AbstractSkill;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.Pair;
import lib.duplexMap.DuplexMap;
import managment.playerManagement.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class HybridisationSkillProxyComponent {

    private Player player;

    private Pane proxyHybridisationPane;

    //Skill HashCode vs Pair: place in hero skill collection & place in proxy container;
    private final Map<Integer, Pair<Integer, Integer>> hashVsPairMap;

    private final DuplexMap<Skill, Skill> skillVsProxyMap;

    private Skill justInTimeHybridisationSkill;

    HybridisationSkillProxyComponent(final Player player) {
        this.player = player;
        this.initProxyPane(player);
        this.hashVsPairMap = new HashMap<>();
        this.skillVsProxyMap = new DuplexMap<>();
    }

    private void initProxyPane(final Player player){
        final Pane skillPane = player.getCurrentHero().getLocation().getSkillPane();
        this.proxyHybridisationPane = new Pane();
        this.proxyHybridisationPane.setLayoutX(skillPane.getLayoutX());
        this.proxyHybridisationPane.setLayoutY(skillPane.getLayoutY());
        this.proxyHybridisationPane.setMinWidth(skillPane.getMinWidth());
        this.proxyHybridisationPane.setMinHeight(skillPane.getMinHeight());
    }

    final boolean packSkill() {
        final Hero currentHero = player.getCurrentHero();
        final List<Skill> skills = currentHero.getCollectionOfSkills();
        final Pair<Skill, Integer> availableSkill = isAvailableSkill(skills);
        if (availableSkill != null){
            //Unwrapping pair:
            final Skill currentSkill = availableSkill.getKey();
            final Integer containerIndex = availableSkill.getValue();
            //Get main super skill location:
            final ALocation location = player.getCurrentHero().getLocation();
            final ObservableList<Node> mainSkillContainers = location.getSkillPane().getChildren();
            final Node skillNode = mainSkillContainers.get(containerIndex);
            //Creating custom skill:
            final AbstractSkill hybridisationSkill = new HybridisationSkill(this);
            final Pane hybridisationSkillContainer = hybridisationSkill.getContainer();
            hybridisationSkillContainer.setLayoutX(skillNode.getLayoutX());
            hybridisationSkillContainer.setLayoutY(skillNode.getLayoutY());
            skills.add(hybridisationSkill);
            this.proxyHybridisationPane.getChildren().add(hybridisationSkillContainer);
            this.skillVsProxyMap.bind(currentSkill, hybridisationSkill);
            this.hashVsPairMap.put(hybridisationSkill.hashCode()
                    , new Pair<>(skills.size() - 1, proxyHybridisationPane.getChildren().size() - 1));
            this.justInTimeHybridisationSkill = hybridisationSkill;
            return true;
        }
        return false;
    }

    private Pair<Skill, Integer> isAvailableSkill(final List<Skill> skills){
        for (int i = 0; i < skills.size(); i++) {
            final Skill currentSkill = skills.get(i);
            if (currentSkill.getName().equals("FlameSnakes") && currentSkill.isReady()) {
                return new Pair<>(currentSkill, i);
            }
        }
        return null;
    }

    final void destroy(final Skill skill) {
        final Pair<Integer, Integer> indexPair = hashVsPairMap.remove(skill.hashCode());
        final int heroSkillCollectionIndex = indexPair.getKey();
        final int proxySkillContainerIndex = indexPair.getValue();
        this.player.getCurrentHero().getCollectionOfSkills().remove(heroSkillCollectionIndex);
        this.proxyHybridisationPane.getChildren().remove(proxySkillContainerIndex);
        this.skillVsProxyMap.remove(skill);
    }

    final Skill getJustInTimeHybridisationSkill() {
        final Skill fireBlastSkill = this.justInTimeHybridisationSkill;
        this.justInTimeHybridisationSkill = null;
        return fireBlastSkill;
    }

    final DuplexMap<Skill, Skill> getSkillVsProxyMap() {
        return skillVsProxyMap;
    }
}
