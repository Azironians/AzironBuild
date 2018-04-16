package managment.actionManagement.actions;

import bonus.bonuses.Bonus;
import com.google.inject.Singleton;
import heroes.abstractHero.hero.Hero;
import javafx.util.Pair;
import managment.playerManagement.Player;

@Singleton
public final class ActionEventFactory {

    public static ActionEvent getStartTurn(final Player player){
        return new ActionEvent(ActionType.START_TURN, player);
    }

    public static ActionEvent getEndTurn(final Player player){
        return new ActionEvent(ActionType.END_TURN, player);
    }

    public static ActionEvent getSkipTurn(final Player player){
        return new ActionEvent(ActionType.SKIP_TURN, player);
    }

    public static ActionEvent getPlayerSwap(final Player player){
        return new ActionEvent(ActionType.SWAP_HEROES, player);
    }

    public static ActionEvent getPlayerOut(final Player player){
        return new ActionEvent(ActionType.PLAYER_OUT, player);
    }

    public static ActionEvent getEndGame(final Player player){
        return new ActionEvent(ActionType.END_GAME, player);
    }

    public static ActionEvent getFrame(final Player player){
        return new ActionEvent(ActionType.GET_FRAME, player);
    }

    public static ActionEvent getTreatment(final Player player){
        return new ActionEvent(ActionType.BEFORE_TREATMENT, player);
    }

    public static ActionEvent getAttack(final Player player){
        return new ActionEvent(ActionType.BEFORE_ATTACK, player);
    }


    /**
     * @param player who made damage
     * @param victim has special format about who was damaged: "damage playerID heroID"
     * @param damage is understand
     * @return event
     */

    public static ActionEvent getBeforeDealDamage(final Player player, final Hero victim, final double damage){
        return new ActionEvent(ActionType.BEFORE_DEAL_DAMAGE, player, new Pair<>(victim, damage));
    }

    public static ActionEvent getAfterDealDamage(final Player player, final Hero victim, final double damage){
        return new ActionEvent(ActionType.AFTER_DEAL_DAMAGE, player, new Pair<>(victim, damage));
    }

    public static ActionEvent getBeforeUsedSkill(final Player player, String skillName){
        return new ActionEvent(ActionType.BEFORE_USED_SKILL, player, skillName);
    }

    public static ActionEvent getAfterUsedBonus(final Player player, final Bonus bonus){
        return new ActionEvent(ActionType.AFTER_USED_BONUS, player, bonus);
    }
}