package managment.actionManagement.actionProccessors;

import bonus.bonuses.Bonus;
import managment.actionManagement.ActionManager;
import managment.processors.Processor;

//Not final!
public class BonusProcessor implements Processor {

    private final ActionManager actionManager;

    private Bonus bonus;

    public BonusProcessor(final ActionManager actionManager){
        this.actionManager = actionManager;
    }

    @Override
    public void process() {
        bonus.getActionEvents().clear();
        bonus.use();
        actionManager.refreshScreen();
        actionManager.getEventEngine().handle();
        actionManager.refreshScreen();
    }

    public void setBonus(final Bonus bonus){
        this.bonus = bonus;
    }
}