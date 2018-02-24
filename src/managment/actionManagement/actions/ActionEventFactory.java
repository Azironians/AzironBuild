package managment.actionManagement.actions;

import com.google.inject.Singleton;
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
        return new ActionEvent(ActionType.TREATMENT, player);
    }

    public static ActionEvent getAttack(final Player player){
        return new ActionEvent(ActionType.ATTACK, player);
    }

    public static ActionEvent getDealDamage(final Player player){
        return new ActionEvent(ActionType.DEAL_DAMAGE, player);
    }

    public static ActionEvent getUsedSkill(final Player player, String skillName){
        return new ActionEvent(ActionType.USED_SKILL, player, skillName);
    }

    public static ActionEvent getUsedBonus(final Player player, String bonusName){
        return new ActionEvent(ActionType.USED_BONUS, player, bonusName);
    }
}