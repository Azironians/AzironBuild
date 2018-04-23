package bonus.devourerBonuses.bonuses.experience.boneCell

import bonus.bonuses.Bonus
import javafx.scene.image.ImageView
import managment.actionManagement.service.components.providerComponent.ProviderComponent

final class XBoneCell(name: String, id: Int, sprite: ImageView) extends Bonus(name, id, sprite){

  private  var previousProviderComponent: ProviderComponent[java.lang.Integer] = _

  private val customProviderComponent = new ProviderComponent[java.lang.Integer] {
    override final def getValue: Integer = ???

    override final def getPriority: Int = ???

    override final def setPriority(priority: Int): Unit = ???
  }

  override def use(): Unit = {
    val opponentHero = playerManager.getOpponentATeam.getCurrentPlayer.getCurrentHero
    val bonusManager = opponentHero.getBonusManager
    val providerComponentList = bonusManager.getProviderComponentList
    val index = bonusManager.getAvailableProviderComponent

  }

  private final class CustomProviderComponent(var priority: Int) extends ProviderComponent[java.lang.Integer]{

    override def getValue: Integer = -1

    override def getPriority: Int = this.priority

    override def setPriority(priority: Int): Unit = this.priority = priority
  }
}
