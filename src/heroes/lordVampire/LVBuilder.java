package heroes.lordVampire;

import bonus.bonuses.Bonus;
import heroes.abstractHero.builder.AHeroBuilder;
import heroes.abstractHero.hero.AHero;
import heroes.abstractHero.resourceSupplier.HeroResourceSupplier;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import managment.actionManagement.actions.ActionEventFactory;
import managment.battleManagement.BattleManager;
import managment.playerManagement.Player;
import managment.playerManagement.PlayerManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class LVBuilder implements AHeroBuilder {

    private final HeroResourceSupplier resourceSupplier = new LVResourceSupplier();

    @Override
    public final AHero buildHero(final List<Bonus> deck) {
        //Start position:
        final double START_ATTACK = 50.0;
        final double START_TREATMENT = 75.0;
        final double START_HIT_POINTS = 300.0;
        final double START_SUPPLY_HEALTH = 300.0;
        final double START_EXPERIENCE = 0.0;
        final int START_LEVEL = 1;

        final int SWAP_RELOAD = 5;

        final double SWAP_SKILL_COEFFICIENT = 1.0;
        final List<Double> SWAP_SKILL_COEFFICIENTS = Collections.singletonList(SWAP_SKILL_COEFFICIENT);
        final AHero.Skill SWAP_SKILL = new AHero.Skill(SWAP_RELOAD, SWAP_SKILL_COEFFICIENTS, new ArrayList<>()) {
            @Override
            public void use(BattleManager battleManager, PlayerManager playerManager) {
                final Player opponentPlayer = playerManager.getOpponentATeam().getCurrentPlayer();
                final Player currentPlayer = playerManager.getCurrentTeam().getCurrentPlayer();
                final AHero currentHero = currentPlayer.getHero();
                final AHero opponentHero = opponentPlayer.getHero();
                final int levelComparison = opponentHero.getLevel() - currentHero.getLevel();
                final double SKILL_COEFFICIENT = levelComparison > 0 ? (levelComparison + 1) * coefficients.get(0)
                        : coefficients.get(0);
                final double DAMAGE = currentHero.getAttack() * SKILL_COEFFICIENT;
                if (opponentHero.getDamage(DAMAGE)){
                    actionEvents.add(ActionEventFactory.getDealDamage(currentPlayer));
                }
            }

            @Override
            public void showAnimation() {

            }

            @Override
            public final void reload() {
                if (temp + 1 <= reload){
                    temp++;
                }
            }

            @Override
            public final void reset() {
                temp = 1;
            }
        };

        //Суперспособность 1:
        final int CANNIBALISM_RELOAD = 5;
        final int CANNIBALISM_REQUIRED_LEVEL = 1;

        final double CANNIBALISM_DAMAGE_SKILL_COEFFICIENT = 2.5;
        final double CANNIBALISM_HEALING_SKILL_COEFFICIENT = 2.5;
        final List<Double> CANNIBALISM_SKILL_COEFFICIENTS = Arrays.asList
                (CANNIBALISM_DAMAGE_SKILL_COEFFICIENT
                , CANNIBALISM_HEALING_SKILL_COEFFICIENT);

        final HeroResourceSupplier.GetSkill CANNIBALISM_RESOURCE = resourceSupplier.getSuperSkillResources().get(0);
        final AHero.Skill CANNIBALISM = new AHero.Skill("Cannibalism"
                , CANNIBALISM_RELOAD, CANNIBALISM_REQUIRED_LEVEL, CANNIBALISM_SKILL_COEFFICIENTS
                , CANNIBALISM_RESOURCE.getSprite()
                , CANNIBALISM_RESOURCE.getDescription()
                , new ArrayList<>()
//                , new Media("file:///D:/NoWayAlexMilkevich/videosForTutorial/menuVideo.mp4")
        ) {
            @Override
            public final void use(final BattleManager battleManager, final PlayerManager playerManager) {

                final double DAMAGE = getParent().getAttack() * coefficients.get(0);
                final double HEALING = getParent().getAttack() * coefficients.get(1);

                final Player currentPlayer = playerManager.getCurrentTeam().getCurrentPlayer();
                final Player opponentPlayer = playerManager.getOpponentATeam().getCurrentPlayer();
                final AHero currentHero = currentPlayer.getHero();
                final AHero opponentHero = opponentPlayer.getHero();
                if (opponentHero.getDamage(DAMAGE)) {
                    actionEvents.add(ActionEventFactory.getDealDamage(currentPlayer));
                }
                if (currentHero.getHealing(HEALING)){

                }
            }

            @Override
            public final void showAnimation() {

            }
        };

        // Суперспособность 2:
        final int NIGHT_BLADES_RELOAD = 8;
        final int NIGHT_BLADES_REQUIRED_LEVEL = 2;

        final double NIGHT_BLADES_SKILL_COEFFICIENT = 1.2;
        final List<Double> NIGHT_BLADES_SKILL_COEFFICIENTS = Collections.singletonList(NIGHT_BLADES_SKILL_COEFFICIENT);
        final HeroResourceSupplier.GetSkill NIGHT_BLADES_RESOURCE = resourceSupplier.getSuperSkillResources().get(1);
        final AHero.Skill NIGHT_BLADES = new AHero.Skill("NightBlades"
                , NIGHT_BLADES_RELOAD, NIGHT_BLADES_REQUIRED_LEVEL, NIGHT_BLADES_SKILL_COEFFICIENTS
                , NIGHT_BLADES_RESOURCE.getSprite()
                , NIGHT_BLADES_RESOURCE.getDescription()
                , new ArrayList<>()
        ) {
            @Override
            public final void use(final BattleManager battleManager, final PlayerManager playerManager) {
                final double ATTACK_VALUE = parent.getAttack() * coefficients.get(0);
                parent.setAttack(ATTACK_VALUE);
            }

            @Override
            public final void showAnimation() {

            }
        };

        //Суперспособность 3:
        final int REINCARNATION_RELOAD = 9;
        final int REINCARNATION_REQUIRED_LEVEL = 5;

        final double REINCARNATION_SKILL_COEFFICIENT = 1.0;
        final List<Double> REINCARNATION_SKILL_COEFFICIENTS = Collections.singletonList(REINCARNATION_SKILL_COEFFICIENT);
        final HeroResourceSupplier.GetSkill REINCARNATION_RESOURCE = resourceSupplier.getSuperSkillResources().get(2);
        final AHero.Skill REINCARNATION = new AHero.Skill("Reincarnation"
                , REINCARNATION_RELOAD, REINCARNATION_REQUIRED_LEVEL, REINCARNATION_SKILL_COEFFICIENTS
                , REINCARNATION_RESOURCE.getSprite()
                , REINCARNATION_RESOURCE.getDescription()
                , new ArrayList<>()
        ) {
            @Override
            public final void use(final BattleManager battleManager, final PlayerManager playerManager) {
                final double hitPoints = parent.getHitPoints();
                parent.setHitPoints(Math.abs(hitPoints) * coefficients.get(0));
            }

            @Override
            public final void showAnimation() {

            }
        };

        //Лист, в который ты потом положишь суперспособности:
        final List<AHero.Skill> SKILL_LIST = Arrays.asList(CANNIBALISM, NIGHT_BLADES, REINCARNATION);

        //Лист перехода по опыту:
        final double EXP_LEVEL_2 = 250.0;
        final double EXP_LEVEL_3 = 610.0;
        final double EXP_LEVEL_4 = 1114.0;
        final double EXP_LEVEL_5 = 1794.0;
        final double EXP_LEVEL_6 = 2730.0;
        final double EXP_LEVEL_7 = 3980.0;
        final double EXP_LEVEL_8 = 5619.0;
        final double EXP_LEVEL_9 = 7767.0;
        final double EXP_LEVEL_10 = 10562.0;

        List<Double> REQUIRED_EXPERIENCE_LIST = Arrays.asList(EXP_LEVEL_2, EXP_LEVEL_3, EXP_LEVEL_4, EXP_LEVEL_5
                , EXP_LEVEL_6, EXP_LEVEL_7, EXP_LEVEL_8, EXP_LEVEL_9, EXP_LEVEL_10);

        //Лист переходов со здоровьем:
        final double HP_LEVEL_2 = 60.0;
        final double HP_LEVEL_3 = 70.0;
        final double HP_LEVEL_4 = 90.0;
        final double HP_LEVEL_5 = 105.0;
        final double HP_LEVEL_6 = 120.0;
        final double HP_LEVEL_7 = 150.0;
        final double HP_LEVEL_8 = 180.0;
        final double HP_LEVEL_9 = 225.0;
        final double HP_LEVEL_10 = 250.0;

        final List<Double> SUPPLY_HEATH_LIST = Arrays.asList(HP_LEVEL_2, HP_LEVEL_3, HP_LEVEL_4, HP_LEVEL_5, HP_LEVEL_6
                , HP_LEVEL_7, HP_LEVEL_8, HP_LEVEL_9, HP_LEVEL_10);

        //Лист звуков с атакой:
        final List<Media> ATTACK_MEDIA_LIST = Arrays.asList(
//                new Media("src\\Sounds\\SoundLordVampire\\LVAttack-1.wav"),
//                new Media("src\\Sounds\\SoundLordVampire\\LVAttack-2.wav"),
//                new Media("src\\Sounds\\SoundLordVampire\\LVAttack-3.wav"),
//                new Media("src\\Sounds\\SoundLordVampire\\LVAttack-4.wav")
        );

        //Лист звуков с лечением:
        final List<Media> TREATMENT_MEDIA_LIST = Arrays.asList(
//                new Media("src\\Sounds\\SoundLordVampire\\LVTreatment-1.wav"),
//                new Media("src\\Sounds\\SoundLordVampire\\LVTreatment-2.wav"),
//                new Media("src\\Sounds\\SoundLordVampire\\LVTreatment-3.wav"),
//                new Media("src\\Sounds\\SoundLordVampire\\LVTreatment-4.wav")
        );

        //Лист урона:
        final double DAMAGE_LEVEL_2 = 10.0;
        final double DAMAGE_LEVEL_3 = 12.0;
        final double DAMAGE_LEVEL_4 = 13.0;
        final double DAMAGE_LEVEL_5 = 19.0;
        final double DAMAGE_LEVEL_6 = 21.0;
        final double DAMAGE_LEVEL_7 = 24.0;
        final double DAMAGE_LEVEL_8 = 30.0;
        final double DAMAGE_LEVEL_9 = 36.0;
        final double DAMAGE_LEVEL_10 = 41.0;

        final List<Double> DAMAGE_LIST = Arrays.asList(DAMAGE_LEVEL_2, DAMAGE_LEVEL_3, DAMAGE_LEVEL_4, DAMAGE_LEVEL_5
                , DAMAGE_LEVEL_6, DAMAGE_LEVEL_7, DAMAGE_LEVEL_8, DAMAGE_LEVEL_9, DAMAGE_LEVEL_10);

        //Лист Лечения:
        final double TREAT_LEVEL_2 = 15.0;
        final double TREAT_LEVEL_3 = 18.0;
        final double TREAT_LEVEL_4 = 22.0;
        final double TREAT_LEVEL_5 = 26.0;
        final double TREAT_LEVEL_6 = 31.0;
        final double TREAT_LEVEL_7 = 37.0;
        final double TREAT_LEVEL_8 = 45.0;
        final double TREAT_LEVEL_9 = 53.0;
        final double TREAT_LEVEL_10 = 65.0;

        final List<Double> TREATMENT_LIST = Arrays.asList(TREAT_LEVEL_2, TREAT_LEVEL_3, TREAT_LEVEL_4, TREAT_LEVEL_5
                , TREAT_LEVEL_6, TREAT_LEVEL_7, TREAT_LEVEL_8, TREAT_LEVEL_9, TREAT_LEVEL_10);

        //Картинка героя:
        final ImageView FACE = resourceSupplier.getFaceImageInstance();

        //Презентация:
        final List<Media> PRESENTATION_MEDIA_LIST = Arrays.asList(
//                new Media("src\\Sounds\\SoundLordVampire\\LVGreetings-1.wav"),
//                new Media("src\\Sounds\\SoundLordVampire\\LVGreetings-2.wav")
        );

        final AHero.Presentation PRESENTATION = new AHero.Presentation(
                new ImageView(new Image("file:src\\resources\\heroes\\lordVampire\\presentation\\spotlight.jpg")),
                PRESENTATION_MEDIA_LIST, new Pane());

        //Возврат собранного героя:
        return new LV("LordVampire", START_ATTACK, START_TREATMENT, START_HIT_POINTS, START_SUPPLY_HEALTH, START_EXPERIENCE
                , START_LEVEL, REQUIRED_EXPERIENCE_LIST, DAMAGE_LIST, TREATMENT_LIST, SUPPLY_HEATH_LIST, SKILL_LIST, SWAP_SKILL
                , FACE, ATTACK_MEDIA_LIST, TREATMENT_MEDIA_LIST, PRESENTATION, deck);
    }
}