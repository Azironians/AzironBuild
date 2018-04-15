package bonus.devourerBonuses.bonuses.experience.oracle

import bonus.bonuses.Bonus
import javafx.scene.image.ImageView

final class Concentration(name: String = "", id: Int = 0, imageView: ImageView = new ImageView())
  extends Bonus(name, id, imageView){

  private val CONCENTRATION_BOOST = 3

  override def use(): Unit = {
    val hero = playerManager.getCurrentTeam.getCurrentPlayer.getCurrentHero
    val additionalAbilityMap: java.util.Map[String, Object] = hero.getAdditionalAbilityMap
    val ability: Object = additionalAbilityMap.get("Concentration")
    ability match {
      case concentrationAbility: ConcentrationAbility =>
        concentrationAbility.concentration += CONCENTRATION_BOOST
    }
  }
}