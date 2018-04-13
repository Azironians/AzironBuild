package bonus.devourerBonuses.bonuses.health.snakeShield

import heroes.abstractHero.skills.Skill
import javafx.beans.Observable
import javafx.collections.ObservableList
import javafx.scene.Node
import javafx.util.Pair
import lib.duplexMap.DuplexMap
import managment.playerManagement.Player

import scala.collection.immutable.HashMap

final class SnakeShieldSkillProxyComponent (var player: Player) {
  private val hashVsPairMap = new HashMap[Integer, Pair[Integer, Integer]]
  private val skillVsProxyMap = new DuplexMap[Skill, Skill]
  private val proxySnakeShieldPane = null
  //Skill HashCode vs Pair: place in hero skill collection & place in proxy container;
  private val justInTimeSnakeShieldSkill = null

  def packSkill(index: Int, skills: List[Skill]): Unit ={
    val mainSkillContainers: ObservableList[Node] = player.getLocation.getSkillPane.getChildren
    val skillNode: Node = mainSkillContainers.get(index)
    //Creating custom skill
  }


//  final boolean packSkill() {
//    final Pair<Skill, Integer> availableSkill = getFirstAvailableSkill(skills);
//    if (availableSkill != null){
//      //Unwrapping pair:
//      //Get main super skill location:
//      final Node skillNode = mainSkillContainers.get(containerIndex);
//      //Creating custom skill:
//      final AbstractSkill fireBlastSkill = new FireBlastSkill(this);
//      final Pane fireBlastContainer = fireBlastSkill.getContainer();
//      fireBlastContainer.setLayoutX(skillNode.getLayoutX());
//      fireBlastContainer.setLayoutY(skillNode.getLayoutY());
//      skills.add(fireBlastSkill);
//      this.proxyFireBlastPane.getChildren().add(fireBlastContainer);
//      this.skillVsProxyMap.bind(currentSkill, fireBlastSkill);
//      this.hashVsPairMap.put(fireBlastSkill.hashCode()
//        , new Pair<>(skills.size() - 1, proxyFireBlastPane.getChildren().size() - 1));
//      this.justInTimeFireBlastSkill = fireBlastSkill;
//      return true;
//    }
//    return false;
//  }
}