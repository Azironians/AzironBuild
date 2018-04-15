package bonus.devourerBonuses.bonuses.experience.kronMark

import heroes.abstractHero.skills.abstractSkill.AbstractSkill
import javafx.scene.image.ImageView
import javafx.scene.media.Media
import managment.battleManagement.BattleManager
import managment.playerManagement.PlayerManager

class KronMarkSkill(private val proxyComponent: KronMarkProxyComponent
                    , name:String = ""
                    , reload: Int = 1
                    , requiredLevel: Int = 1
                    , coefficients: List<Double> = listOf()
                    , sprite: ImageView = ImageView()
                    , description: ImageView = ImageView()
                    , listOfVoices: List<Media> = listOf())
    : AbstractSkill(name, reload, requiredLevel, coefficients, sprite, description, listOfVoices) {

    override fun use(battleManager: BattleManager?, playerManager: PlayerManager?) {
        val hero = playerManager!!.currentTeam.currentPlayer.currentHero
        val experienceBoostCoefficient =KronMarkAssistant.formExperienceBoostCoefficient(hero.collectionOfSkills)
        val experienceBoost = hero.currentExperience * experienceBoostCoefficient
        hero.addExperience(experienceBoost)
        destroy()
    }

    private fun destroy() = proxyComponent.destroy(this)

    override fun showAnimation() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}