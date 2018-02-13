package heroes.devourer.builder;

import bonus.bonuses.Bonus;
import bonus.bonuses.HandlerBonus;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import heroes.abstractHero.builder.AHeroBuilder;
import heroes.abstractHero.hero.AHero;
import heroes.abstractHero.presentation.Presentation;
import heroes.abstractHero.resourceSupplier.HeroResourceSupplier;
import heroes.abstractHero.skills.Skill;
import heroes.abstractHero.skills.SkillFactory;
import heroes.devourer.annotation.DevourerSource;
import heroes.devourer.recourceSupplier.DevourerResourceSupplier;
import heroes.devourer.hero.Devourer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import managment.actionManagement.actions.ActionEvent;
import managment.actionManagement.actions.ActionEventFactory;
import managment.actionManagement.actions.ActionType;
import managment.battleManagement.BattleManager;
import managment.playerManagement.Player;
import managment.playerManagement.PlayerManager;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class DevourerBuilder implements AHeroBuilder {

    private static final Logger log = LoggerFactory.getLogger(DevourerBuilder.class);

    //Start characteristics:
    @Inject
    @Named("DEVOURER_START_ATTACK")
    private static double START_ATTACK;

    @Inject
    @Named("DEVOURER_START_TREATMENT")
    private static double START_TREATMENT;

    @Inject
    @Named("DEVOURER_START_HIT_POINTS")
    private static double START_HIT_POINTS;

    @Inject
    @Named("DEVOURER_START_SUPPLY_HEALTH")
    private static double START_SUPPLY_HEALTH;

    @Inject
    @Named("DEVOURER_START_EXPERIENCE")
    private static double START_EXPERIENCE;

    @Inject
    @Named("DEVOURER_START_LEVEL")
    private static int START_LEVEL;

    @Inject
    @DevourerSource
    private SkillFactory skillFactory;

    @Inject
    @DevourerSource
    private HeroResourceSupplier resourceSupplier;



    @NotNull
    @Override
    public final AHero buildHero(final List<Bonus> inputDeck) {

        //Swap skill:
        final int SWAP_RELOAD = 5;

        final double SWAP_SKILL_COEFFICIENT = 1.0;
        final List<Double> SWAP_SKILL_COEFFICIENTS = Collections.singletonList(SWAP_SKILL_COEFFICIENT);
        final Skill SWAP_SKILL = new AHero.Skill(SWAP_RELOAD, SWAP_SKILL_COEFFICIENTS, new ArrayList<>()) {

            @Override
            public final void use(final BattleManager battleManager, final PlayerManager playerManager) {
                final Player opponentPlayer = playerManager.getOpponentATeam().getCurrentPlayer();
                final AHero currentHero = playerManager.getCurrentTeam().getCurrentPlayer().getHero();
                final AHero opponentHero = opponentPlayer.getHero();
                final int levelComparison = opponentHero.getLevel() - currentHero.getLevel();
                final double SKILL_COEFFICIENT = levelComparison > 0 ? (levelComparison + 1) * coefficients.get(0)
                        : coefficients.get(0);
                final double DAMAGE = currentHero.getAttack() * SKILL_COEFFICIENT;
                opponentHero.removeExperience(DAMAGE);
            }

            @Override
            public final void showAnimation() {

            }
        };

        //Суперспособность 1:
        final int FLAME_SNAKES_RELOAD = 5;
        final int FLAME_SNAKES_REQUIRED_LEVEL = 1;

        final double FLAME_SNAKES_SKILL_COEFFICIENT = 5;
        final List<Double> FLAME_SNAKES_SKILL_COEFFICIENTS = Collections.singletonList(FLAME_SNAKES_SKILL_COEFFICIENT);
        final HeroResourceSupplier.GetSkills FLAME_SNAKES_RESOURCE = resourceSupplier.getSkillInstances().get(0);
        final AHero.Skill FLAME_SNAKES = new AHero.Skill("FlameSnakes"
                , FLAME_SNAKES_RELOAD, FLAME_SNAKES_REQUIRED_LEVEL, FLAME_SNAKES_SKILL_COEFFICIENTS
                , FLAME_SNAKES_RESOURCE.getSprite()
                , FLAME_SNAKES_RESOURCE.getDescription()
                , new ArrayList<>()) {
            @Override
            public final void use(final BattleManager battleManager, final PlayerManager playerManager) {
                final double DAMAGE = getParent().getAttack() * coefficients.get(0);
                log.info("FLAME_SNAKES_DAMAGE : " + DAMAGE);
                final Player currentPlayer = playerManager.getCurrentTeam().getCurrentPlayer();
                final Player opponentPlayer = playerManager.getOpponentATeam().getCurrentPlayer();
                final AHero opponentHero = opponentPlayer.getHero();
                if (opponentHero.getDamage(DAMAGE)) {
                    actionEvents.add(ActionEventFactory.getDealDamage(currentPlayer));
                }
            }

            @Override
            public final void showAnimation() {

            }
        };

        //Суперспособность 2:
        final int REGENERATION_RELOAD = 7;
        final int REGENERATION_REQUIRED_LEVEL = 3;

        final double REGENERATION_SKILL_COEFFICIENT = 1.4;
        final List<Double> REGENERATION_SKILL_COEFFICIENTS = Collections.singletonList(REGENERATION_SKILL_COEFFICIENT);
        final HeroResourceSupplier.GetSkills REGENERATION_RESOURCE = resourceSupplier.getSkillInstances().get(1);
        final AHero.Skill REGENERATION = new AHero.Skill("Regeneration"
                , REGENERATION_RELOAD, REGENERATION_REQUIRED_LEVEL, REGENERATION_SKILL_COEFFICIENTS
                , REGENERATION_RESOURCE.getSprite()
                , REGENERATION_RESOURCE.getDescription()
                , new ArrayList<>()) {

            private void getEffect(final Player currentPlayer, final double coefficient){
                final double HEALING = getParent().getTreatment() * coefficient;
                final AHero currentHero = currentPlayer.getHero();
                if (currentHero.getHealing(HEALING)){
                    actionManager.getBonusEventEngine().handle();
                }
            }

            @Override
            public final void use(final BattleManager battleManager, final PlayerManager playerManager) {
                final Player currentPlayer = playerManager.getCurrentTeam().getCurrentPlayer();
                getEffect(currentPlayer, coefficients.get(0));
                actionManager.getBonusEventEngine().addHandler(new HandlerBonus.GetAHandler() {

                    private Player player;

                    private double coefficient;

                    private boolean isWorking = true;

                    @Override
                    public void setup() {
                        this.player = currentPlayer;
                        this.coefficient = coefficients.get(0);
                        log.info("Setup was successful");
                    }

                    @Override
                    public void handle(final ActionEvent actionEvent) {
                        if (actionEvent.getActionType() == ActionType.START_TURN && actionEvent.getPlayer() == player){
                            getEffect(player, coefficient);
                            setAble(false);
                            log.info("Second healing");
                        }
                    }

                    @Override
                    public String getName() {
                        return "Regeneration skill";
                    }

                    @Override
                    public Player getPlayer() {
                        return player;
                    }

                    @Override
                    public boolean isWorking() {
                        return isWorking;
                    }

                    @Override
                    public void setAble(boolean able) {
                        this.isWorking = false;
                    }
                });
                log.info("skill added");
            }

            @Override
            public final void showAnimation() {

            }
        };

        //Суперспособность 3:
        final int CONSUMING_RELOAD = 4;
        final int CONSUMING_REQUIRED_LEVEL = 6;

        final double CONSUMING_SKILL_COEFFICIENT = 2.0;
        final List<Double> CONSUMING_SKILL_COEFFICIENTS = Collections.singletonList(CONSUMING_SKILL_COEFFICIENT);
        final HeroResourceSupplier.GetSkills CONSUMING_RESOURCE = resourceSupplier.getSkillInstances().get(2);
        final AHero.Skill CONSUMING = new AHero.Skill("Consuming"
                , CONSUMING_RELOAD, CONSUMING_REQUIRED_LEVEL, CONSUMING_SKILL_COEFFICIENTS
                , CONSUMING_RESOURCE.getSprite()
                , CONSUMING_RESOURCE.getDescription()
                , new ArrayList<>()) {
            @Override
            public final void use(final BattleManager battleManager, final PlayerManager playerManager) {
                final Player currentPlayer = playerManager.getCurrentTeam().getCurrentPlayer();
                final Player opponentPlayer = playerManager.getOpponentATeam().getCurrentPlayer();
                final AHero opponentHero = opponentPlayer.getHero();

                final double DAMAGE = opponentHero.getHitPoints() / coefficients.get(0);
                if (opponentHero.getDamage(DAMAGE)) {
                    actionEvents.add(ActionEventFactory.getDealDamage(currentPlayer));
                }
            }

            @Override
            public final void showAnimation() {

            }
        };

        //Лист, в который ты потом положишь суперспособности:
        final List<AHero.Skill> SKILL_LIST = Arrays.asList(FLAME_SNAKES, REGENERATION, CONSUMING);

        //Лист перехода по опыту:
        final double EXP_LEVEL_2 = 200.0;
        final double EXP_LEVEL_3 = 488.0;
        final double EXP_LEVEL_4 = 894.0;
        final double EXP_LEVEL_5 = 1446.0;
        final double EXP_LEVEL_6 = 2193.0;
        final double EXP_LEVEL_7 = 3193.0;
        final double EXP_LEVEL_8 = 4513.0;
        final double EXP_LEVEL_9 = 6229.0;
        final double EXP_LEVEL_10 = 8465.0;

        List<Double> REQUIRED_EXPERIENCE_LIST = Arrays.asList(EXP_LEVEL_2, EXP_LEVEL_3, EXP_LEVEL_4, EXP_LEVEL_5
                , EXP_LEVEL_6, EXP_LEVEL_7, EXP_LEVEL_8, EXP_LEVEL_9, EXP_LEVEL_10);

        final double HP_LEVEL_2 = 80.0;
        final double HP_LEVEL_3 = 95.0;
        final double HP_LEVEL_4 = 115.0;
        final double HP_LEVEL_5 = 140.0;
        final double HP_LEVEL_6 = 165.0;
        final double HP_LEVEL_7 = 200.0;
        final double HP_LEVEL_8 = 235.0;
        final double HP_LEVEL_9 = 290.0;
        final double HP_LEVEL_10 = 345.0;

        //Лист переходов со здоровьем:
        final List<Double> SUPPLY_HEATH_LIST = Arrays.asList(HP_LEVEL_2, HP_LEVEL_3, HP_LEVEL_4, HP_LEVEL_5, HP_LEVEL_6
                , HP_LEVEL_7, HP_LEVEL_8, HP_LEVEL_9, HP_LEVEL_10);

        //Лист звуков с атакой:
        final List<Media> ATTACK_MEDIA_LIST = Arrays.asList(
//                new Media("src\\Sounds\\SoundDevourer\\DevAttack-1.wav"),
//                new Media("src\\Sounds\\SoundDevourer\\DevAttack-2.wav"),
//                new Media("src\\Sounds\\SoundDevourer\\DevAttack-3.wav"),
//                new Media("src\\Sounds\\SoundDevourer\\DevAttack-4.wav")
        );

        //Лист звуков с лечением:
        final List<Media> TREATMENT_MEDIA_LIST = Arrays.asList(
//                new Media("src\\Sounds\\SoundDevourer\\DevTreatment-1.wav"),
//                new Media("src\\Sounds\\SoundDevourer\\DevTreatment-2.wav"),
//                new Media("src\\Sounds\\SoundDevourer\\DevTreatment-3.wav"),
//                new Media("src\\Sounds\\SoundDevourer\\DevTreatment-4.wav")
        );

        //Лист урона:
        final double DAMAGE_LEVEL_2 = 8.0;
        final double DAMAGE_LEVEL_3 = 10.0;
        final double DAMAGE_LEVEL_4 = 11.0;
        final double DAMAGE_LEVEL_5 = 14.0;
        final double DAMAGE_LEVEL_6 = 17.0;
        final double DAMAGE_LEVEL_7 = 20.0;
        final double DAMAGE_LEVEL_8 = 23.0;
        final double DAMAGE_LEVEL_9 = 29.0;
        final double DAMAGE_LEVEL_10 = 35.0;

        final List<Double> DAMAGE_LIST = Arrays.asList(DAMAGE_LEVEL_2, DAMAGE_LEVEL_3, DAMAGE_LEVEL_4, DAMAGE_LEVEL_5
                , DAMAGE_LEVEL_6, DAMAGE_LEVEL_7, DAMAGE_LEVEL_8, DAMAGE_LEVEL_9, DAMAGE_LEVEL_10);

        //Лист Лечения:
        final double TREAT_LEVEL_2 = 20.0;
        final double TREAT_LEVEL_3 = 24.0;
        final double TREAT_LEVEL_4 = 29.0;
        final double TREAT_LEVEL_5 = 34.0;
        final double TREAT_LEVEL_6 = 42.0;
        final double TREAT_LEVEL_7 = 50.0;
        final double TREAT_LEVEL_8 = 59.0;
        final double TREAT_LEVEL_9 = 72.0;
        final double TREAT_LEVEL_10 = 86.0;

        final List<Double> TREATMENT_LIST = Arrays.asList(TREAT_LEVEL_2, TREAT_LEVEL_3, TREAT_LEVEL_4, TREAT_LEVEL_5
                , TREAT_LEVEL_6, TREAT_LEVEL_7, TREAT_LEVEL_8, TREAT_LEVEL_9, TREAT_LEVEL_10);

        //Картинка героя:
        final ImageView FACE = resourceSupplier.getFaceImageInstance();

        //Презентация:
        final List<Media> PRESENTATION_MEDIA_LIST = Arrays.asList(
//                new Media("src\\Sounds\\SoundDevourer\\DevGreetings-1.wav"),
//                new Media("src\\Sounds\\SoundDevourer\\DevGreetings-2.wav")
        );

        final AHero.Presentation PRESENTATION = new AHero.Presentation(
                new ImageView(new Image("file:src\\resources\\heroes\\devourer\\presentation\\spotlight.png")),
                PRESENTATION_MEDIA_LIST, new Pane());

        //Возврат собранного героя:
        return new Devourer("Devourer", START_ATTACK, START_TREATMENT, START_HIT_POINTS, START_SUPPLY_HEALTH, START_EXPERIENCE
                , START_LEVEL, REQUIRED_EXPERIENCE_LIST, DAMAGE_LIST, TREATMENT_LIST, SUPPLY_HEATH_LIST, SKILL_LIST, SWAP_SKILL
                , FACE, ATTACK_MEDIA_LIST, TREATMENT_MEDIA_LIST, PRESENTATION, inputDeck);
    }

    @Override
    public Presentation buildPresentation() {
        return null;
    }
}
