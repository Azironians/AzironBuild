package bonus.devourerBonuses.bonuses.experience.kronMark

import heroes.abstractHero.skills.Skill

object KronMarkAssistant {

    private val ADDITIONAL_EXPERIENCE_COEFFICIENT = 1.05

    fun formExperienceBoostCoefficient(skills: List<Skill>): Double {
        var currentExperienceCoefficient = 1.0
        for (skill in skills) {
            if (skill.name == "HKronMark") {
                currentExperienceCoefficient *= ADDITIONAL_EXPERIENCE_COEFFICIENT
            }
        }
        return currentExperienceCoefficient - 1
    }
}