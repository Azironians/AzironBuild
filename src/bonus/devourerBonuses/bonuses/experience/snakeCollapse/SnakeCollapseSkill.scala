package bonus.devourerBonuses.bonuses.experience.snakeCollapse

import heroes.abstractHero.skills.abstractSkill.AbstractSkill
import javafx.scene.image.ImageView
import javafx.scene.media.Media
import managment.battleManagement.BattleManager
import managment.playerManagement.PlayerManager

final class SnakeCollapseSkill(name: String = "",
                         reload: Int = 0,
                         requiredLevel: Int = 1,
                         coefficients: java.util.List[java.lang.Double] = new java.util.ArrayList[java.lang.Double]
                         , spite: ImageView = new ImageView()
                         , description: ImageView = new ImageView()
                         , listMedia: java.util.List[Media] = new java.util.ArrayList[Media])
  extends AbstractSkill(name, reload, requiredLevel, coefficients, spite, description, listMedia){

  override def use(battleManager: BattleManager, playerManager: PlayerManager): Unit = {
    val damage = getParent.getAttack * coefficients.get(0)
    val opponentPlayer = playerManager.getOpponentTeam.getCurrentPlayer
    val opponentHero = opponentPlayer.getCurrentHero
    opponentHero.removeExperience(damage)
  }

  override def showAnimation(): Unit = ???
}
