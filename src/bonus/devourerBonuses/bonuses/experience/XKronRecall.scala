package bonus.devourerBonuses.bonuses.experience

import bonus.bonuses.ExtendedBonus
import heroes.abstractHero.hero.Hero
import javafx.scene.image.ImageView
import javafx.scene.text.Text
import managment.actionManagement.actions.{ActionEvent, ActionType}
import managment.actionManagement.service.components.handleComponet.HandleComponent
import managment.actionManagement.service.engine.services.DynamicHandleService
import managment.playerManagement.{ATeam, Player}

import scala.collection.mutable

final class XKronRecall(name: String, id: Int, sprite: ImageView) extends ExtendedBonus(name, id, sprite)
  with DynamicHandleService {

  private val START_COUNT = 0

  private val END_COUNT = 3

  private var count: Int = START_COUNT

  private val text: Text = new Text(START_COUNT + "/" + END_COUNT)

  this.installContainer(this.text)

  override def use(): Unit = {
    if (this.count + 1 == END_COUNT) {
      //Put handler component:
      this.actionManager.getEventEngine.addHandler(getHandlerInstance)
      this.count = START_COUNT
    }
    else {
      this.count += 1
      this.text.setText(count + "/" + END_COUNT)
    }
  }

  override def getHandlerInstance: HandleComponent = new HandleComponent {

    private val player: Player = playerManager.getCurrentTeam.getCurrentPlayer

    private val opponentTeam: ATeam = playerManager.getOpponentTeam

    private var work: Boolean = true

    private val opponentLevelMap = new mutable.HashMap[Hero, java.lang.Integer]

    private var additionalBonusCount: Int = 0

    override final def setup(): Unit = {
      val allOpponentPlayers = opponentTeam.getAllPlayers
      for (i <- 0 until allOpponentPlayers.size()){
        val opponentPlayer = allOpponentPlayers.get(i)
        val allOpponentHeroes = opponentPlayer.getAllHeroes
        for (j <- 0 until allOpponentHeroes.size()){
          val opponentHero = allOpponentHeroes.get(j)
          this.opponentLevelMap.+=(opponentHero -> opponentHero.getLevel)
        }
      }
    }

    override final def handle(actionEvent: ActionEvent): Unit = {
      val actionType = actionEvent.getActionType
      val player = actionEvent.getPlayer
      val allHeroes = player.getAllHeroes
      for (i <- 0 until allHeroes.size()){
        val opponentHero = allHeroes.get(i)
        val currentLevel = opponentHero.getLevel
        val inMapLevel = this.opponentLevelMap(opponentHero)
        val comparison = currentLevel - inMapLevel
        if (comparison > 0){
          this.additionalBonusCount += comparison
        }
        this.opponentLevelMap.+=(opponentHero -> currentLevel)
      }
      if (actionType == ActionType.AFTER_USED_BONUS && this.player == player && this.additionalBonusCount > 0){
        battleManager.loadRandomBonuses(player.getCurrentHero)
        this.additionalBonusCount -= 1
      }
    }

    override final def getName: String = "KronRecall"

    override final def getCurrentPlayer: Player = this.player

    override final def isWorking: Boolean = this.work

    override final def setWorking(able: Boolean): Unit = this.work = able
  }
}