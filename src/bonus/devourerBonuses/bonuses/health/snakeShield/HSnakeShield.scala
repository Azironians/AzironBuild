package bonus.devourerBonuses.bonuses.health.snakeShield

import java.util
import java.util.function.Consumer

import bonus.bonuses.Bonus
import heroes.abstractHero.skills.Skill
import javafx.scene.image.ImageView
import managment.actionManagement.actions.ActionEvent
import managment.actionManagement.service.components.handleComponet.{HandleComponent, IllegalSwitchOffHandleComponentException}
import managment.actionManagement.service.engine.services.RegularHandleService
import managment.playerManagement.Player

final class HSnakeShield(name: String, val id: Int, sprite: ImageView) extends Bonus(name, id, sprite)
  with RegularHandleService {

  private var snakeShieldSkillProxyComponent: SnakeShieldSkillProxyComponent = _

  override def use(): Unit = {
    val hero = playerManager.getCurrentTeam.getCurrentPlayer.getCurrentHero
    val skills: util.List[Skill] = hero.getCollectionOfSkills
    skills forEach (skill => {
      if (skill.getName == "FlameSnakes") {

      }
    })
  }

  override def getRegularHandlerInstance(player: Player): HandleComponent = new HandleComponent {

    override def setup(): Unit = {
      snakeShieldSkillProxyComponent = new SnakeShieldSkillProxyComponent(player)
    }

    override def handle(actionEvent: ActionEvent): Unit = {}

    override def getName: String = "SnakeShield"

    override def getCurrentPlayer: Player = player

    override def isWorking: Boolean = true

    override def setWorking(able: Boolean): Unit = throw new IllegalSwitchOffHandleComponentException()
  }
}