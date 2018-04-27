package bonus.devourerBonuses.bonuses.special.merge

import bonus.bonuses.ExtendedBonus
import heroes.abstractHero.hero.Hero
import heroes.abstractHero.skills.Skill
import javafx.scene.Node
import javafx.scene.image.ImageView
import javafx.scene.layout.{AnchorPane, Pane}
import javafx.scene.text.Text
import managment.playerManagement.Player

import scala.collection.mutable

final class SMerge(name: String, id: Int, sprite: ImageView) extends ExtendedBonus(name, id, sprite) {

  private val START_COUNT = 0

  private val END_COUNT = 8

  private val REQUIRED_LEVEL = 6

  private val EXPERIENCE_BOOST = 50

  private var count: Int = START_COUNT

  private val text: Text = new Text(START_COUNT + "/" + END_COUNT)

  this.installContainer(this.text)

  override def use(): Unit = {
    val player = playerManager.getCurrentTeam.getCurrentPlayer
    val hero = player.getCurrentHero
    if (hero.getLevel >= REQUIRED_LEVEL){
      if (this.count + 1 == END_COUNT) {
        //FIXME: MOVE LOCATION CLASS ON HERO!!! NOW PLAYER IS TEMPORARY IN SIGNATURE!!!
        this.mergeSkills(hero, player)
        this.count = START_COUNT
      }
      else {
        this.count += 1
        this.text.setText(count + "/" + END_COUNT)
      }
    } else {
      hero.addExperience(EXPERIENCE_BOOST)
    }
  }

  //FIXME: MOVE LOCATION CLASS ON HERO!!! NOW PLAYER IS TEMPORARY IN SIGNATURE!!!
  private def mergeSkills(hero: Hero, player: Player): Unit = {
    val skills = hero.getCollectionOfSkills
    val indexBasicSkillList = this.foundBasicSkills(skills)
    val skillPane: Pane = player.getCurrentHero.getLocation.getSkillPane
    val skillNodes = skillPane.getChildren
    val firstBasicSkillPane = skillNodes.get(indexBasicSkillList.head)
    if (indexBasicSkillList.nonEmpty){
      for (i <- indexBasicSkillList){
        skills.remove(i)
        skillNodes.remove(i)
      }
      this.createDevouringSkill(skillPane, player, firstBasicSkillPane )
    }
  }

  private def foundBasicSkills(skills: java.util.List[Skill]): mutable.MutableList[Int] = {
    val indexBasicSkillList: mutable.MutableList[Int] = new mutable.MutableList[Int]
    var foundFlameSnakes: Boolean = false
    var foundRegeneration: Boolean = false
    var foundConsuming: Boolean = false
    for (i <- 0 until skills.size()){
      val skill = skills.get(i)
      if (!foundFlameSnakes && skill.getName.equals("FlameSnakes")){
        indexBasicSkillList.+=(i)
        foundFlameSnakes = true
      }
      if (!foundRegeneration && skill.getName.equals("Regeneration")){
        indexBasicSkillList.+=(i)
        foundRegeneration = true
      }
      if (!foundConsuming && skill.getName.equals("Consuming")){
        indexBasicSkillList.+=(i)
        foundConsuming = true
      }
      if (foundFlameSnakes && foundRegeneration && foundConsuming){
        return indexBasicSkillList
      }
    }
    mutable.MutableList.empty
  }

  private def createDevouringSkill(skillPane: Pane, parentPlayer: Player, firstPane: Node): Unit = {
    val devouringSkill = new DevouringSkill()
    val layoutX = firstPane.getLayoutX
    val layoutY = firstPane.getLayoutY
    devouringSkill.install(skillPane, parentPlayer.getCurrentHero, layoutX, layoutY, layoutX, -127
      , parentPlayer.getCurrentHero.getLocation.isInvert)
  }
}