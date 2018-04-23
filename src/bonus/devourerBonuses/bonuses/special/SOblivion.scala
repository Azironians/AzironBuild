package bonus.devourerBonuses.bonuses.special

import bonus.bonuses.Bonus
import javafx.scene.image.ImageView

final class SOblivion(name: String, id: Int, sprite: ImageView) extends Bonus(name, id, sprite) {

  private val SKILL_BOOST_COEFFICIENT: Double = 1.20

  private val ATTACK_LOSING: Double = 10.0

  override def use(): Unit = {
    val hero = playerManager.getCurrentTeam.getCurrentPlayer.getCurrentHero
    val skills = hero.getCollectionOfSkills
    for (i <- 0 until skills.size()) {
      val skill = skills.get(i)
      val lastCoefficients = skill.getCoefficients
      for (j <- 0 until lastCoefficients.size()) {
        val lastCoefficient = lastCoefficients.get(j)
        lastCoefficients.set(i, lastCoefficient * SKILL_BOOST_COEFFICIENT)
      }
    }
    val lastAttack = hero.getAttack
    val newAttack = lastAttack - ATTACK_LOSING
    if (newAttack > 0) {
      hero.setAttack(newAttack)
    } else {
      //Attack can't be less than zero:
      hero.setAttack(0)
    }
  }
}