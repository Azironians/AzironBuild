package bonus.devourerBonuses.bonuses.experience

import bonus.bonuses.Bonus
import javafx.scene.image.ImageView
import managment.actionManagement.actions.{ActionEvent, ActionType}
import managment.actionManagement.service.components.handleComponet.HandleComponent
import managment.actionManagement.service.engine.services.DynamicHandleService
import managment.playerManagement.Player
import managment.processors.Processor

final class XSnakeCollapse(name: String, id: Int, sprite: ImageView) extends Bonus(name, id, sprite)
  with DynamicHandleService {

  override def use(): Unit = {
    actionManager.getEventEngine.addHandler(getHandlerInstance)
  }

  override def getHandlerInstance: HandleComponent = new HandleComponent {

    private var player: Player = _

    private var work: Boolean = _

    override final def setup(): Unit = {
      this.player = playerManager.getCurrentTeam.getCurrentPlayer
      this.work = true
    }

    override final def handle(actionEvent: ActionEvent): Unit = {
      if (actionEvent.getPlayer == player) {
        actionEvent.getActionType match {
          case ActionType.BEFORE_USED_SKILL =>
            val data = actionEvent.getData
            data match {
              case skillName: String =>
                if (skillName.equals("FlameSnakes")) {

                }
            }
          case ActionType.AFTER_USED_SKILL =>

        }
      }
    }

    override final def getName: String = "SnakeCollapse"

    override final def getCurrentPlayer: Player = player

    override final def isWorking: Boolean = work

    override final def setWorking(able: Boolean): Unit = work = able
  }
}