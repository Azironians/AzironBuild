package bonus.devourerBonuses.bonuses.experience.oracle

import java.util.Collections

import heroes.abstractHero.skills.swapSkill.AbstractSimplifiedSkill
import javafx.scene.image.ImageView
import javafx.scene.media.Media
import managment.battleManagement.BattleManager
import managment.playerManagement.PlayerManager

private final class ConcentrationAbility(name: String = "Concentration"
                                 , reload: Int = 1
                                 , requiredLevel: Int = 1
                                 , coefficients: java.util.List[java.lang.Double] = Collections.singletonList(1.0)
                                 , sprite: ImageView = new ImageView()
                                 , description: ImageView = new ImageView()
                                 , mediaList: java.util.List[Media] = Collections.emptyList()
                                ) extends AbstractSimplifiedSkill(name, reload, requiredLevel, coefficients, sprite
  , description, mediaList){

  var concentration = 0

  override def use(battleManager: BattleManager, playerManager: PlayerManager): Unit = {
    val team = playerManager.getCurrentTeam
    val player = team.getCurrentPlayer
    val hero = player.getCurrentHero
    hero.addExperience(concentration)
    actionManager.refreshScreen()
    if (battleManager.isEndTurn){
      actionManager.endTurn(team)
    }
  }

  override def showAnimation(): Unit = ???
}
