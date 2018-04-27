package bonus.devourerBonuses.bonuses.special.merge

import heroes.abstractHero.skills.abstractSkill.AbstractSkill
import javafx.scene.image.ImageView
import javafx.scene.media.Media
import managment.actionManagement.actions.ActionEventFactory
import managment.battleManagement.BattleManager
import managment.playerManagement.PlayerManager

final class DevouringSkill(name: String = "",
                           reload: Int = 0,
                           requiredLevel: Int = 1,
                           coefficients: java.util.List[java.lang.Double] = new java.util.ArrayList[java.lang.Double]
                           , spite: ImageView = new ImageView()
                           , description: ImageView = new ImageView()
                           , listMedia: java.util.List[Media] = new java.util.ArrayList[Media])
  extends AbstractSkill(name, reload, requiredLevel, coefficients, spite, description, listMedia) {

  val SPLIT_COEFFICIENT: Double = 4.0

  override def use(battleManager: BattleManager, playerManager: PlayerManager): Unit = {
    val player = playerManager.getCurrentTeam.getCurrentPlayer
    val hero = player.getCurrentHero
    val opponentHero = playerManager.getOpponentTeam.getCurrentPlayer.getCurrentHero
    val damage, healing = opponentHero.getHitPoints - (opponentHero.getHitPoints / SPLIT_COEFFICIENT)
    this.actionManager.getEventEngine.handle(ActionEventFactory.getBeforeDealDamage(player, hero, damage))
    if (opponentHero.getDamage(damage)){
      this.actionManager.getEventEngine.handle(ActionEventFactory.getAfterDealDamage(player, opponentHero, damage))
    }
    hero.getHealing(healing)
  }

  override def showAnimation(): Unit = ???
}
