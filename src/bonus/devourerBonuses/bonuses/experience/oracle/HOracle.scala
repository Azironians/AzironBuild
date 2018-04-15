package bonus.devourerBonuses.bonuses.experience.oracle

import bonus.bonuses.ExtendedBonus
import heroes.abstractHero.skills.Skill
import javafx.scene.image.ImageView
import javafx.scene.text.Text

final class HOracle(name: String, id: Int, sprite: ImageView) extends ExtendedBonus(name, id, sprite){

  private val START_COUNT = 0

  private val END_COUNT = 7

  private var count: Int = START_COUNT

  private val text: Text = new Text(START_COUNT + "/" + END_COUNT)

  {
    installContainer(text)
  }


  override def use(): Unit = {
    if (count + 1 == END_COUNT) {
      addConcentration()
      count = START_COUNT
    }
    else {
      this.count += 1
      text.setText(count + "/" + END_COUNT)
    }
  }

  private def addConcentration(): Unit ={
    val hero = playerManager.getCurrentTeam.getCurrentPlayer.getCurrentHero
    val bonusCollection = hero.getBonusCollection
    hero.getAdditionalAbilityMap.put("Concentration", createAbility())
    bonusCollection.remove(this)
    bonusCollection.add(new Concentration())
  }

  private def createAbility(): Skill = {
    val concentrationAbility = new ConcentrationAbility()
    concentrationAbility.setActionManager(actionManager)
    concentrationAbility
  }
}