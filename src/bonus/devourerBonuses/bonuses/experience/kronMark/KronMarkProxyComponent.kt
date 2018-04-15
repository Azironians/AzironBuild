package bonus.devourerBonuses.bonuses.experience.kronMark

import heroes.abstractHero.skills.Skill
import javafx.scene.layout.Pane
import lib.duplexMap.DuplexMap
import managment.actionManagement.service.engine.EventEngine
import managment.playerManagement.Player
import managment.playerManagement.PlayerManager

class KronMarkProxyComponent(val player: Player?) {

    private val proxyMarkKronPane: Pane = Pane()

    private val hashVsPairMap: MutableMap<Int, Pair<Int, Int>> = HashMap()

    val skillVsProxyMap = DuplexMap<Skill, Skill>()

    private var justInTimeSkill: Skill? = null

    init {
        initProxyPane(player)
    }

    private fun initProxyPane(player: Player?){
        val skillPane = player!!.location.skillPane
        this.proxyMarkKronPane.layoutX = skillPane.layoutX
        this.proxyMarkKronPane.layoutY = skillPane.layoutY
        this.proxyMarkKronPane.minWidth = skillPane.minWidth
        this.proxyMarkKronPane.maxHeight = skillPane.maxHeight
    }


    fun packSkill(): Boolean {
        val currentHero = player!!.currentHero
        val skills = currentHero.collectionOfSkills
        val availableSkill = getFirstAvailableSkill(skills)
        if (availableSkill != null) {
            //Unwrapping pair:
            val currentSkill = availableSkill.first
            val containerIndex = availableSkill.second
            //Get main super skill location:
            val mainSkillContainers = player.location.skillPane.children
            val skillNode = mainSkillContainers[containerIndex]
            //Creating custom skill:
            val kronMarkSkill = KronMarkSkill(this)
            val kronMarkContainer = kronMarkSkill.container
            kronMarkContainer.layoutX = skillNode.layoutX
            kronMarkContainer.layoutY = skillNode.layoutY
            skills.add(kronMarkSkill)
            this.proxyMarkKronPane.children.add(kronMarkContainer)
            this.skillVsProxyMap.bind(currentSkill, kronMarkSkill)
            this.hashVsPairMap[kronMarkSkill.hashCode()] = Pair(skills.size - 1, proxyMarkKronPane.children.size - 1)
            this.justInTimeSkill = kronMarkSkill
            return true
        }
        return false
    }

    private fun getFirstAvailableSkill(skills: List<Skill>): Pair<Skill, Int>? {
        for (i in 0 until skills.size) {
            val currentSkill = skills[i]
            if (!currentSkill.isReady && !skillVsProxyMap.contains(currentSkill)) {
                return Pair(currentSkill, i)
            }
        }
        return null
    }

    fun invokeSkill(playerManager: PlayerManager) {
        val hero = playerManager.currentTeam.currentPlayer.currentHero
        val experienceBoostCoefficient =KronMarkAssistant.formExperienceBoostCoefficient(hero.collectionOfSkills)
        val experienceBoost = hero.currentExperience * experienceBoostCoefficient
        hero.addExperience(experienceBoost)
    }

    fun destroy(skill: Skill) {
        val indexPair = hashVsPairMap[skill.hashCode()]
        if (indexPair != null){
            val heroSkillCollectionIndex = indexPair.first
            val proxySkillContainerIndex = indexPair.second
            this.player!!.currentHero.collectionOfSkills.removeAt(heroSkillCollectionIndex)
            this.proxyMarkKronPane.children.removeAt(proxySkillContainerIndex)
            this.skillVsProxyMap.remove(skill)
        }
    }

    fun getJustInTimeSkill(): Skill? {
        val outputSkill = justInTimeSkill
        this.justInTimeSkill = null
        return outputSkill
    }
}