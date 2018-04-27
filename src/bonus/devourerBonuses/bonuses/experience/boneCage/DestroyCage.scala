package bonus.devourerBonuses.bonuses.experience.boneCage

import bonus.bonuses.ExtendedBonus
import heroes.abstractHero.hero.Hero
import javafx.scene.image.ImageView
import javafx.scene.text.Text

final class DestroyCage(val opponentHero: Hero, name: String = "", id: Int = 0, sprite: ImageView = new ImageView())
  extends ExtendedBonus(name, id, sprite) {

  private val START_COUNT = 0

  private val END_COUNT = 3

  private var count: Int = START_COUNT

  private val text: Text = new Text(START_COUNT + "/" + END_COUNT)

  this.installContainer(this.text)

  override def use(): Unit = {
    if (this.count + 1 == END_COUNT) {
      this.count = START_COUNT
      val opponentHeroBonusCollection = opponentHero.getBonusCollection
      opponentHeroBonusCollection.remove(this)
      opponentHero.setAttackAccess(true)
    }
    else {
      this.count += 1
      this.text.setText(count + "/" + END_COUNT)
    }
  }
}