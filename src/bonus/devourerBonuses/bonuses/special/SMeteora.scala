package bonus.devourerBonuses.bonuses.special

import bonus.bonuses.ExtendedBonus
import javafx.scene.image.ImageView
import javafx.scene.text.Text
import managment.actionManagement.actions.ActionEventFactory

final class SMeteora(name: String, id: Int, sprite: ImageView) extends ExtendedBonus(name, id, sprite){

  private val START_COUNT = 0

  private val END_COUNT = 25

  private val DAMAGE = 5000

  private var count: Int = START_COUNT

  private val text: Text = new Text(START_COUNT + "/" + END_COUNT)

  this.installContainer(this.text)

  override def use(): Unit = {
    if (this.count + 1 == END_COUNT) {
      this.count = START_COUNT
      val player = playerManager.getCurrentTeam.getCurrentPlayer
      val opponentHero = playerManager.getOpponentTeam.getCurrentPlayer.getCurrentHero
      val eventEngine = this.actionManager.getEventEngine
      eventEngine.handle(ActionEventFactory.getBeforeDealDamage(player, opponentHero, DAMAGE))
      if (opponentHero.getDamage(DAMAGE)){
        eventEngine.handle(ActionEventFactory.getAfterDealDamage(player, opponentHero, DAMAGE))
      }
    }
    else {
      this.count += 1
      this.text.setText(count + "/" + END_COUNT)
    }
  }
}