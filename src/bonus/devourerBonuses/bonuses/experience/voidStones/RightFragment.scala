package bonus.devourerBonuses.bonuses.experience.voidStones

import bonus.bonuses.Bonus
import javafx.scene.image.ImageView

final class RightFragment (val parent: VoidStones, name: String = "", id: Int = 0, sprite: ImageView = new ImageView())
  extends Bonus(name, id, sprite){

  var rightIsActive = false

  override def use(): Unit = {
    if (!rightIsActive){
      this.rightIsActive = true
      this.parent.checkFragments()
    }
  }
}