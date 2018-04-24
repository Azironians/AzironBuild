package bonus.devourerBonuses.bonuses.special.merge

import bonus.bonuses.ExtendedBonus
import heroes.abstractHero.hero.Hero
import heroes.abstractHero.skills.Skill
import javafx.scene.image.ImageView
import javafx.scene.text.Text
import managment.playerManagement.Player

import scala.collection.mutable

final class SMerge(name: String, id: Int, sprite: ImageView) extends ExtendedBonus(name, id, sprite) {

  private val START_COUNT = 0

  private val END_COUNT = 8

  private var count: Int = START_COUNT

  private val text: Text = new Text(START_COUNT + "/" + END_COUNT)

  this.installContainer(this.text)

  override def use(): Unit = {
    if (this.count + 1 == END_COUNT) {
      val player = playerManager.getCurrentTeam.getCurrentPlayer
      val hero = player.getCurrentHero
      //FIXME: MOVE LOCATION CLASS ON HERO!!! NOW PLAYER IS TEMPORARY IN SIGNATURE!!!
      this.mergeSkills(hero, player)
      this.count = START_COUNT

    }
    else {
      this.count += 1
      this.text.setText(count + "/" + END_COUNT)
    }
  }

  //FIXME: MOVE LOCATION CLASS ON HERO!!! NOW PLAYER IS TEMPORARY IN SIGNATURE!!!
  private def mergeSkills(hero: Hero, player: Player): Unit = {
    val skills = hero.getCollectionOfSkills
    val indexBasicSkillList = this.foundBasicSkills(skills)
    if (indexBasicSkillList.nonEmpty){
      for (i <- indexBasicSkillList){
        val skillPane = player.getLocation.getSkillPane
        val skillNodes = skillPane.getChildren
        skills.remove(i)
        skillNodes.remove(i)
      }
      val firstIndex = indexBasicSkillList.head
      this.createDevouringSkill(firstIndex, skills)
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

  private def createDevouringSkill(position: Int, skills : java.util.List[Skill])  = {
    //TODO...
  }
}