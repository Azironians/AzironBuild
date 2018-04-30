package bonus.devourerBonuses.bonuses.special

import bonus.bonuses.Bonus
import javafx.scene.image.ImageView
import managment.actionManagement.actions.{ActionEvent, ActionEventFactory, ActionType}
import managment.actionManagement.service.components.handleComponet.HandleComponent
import managment.actionManagement.service.engine.services.DynamicHandleService
import managment.playerManagement.Player

final class SVolley(name: String, id: Int, sprite: ImageView) extends Bonus(name, id, sprite)
  with DynamicHandleService {

  private val DAMAGE: Int = 10

  override def use(): Unit = {
    this.actionManager.getEventEngine.addHandler(getHandlerInstance)
  }

  override def getHandlerInstance: HandleComponent = new HandleComponent {

    private val player: Player = playerManager.getCurrentTeam.getCurrentPlayer

    private var work: Boolean = true

    override final def setup(): Unit = {}

    override final def handle(actionEvent: ActionEvent): Unit = {
      val actionType = actionEvent.getActionType
      val player = actionEvent.getPlayer
      if (player == this.player && actionType == ActionType.BEFORE_USED_SKILL) {
        val opponentHero = playerManager.getOpponentTeam.getCurrentPlayer.getCurrentHero
        val eventEngine = actionManager.getEventEngine
        eventEngine.handle(ActionEventFactory.getBeforeDealDamage(this.player, opponentHero, DAMAGE))
        if (opponentHero.getDamage(DAMAGE)){
          eventEngine.handle(ActionEventFactory.getAfterDealDamage(this.player, opponentHero, DAMAGE))
        }
      }
    }

    override final def getName: String = "Volley"

    override final def getCurrentPlayer: Player = this.player

    override final def isWorking: Boolean = this.work

    override final def setWorking(able: Boolean): Unit = this.work = able
  }
}