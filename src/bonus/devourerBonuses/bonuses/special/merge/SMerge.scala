package bonus.devourerBonuses.bonuses.special.merge

import bonus.bonuses.ExtendedBonus
import heroes.abstractHero.hero.Hero
import heroes.abstractHero.skills.Skill
import javafx.scene.image.ImageView
import javafx.scene.text.Text
import managment.actionManagement.actions.ActionEventFactory

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
      this.mergeSkills(hero)
      this.count = START_COUNT

    }
    else {
      this.count += 1
      this.text.setText(count + "/" + END_COUNT)
    }
  }

  private def mergeSkills(hero: Hero): Unit = {
    val skills = hero.getCollectionOfSkills
    val basicSkillCount = 3
    if (skills.size() > basicSkillCount){

    }
  }


  private def foundBasicSkills(skills: java.util.List[Skill]): List[Int] = {
    var foundFlameSnakes: Boolean = false
    var foundRegeneration: Boolean = false
    var foundConsuming: Boolean = false
    for (i <- 0 until skills.size()){

    }
  }
}
