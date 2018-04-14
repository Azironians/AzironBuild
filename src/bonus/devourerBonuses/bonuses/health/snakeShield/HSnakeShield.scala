package bonus.devourerBonuses.bonuses.health.snakeShield

import java.util

import bonus.bonuses.Bonus
import heroes.abstractHero.hero.Hero
import heroes.abstractHero.skills.Skill
import javafx.scene.image.ImageView
import javafx.util
import managment.actionManagement.actions.{ActionEvent, ActionType}
import managment.actionManagement.service.components.handleComponet.{HandleComponent, IllegalSwitchOffHandleComponentException}
import managment.actionManagement.service.engine.services.RegularHandleService
import managment.playerManagement.Player

final class HSnakeShield(name: String, val id: Int, sprite: ImageView) extends Bonus(name, id, sprite)
  with RegularHandleService {

  private var snakeShieldSkillProxyComponent: SnakeShieldSkillProxyComponent = _

  override def use(): Unit = {
    val hero = playerManager.getCurrentTeam.getCurrentPlayer.getCurrentHero
    val skills: java.util.List[Skill] = hero.getCollectionOfSkills
    for (i <- 0 to skills.size){
      val skill = skills.get(i)
      if (skill.getName.equals("FlameSnakes")){
        skill.reset()
        snakeShieldSkillProxyComponent.packSkill(i, skills)
        wireActionManager(snakeShieldSkillProxyComponent.justInTimeSnakeShieldSkill)
      }
    }
  }

  private def wireActionManager(skill: Skill): Unit = {
    skill.setActionManager(actionManager)
  }

  override def getRegularHandlerInstance(player: Player): HandleComponent = new HandleComponent {

    override final def setup(): Unit = {
      snakeShieldSkillProxyComponent = new SnakeShieldSkillProxyComponent(player)
    }

    override final def handle(actionEvent: ActionEvent): Unit = {
      if (actionEvent.getActionType == ActionType.BEFORE_DEAL_DAMAGE){
        val data = actionEvent.getData
        data match {
          case heroVsDamage: javafx.util.Pair[Hero, Double] =>
            val hero = heroVsDamage.getKey

            // use here while loop
//            for (i <- 0 to hero.getCollectionOfSkills.size){
//              val skill = hero.getCollectionOfSkills.get(i)
//              if (skill.getName == "SnakeShield"){
//                val comparison =
//                hero.setArmor(snakeShieldSkillProxyComponent.proxySkillVsDamageMap.get())
//
//
//
//              }
//            }


        }
      }



    }

    override final def getName: String = "SnakeShield"

    override final def getCurrentPlayer: Player = player

    override final def isWorking: Boolean = true

    override final def setWorking(able: Boolean): Unit = throw new IllegalSwitchOffHandleComponentException()
  }
}