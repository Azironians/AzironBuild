package bonus.generalBonuses.supplier;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public final class BonusGeneralResourceSupplier {

    public final ImageView getAttackSpriteInstance(){
        return bonusSpriteFormat("AAttack.png");
    }

    public final ImageView getAxeUpSpriteInstance(){
        return bonusSpriteFormat("AAxeUp.png");
    }

    public final ImageView getBerserkSpriteInstance(){
        return bonusSpriteFormat("ABerserk.png");
    }

    public final ImageView getDoubleInHeadSpriteInstance(){
        return bonusSpriteFormat("ADoubleInHead.png");
    }

    public final ImageView getAdaptationSpriteInstance(){
        return bonusSpriteFormat("HAdaptation.png");
    }

    public final ImageView getElixirOfLifeSpriteInstance(){
        return bonusSpriteFormat("HElixirOfLife.png");
    }

    public final ImageView getExorcismSpriteInstance(){
        return bonusSpriteFormat("HExorcism.png");
    }

    public final ImageView getStrengthenTheArmor(){
        return bonusSpriteFormat("HStrengthenTheArmor.png");
    }

    public final ImageView getCounterSpellSpriteInstance(){
        return bonusSpriteFormat("SCounterSpell.png");
    }

    public final ImageView getGiveFireSpriteInstance(){
        return bonusSpriteFormat("SGiveFire.png");
    }

    public final ImageView getMagicTotemSpriteInstance(){
        return bonusSpriteFormat("SMagicTotem.png");
    }

    public final ImageView getWayOfWizard(){
        return bonusSpriteFormat("SWayOfWizard.png");
    }

    public final ImageView getAnticipationSpriteInstance(){
        return bonusSpriteFormat("XAnticipation.png");
    }

    public final ImageView getFeedBackSpriteInstance(){
        return bonusSpriteFormat("XFeedBack.png");
    }

    public final ImageView getNerfSpriteInstance(){
        return bonusSpriteFormat("XNerf.png");
    }

    public final ImageView getStepByStep(){
        return bonusSpriteFormat("XStepByStep.png");
    }

    private ImageView bonusSpriteFormat(final String path){
        final String bonusPath = "file:src\\resources\\bonuses\\general\\";
        final ImageView imageView = new ImageView(new Image(bonusPath + path));
        return imageView;
    }
}
