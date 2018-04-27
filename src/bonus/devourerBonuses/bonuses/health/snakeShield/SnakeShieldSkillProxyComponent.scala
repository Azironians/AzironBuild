package bonus.devourerBonuses.bonuses.health.snakeShield

import heroes.abstractHero.skills.Skill
import heroes.abstractHero.skills.abstractSkill.AbstractSkill
import javafx.collections.ObservableList
import javafx.scene.Node
import javafx.scene.layout.Pane
import javafx.util.Pair
import lib.duplexMap.DuplexMap
import managment.playerManagement.Player

final class SnakeShieldSkillProxyComponent (var player: Player) {

  private val hashVsPairMap: java.util.Map[Integer, Pair[Integer, Integer]] = new java.util.HashMap

  var proxySkillVsDamageMap: java.util.Map[Skill, Double] = new java.util.HashMap

  private val skillVsProxyMap = new DuplexMap[Skill, Skill]

  private val proxySnakeShieldPane: Pane = new Pane()

  //Skill HashCode vs Pair: place in hero skill collection & place in proxy container;
  var justInTimeSnakeShieldSkill: Skill = _

  def packSkill(index: Int, skills: java.util.List[Skill]): Unit ={
    val mainSkillContainers: ObservableList[Node] = player.getCurrentHero.getLocation.getSkillPane.getChildren
    val skillNode: Node = mainSkillContainers.get(index)
    //Creating custom skill:
    val snakeShieldSkill: AbstractSkill = new SnakeShieldSkill(this)
    val snakeShieldContainer: Pane = snakeShieldSkill.getContainer
    snakeShieldContainer.setLayoutX(skillNode.getLayoutX)
    snakeShieldContainer.setLayoutY(skillNode.getLayoutY)
    skills.add(snakeShieldSkill)
    proxySnakeShieldPane.getChildren.add(snakeShieldContainer)
    skillVsProxyMap.bind(skills.get(index), snakeShieldSkill)
    hashVsPairMap.put(snakeShieldSkill.hashCode(), new Pair(skills.size - 1, proxySnakeShieldPane.getChildren.size - 1))
    val hero = player.getCurrentHero
    proxySkillVsDamageMap.put(snakeShieldSkill, hero.getAttack
      * hero.getCollectionOfSkills.get(index).getCoefficients.get(0))
    justInTimeSnakeShieldSkill = snakeShieldSkill
  }

  def pullSkill(): Skill ={
    val outputSkill = justInTimeSnakeShieldSkill
    justInTimeSnakeShieldSkill = null
    outputSkill
  }

  def destroy(skill: Skill){
    val indexPair = hashVsPairMap.get(skill.hashCode)
    val heroSkillCollectionIndex = indexPair.getKey
    val proxySkillContainerIndex = indexPair.getValue
    this.player.getCurrentHero.getCollectionOfSkills.remove(heroSkillCollectionIndex)
    this.proxySnakeShieldPane.getChildren.remove(proxySkillContainerIndex)
    this.skillVsProxyMap.remove(skill)
  }
}