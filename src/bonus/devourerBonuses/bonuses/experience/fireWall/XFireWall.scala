package bonus.devourerBonuses.bonuses.experience.fireWall


import bonus.bonuses.{Bonus, ExtendedBonus}
import heroes.abstractHero.hero.Hero
import javafx.scene.image.ImageView
import javafx.scene.text.Text
import managment.actionManagement.service.components.providerComponent.ProviderComponent

final class XFireWall(name: String, id: Int, sprite: ImageView) extends ExtendedBonus(name, id, sprite) {

  private val START_COUNT = 0

  private val END_COUNT = 3

  private var count: Int = START_COUNT

  private val text: Text = new Text(START_COUNT + "/" + END_COUNT)

  {
    installContainer(text)
  }

  override def use(): Unit = {
    if (count + 1 == END_COUNT) {
      closeBonuses()
      count = START_COUNT
    }
    else {
      this.count += 1
      text.setText(count + "/" + END_COUNT)
    }
  }

  private def closeBonuses(): Unit = {
    val opponentHero = playerManager.getOpponentTeam.getCurrentPlayer.getCurrentHero
    val bonusManager = opponentHero.getBonusManager
    val providerComponentList = bonusManager.getProviderComponentList
    val bonusCollection = opponentHero.getBonusCollection
    //Go through all providers:
    for (i <- 0 until providerComponentList.size()) {
      //Get previous:
      val previousProviderComponent = providerComponentList.get(i)
      //Create new "Destroy Wall" bonus:
      val destroyWall: Bonus = getCustomBonusInstance(opponentHero, previousProviderComponent)
      //Create new provider:
      val newProviderComponent = new FireWallProviderComponent(bonusCollection, destroyWall)
      bonusManager.setCustomProviderComponent(i, newProviderComponent)
    }
  }

  private final class FireWallProviderComponent(val bonusCollection: java.util.List[Bonus]
                                                , val bonus: Bonus, var priority: Int = 0)
    extends ProviderComponent[java.lang.Integer] {

    override def getValue: java.lang.Integer = bonusCollection.indexOf(bonus)

    override def getPriority: Int = priority

    override def setPriority(priority: Int): Unit = this.priority = priority
  }

  private def getCustomBonusInstance(hero: Hero, previousProviderComponent: ProviderComponent[java.lang.Integer])
  = new DestroyWall(hero, previousProviderComponent)
}