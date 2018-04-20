package bonus.devourerBonuses.bonuses.experience.voidStones

import bonus.bonuses.Bonus
import javafx.scene.image.ImageView

final class LeftFragment(val parent: VoidStones, name: String = "", id: Int = 0, sprite: ImageView = new ImageView())
  extends Bonus(name, id, sprite){

  var leftIsActive = false

  override def use(): Unit = {
    if (!leftIsActive){
      this.leftIsActive = true
      this.parent.checkFragments()
    }
  }
}