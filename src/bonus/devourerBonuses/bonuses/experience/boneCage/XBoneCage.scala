package bonus.devourerBonuses.bonuses.experience.boneCage

import bonus.bonuses.Bonus
import javafx.scene.image.ImageView
import managment.actionManagement.service.components.providerComponent.ProviderComponent

final class XBoneCage(name: String, id: Int, sprite: ImageView) extends Bonus(name, id, sprite){

  private  var previousProviderComponent: ProviderComponent[java.lang.Integer] = _

  override def use(): Unit = {
    val opponentHero = playerManager.getOpponentTeam.getCurrentPlayer.getCurrentHero
    val bonusManager = opponentHero.getBonusManager
    val providerComponentList = bonusManager.getProviderComponentList
    val index = bonusManager.getAvailableProviderComponent
    this.previousProviderComponent = providerComponentList.get(index)
    val customProviderComponent = new CustomProviderComponent(this.previousProviderComponent.getPriority)
    bonusManager.setCustomProviderComponent(index, customProviderComponent)
    opponentHero.getBonusCollection.add(new DestroyCage(opponentHero))
    opponentHero.setAttackAccess(false)
    this.actionManager.endTurn(playerManager.getCurrentTeam)
  }

  private final class CustomProviderComponent(var priority: Int) extends ProviderComponent[java.lang.Integer]{

    override def getValue: Integer = -1 //id of DestroyCage

    override def getPriority: Int = this.priority

    override def setPriority(priority: Int): Unit = this.priority = priority
  }
}