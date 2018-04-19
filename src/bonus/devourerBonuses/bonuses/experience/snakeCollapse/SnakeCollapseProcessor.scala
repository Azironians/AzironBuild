package bonus.devourerBonuses.bonuses.experience.snakeCollapse

import heroes.abstractHero.skills.Skill
import managment.actionManagement.ActionManager
import managment.actionManagement.actionProccessors.SkillProcessor
import managment.battleManagement.BattleManager
import managment.playerManagement.{ATeam, PlayerManager}

class SnakeCollapseProcessor(actionManager: ActionManager, battleManager: BattleManager, playerManager: PlayerManager)
  extends SkillProcessor(actionManager, battleManager, playerManager){

  override def setTeamAndSkill(currentTeam: ATeam, skill: Skill): Unit = {
    this.currentTeam = currentTeam
    this.skill = new SnakeCollapseSkill()
  }
}