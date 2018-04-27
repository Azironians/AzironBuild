package bonus.devourerBonuses.bonuses.attack.fusedFromBowel;

import gui.service.locations.ALocation;
import heroes.abstractHero.hero.Hero;
import heroes.abstractHero.skills.Skill;
import heroes.abstractHero.skills.abstractSkill.AbstractSkill;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import managment.actionManagement.ActionManager;
import managment.playerManagement.Player;

import java.util.ArrayList;
import java.util.List;

final class FusedFromBowelSkillProxyComponent {

    private Player player;

    FusedFromBowelSkillProxyComponent(final Player player) {
        this.player = player;
    }

    final void packSkills(final List<Integer> indexes, final ActionManager actionManager) {
        final Hero currentHero = player.getCurrentHero();
        final List<Skill> skills = currentHero.getCollectionOfSkills();
        final ALocation location = player.getCurrentHero().getLocation();
        final ObservableList<Node> mainSkillContainers = location.getSkillPane().getChildren();

        final List<Node> copyObservableList = new ArrayList<>(){{
            addAll(mainSkillContainers);
        }};

        for (final int index : indexes){
            final AbstractSkill armageddonSkill = new ArmageddonSkill(actionManager);
            final Pane armageddonSkillContainer = armageddonSkill.getContainer();
            armageddonSkillContainer.setLayoutX(mainSkillContainers.get(index).getLayoutX());
            armageddonSkillContainer.setLayoutY(mainSkillContainers.get(index).getLayoutY());
            skills.set(index, armageddonSkill);
            copyObservableList.set(index, armageddonSkillContainer);
        }
        mainSkillContainers.clear();
        mainSkillContainers.addAll(copyObservableList);
    }
}