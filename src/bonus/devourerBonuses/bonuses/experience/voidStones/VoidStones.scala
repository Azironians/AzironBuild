package bonus.devourerBonuses.bonuses.experience.voidStones

import bonus.bonuses.Bonus
import bonus.bonuses.subInterfaces.QuestBonus
import heroes.abstractHero.hero.Hero
import javafx.scene.image.ImageView
import javafx.scene.layout.Pane

import scala.collection.mutable

final class VoidStones(name: String, id: Int, sprite: ImageView) extends Bonus(name, id, sprite) with QuestBonus{

  private val COMPLETE: Int = 3

  private var isQuestDropped = false

  private val rightFragment: RightFragment = new RightFragment(this)

  private val leftFragment: LeftFragment = new LeftFragment(this)

  private var progress: Int = 0

  private var hero: Hero = _

  override def use(): Unit = {
    if (!isQuestDropped){
      this.hero = playerManager.getCurrentTeam.getCurrentPlayer.getCurrentHero
      val bonusCollection = hero.getBonusCollection
      bonusCollection.add(rightFragment)
      bonusCollection.add(leftFragment)
      this.isQuestDropped = true
    }
  }

  def checkFragments(): Unit ={
    if (leftFragment.leftIsActive && rightFragment.rightIsActive){
      this.progress += 1
      if (progress >= COMPLETE){
        this.rightFragment.rightIsActive = false
        this.leftFragment.leftIsActive = false
        this.isQuestDropped = false
        val bonusCollection = hero.getBonusCollection
        bonusCollection.remove(rightFragment)
        bonusCollection.remove(leftFragment)
        destroyBonus()
      }
    }
  }

  private var bonusVsContainerMap = new mutable.HashMap[Bonus, Pane]()

  private def destroyBonus(): Unit ={
    val opponentHeroBonusCollection = playerManager.getOpponentTeam.getCurrentPlayer.getCurrentHero.getBonusCollection
    for (i <- 0 until opponentHeroBonusCollection.size() - 1)  {
      val bonus = opponentHeroBonusCollection.get(i)
      bonusVsContainerMap += (bonus -> preparePane(bonus))
    }
  }

  private def preparePane(bonus: Bonus) = {
    //Abstract container build:
    new Pane()
  }

  override def getProgress: Int = progress

  override def setProgress(progress: Int): Unit = this.progress = progress
}