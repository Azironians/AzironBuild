package bonus.bonuses.factory;

import bonus.bonuses.Bonus;
import bonus.generalBonuses.bonuses.attack.AAttack;
import bonus.generalBonuses.bonuses.attack.AAxeUp;
import bonus.generalBonuses.bonuses.attack.ABerserk;
import bonus.generalBonuses.bonuses.attack.doubleInHead.ADoubleInHead;
import bonus.generalBonuses.bonuses.experience.XAnticipation;
import bonus.generalBonuses.bonuses.experience.XFeedBack;
import bonus.generalBonuses.bonuses.experience.XNerf;
import bonus.generalBonuses.bonuses.experience.XStepByStep;
import bonus.generalBonuses.bonuses.health.HAdaptation;
import bonus.generalBonuses.bonuses.health.HElixirOfLife;
import bonus.generalBonuses.bonuses.health.HExorcism;
import bonus.generalBonuses.bonuses.health.HStrengthenTheArmor;
import bonus.generalBonuses.bonuses.special.SCounterSpell;
import bonus.generalBonuses.bonuses.special.SGiveFire;
import bonus.generalBonuses.bonuses.special.SMagicTotem;
import bonus.generalBonuses.bonuses.special.SWayOfWizard;
import bonus.generalBonuses.supplier.BonusGeneralResourceSupplier;

import java.util.HashMap;
import java.util.Map;

public final class BonusFactory {

    private final Map<Integer, Bonus> mapOfBonus = new HashMap<>();

    private final BonusGeneralResourceSupplier resourceSupplier = new BonusGeneralResourceSupplier();

    public BonusFactory() {
        mapOfBonus.putAll(new GeneralBonusFactory().generalBonusMap);
    }

    private final class GeneralBonusFactory {

        private final Map<Integer, Bonus> generalBonusMap = new HashMap<>(16);

        private GeneralBonusFactory(){
            generalBonusMap.put(attack.getId(), attack);
            generalBonusMap.put(axeUp.getId(), axeUp);
            generalBonusMap.put(berserk.getId(), berserk);
            generalBonusMap.put(doubleInHead.getId(), doubleInHead);
            generalBonusMap.put(adaptation.getId(), adaptation);
            generalBonusMap.put(elixirOfLife.getId(), elixirOfLife);
            generalBonusMap.put(exorcism.getId(), exorcism);
            generalBonusMap.put(strengthenTheArmor.getId(), strengthenTheArmor);
            generalBonusMap.put(counterSpell.getId(), counterSpell);
            generalBonusMap.put(giveFire.getId(), giveFire);
            generalBonusMap.put(magicTotem.getId(), magicTotem);
            generalBonusMap.put(wayOfWizard.getId(), wayOfWizard);
            generalBonusMap.put(anticipation.getId(), anticipation);
            generalBonusMap.put(feedBack.getId(), feedBack);
            generalBonusMap.put(nerf.getId(), nerf);
            generalBonusMap.put(stepByStep.getId(), stepByStep);
        }

        private final AAttack attack = new AAttack("AAttack", 1
                , resourceSupplier.getAttackSpriteInstance());
        private final AAxeUp axeUp = new AAxeUp("AAxeUp", 2
                , resourceSupplier.getAxeUpSpriteInstance());
        private final ABerserk berserk = new ABerserk("ABerserk", 3
                , resourceSupplier.getBerserkSpriteInstance());
        private final ADoubleInHead doubleInHead = new ADoubleInHead("ADoubleInHead", 4
                , resourceSupplier.getDoubleInHeadSpriteInstance());
        private final HAdaptation adaptation = new HAdaptation("HAdaptation", 5
                , resourceSupplier.getAdaptationSpriteInstance());
        private final HElixirOfLife elixirOfLife = new HElixirOfLife("HElixirOfLife", 6
                , resourceSupplier.getElixirOfLifeSpriteInstance());
        private final HExorcism exorcism = new HExorcism("HExorcism", 7
                , resourceSupplier.getExorcismSpriteInstance());
        private final HStrengthenTheArmor strengthenTheArmor = new HStrengthenTheArmor("HStrengthenTheArmor"
                , 8, resourceSupplier.getStrengthenTheArmor());
        private final SCounterSpell counterSpell = new SCounterSpell("SCounterSpell", 9
                , resourceSupplier.getCounterSpellSpriteInstance());
        private final SGiveFire giveFire = new SGiveFire("SGiveFire", 10
                , resourceSupplier.getGiveFireSpriteInstance());
        private final SMagicTotem magicTotem = new SMagicTotem("SMagicTotem", 11
                , resourceSupplier.getMagicTotemSpriteInstance());
        private final SWayOfWizard wayOfWizard = new SWayOfWizard("SWayOfWizard", 12
                , resourceSupplier.getWayOfWizard());
        private final XAnticipation anticipation = new XAnticipation("XAnticipation", 13
                , resourceSupplier.getAnticipationSpriteInstance());
        private final XFeedBack feedBack = new XFeedBack("XFeedBack", 14
                , resourceSupplier.getFeedBackSpriteInstance());
        private final XNerf nerf = new XNerf("XNerf", 15
                , resourceSupplier.getNerfSpriteInstance());
        private final XStepByStep stepByStep = new XStepByStep("XStepByStep", 16
                , resourceSupplier.getStepByStep());
    }

    public final Map<Integer, Bonus> getMapOfBonus() {
        return mapOfBonus;
    }
}
