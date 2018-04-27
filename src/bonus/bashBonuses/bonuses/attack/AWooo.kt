package bonus.bashBonuses.bonuses.attack

import bonus.bonuses.Bonus
import javafx.scene.image.ImageView
import managment.actionManagement.actions.ActionEventFactory

class AWooo(name: String, id: Int, sprite: ImageView) : Bonus(name, id, sprite){

    private val DAMAGE_COEFFICIENT: Double = 0.1

    private val START_POSITION: Int = 1

    private var counter: Int  = START_POSITION

    override fun use() {
        val player = playerManager.currentTeam.currentPlayer
        val hero = player.currentHero
        val opponentHero = playerManager.opponentTeam.currentPlayer.currentHero
        val damage = hero.attack * DAMAGE_COEFFICIENT
        val eventEngine = actionManager.eventEngine
        for (i  in 1..counter){
            eventEngine.handle(ActionEventFactory.getBeforeDealDamage(player, opponentHero, damage))
            if (opponentHero.getDamage(damage)){
                eventEngine.handle(ActionEventFactory.getAfterDealDamage(player, opponentHero, damage))
            }
        }
        counter++
    }
}