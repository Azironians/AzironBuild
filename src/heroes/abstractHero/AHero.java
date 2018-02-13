package heroes.abstractHero;

import bonus.bonuses.Bonus;
import javafx.animation.FadeTransition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.util.Duration;
import managment.actionManagement.actions.ActionEvent;
import managment.actionManagement.ActionManager;
import managment.battleManagement.BattleManager;
import managment.playerManagement.PlayerManager;
import org.jetbrains.annotations.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class AHero {
    private static final Logger log = LoggerFactory.getLogger(AHero.class);

    private final double START_EXPERIENCE = 0.0;

    //Name:
    private final String name;

    //Inner:
    private Double attack; //Атака
    private Double treatment; //Лечение
    private Double hitPoints; //Здоровье
    private Double supplyHealth; //Запас здоровья
    private Double currentExperience = START_EXPERIENCE; //Текущий опыт
    private int level; //Уровень героя
    private final List<Double> listOfRequiredExperience;
    private final List<Double> listOfSupplyHealth;
    private final List<Double> levelAttackList;
    private final List<Double> levelTreatmentList;
    private final List<Skill> collectionOfSkills; //Коллекция суперспособностей
    private Skill swapSkill; //Способность при выходе на поле боя
    private List<Bonus> bonusCollection;

    //Outer:
    private final ImageView face; //Картинка героя
    private final List<Media> listOfAttackVoices;
    private final List<Media> listOfTreatmentVoices;
    private final Presentation presentation;

    //Presentation clazz:
    public final static class Presentation {
        private final int WIDTH = 1280;
        private final int HEIGHT = 720;
        private final int START_OPACITY = 0;
        private final int ANIMATION_TIME = 2;

        private final ImageView backGround;
        private final List<Media> listOfPresentationMedia;
        private final Pane pane;

        private String deckName;
        private int deckPriority;

        public Presentation(final ImageView backGround, final List<Media> listOfPresentationMedia, final Pane pane) {
            backGround.setFitWidth(WIDTH);
            backGround.setFitHeight(HEIGHT);
            pane.getChildren().add(backGround);
            pane.setVisible(false);
            pane.setOpacity(START_OPACITY);
            this.backGround = backGround;
            this.listOfPresentationMedia = listOfPresentationMedia;
            this.pane = pane;
        }

        public final void showPresentation() {
            pane.setVisible(true);
            final FadeTransition fadeTransition = new FadeTransition(Duration.seconds(ANIMATION_TIME), pane);
            fadeTransition.setToValue(1);
            fadeTransition.play();
        }

        public final void hidePresentation() {
            final FadeTransition fadeTransition = new FadeTransition(Duration.seconds(ANIMATION_TIME), pane);
            fadeTransition.setToValue(0);
            fadeTransition.setOnFinished(end -> pane.setVisible(false));
            fadeTransition.play();
        }

        @Contract(pure = true)
        public final ImageView getBackGround() {
            return backGround;
        }

        @Contract(pure = true)
        public final List<Media> getPresentationMediaList() {
            return listOfPresentationMedia;
        }


        @Contract(pure = true)
        public final Pane getPane() {
            return pane;
        }

        public String getDeckName() {
            return deckName;
        }

        public void setDeckName(String deckName) {
            this.deckName = deckName;
        }

        public int getDeckPriority() {
            return deckPriority;
        }

        public void setDeckPriority(int deckPriority) {
            this.deckPriority = deckPriority;
        }


        public void setDeckInfo(final String deckName, final int deckPriority){
            this.deckName = deckName;
            this.deckPriority = deckPriority;
        }
    }

    //Skill clazz:
    public abstract static class Skill {

        private final String name;

        protected int temp = 1;
        protected int reload;
        private int requiredLevel;

        protected List<Double> coefficients;

        private final int START_OPACITY = 0;

        private ImageView pictureOfDescription;
        private ImageView sprite;
        private Media animationSound;
        private final List<Media> listOfVoices;

        //Parent:
        protected AHero parent;

        //ActionManager:
        protected ActionManager actionManager;

        protected Skill(final String name, final int reload, final int requiredLevel, final List<Double> coefficients
                , final ImageView sprite, final ImageView pictureOfDescription, final List<Media> listOfVoices) {
            this.name = name;
            this.reload = reload;
            this.requiredLevel = requiredLevel;
            this.coefficients = coefficients;

            sprite.setOnMouseEntered(event -> showDescription());
            sprite.setOnMouseExited(event -> hideDescription());
            sprite.setOnMouseClicked(event -> sendRequest());
            sprite.setVisible(false);
            this.pictureOfDescription = pictureOfDescription;
            this.sprite = sprite;
            this.listOfVoices = listOfVoices;
        }

        //Swap skill:
        protected Skill(final int reload, final List<Double> coefficients, final List<Media> voiceList) {
            this.name = "swap";
            this.reload = reload;
            this.requiredLevel = 1;
            this.coefficients = coefficients;

            this.listOfVoices = voiceList;
        }

        private void sendRequest() {
            if (parent != null) {
                log.info(" skill request");
                actionManager.setSkillRequest(parent, this);

            }
        }

        public abstract void use(final BattleManager battleManager, final PlayerManager playerManager);

        protected final List<ActionEvent> actionEvents = new ArrayList<>();

        public final List<ActionEvent> getActionEvents() {
            return actionEvents;
        }

        public final boolean isReady() {
            return temp >= reload;
        }

        public void reload() {
            if (parent != null){
                if (parent.level >= requiredLevel){
                    temp++;
                } else {
                    temp = 1;
                }
            }
        }

        public void reset() {
            temp = temp % reload;
            if (sprite != null) {
                sprite.setVisible(false);
            }
        }

        final void showDescription() {
            log.info("show description");
            final FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), pictureOfDescription);
            fadeTransition.setToValue(1);
            fadeTransition.play();
        }

        final void hideDescription() {
            log.info("hide description");
            final FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), pictureOfDescription);
            fadeTransition.setToValue(0);
            fadeTransition.play();
        }

        public final void install(final Pane parentPane, final AHero parent
                , final double x, final double y, final boolean invert) {
            //the skill must match the parent!
            this.parent = parent;
            //init description:
            pictureOfDescription.setLayoutX(x);
            pictureOfDescription.setLayoutY(-127);
            pictureOfDescription.setOpacity(START_OPACITY);
            parentPane.getChildren().add(pictureOfDescription);
            //init sprite:
            final int inversion = invert ? -1 : 1;
            sprite.setLayoutX(x);
            sprite.setLayoutY(y);
            sprite.setScaleX(inversion);
            parentPane.getChildren().add(sprite);
        }

        public final List<Double> getCoefficients() {
            return coefficients;
        }

        public ImageView getSprite() {
            return sprite;
        }

        public abstract void showAnimation();

        private boolean skillAccess = true;

        public String getName() {
            return name;
        }

        public void setCoefficients(List<Double> coefficients) {
            this.coefficients = coefficients;
        }

        @Contract(pure = true)
        public final List<Media> getListOfVoices() {
            return listOfVoices;
        }

        @Contract(pure = true)
        public final Media getAnimationSound() {
            return animationSound;
        }

        public boolean isSkillAccess() {
            return skillAccess;
        }

        public void setSkillAccess(boolean skillAccess) {
            this.skillAccess = skillAccess;
        }

        public int getTemp() {
            return temp;
        }

        public int getReload() {
            return reload;
        }

        public void setReload(int reload) {
            this.reload = reload;
        }

        public AHero getParent() {
            return parent;
        }

        public void setActionManager(final ActionManager actionManager) {
            this.actionManager = actionManager;
        }

        public final int getRequiredLevel() {
            return requiredLevel;
        }

        public final void setRequiredLevel(final int requiredLevel) {
            this.requiredLevel = requiredLevel;
        }
    }

    public final void reloadSkills() {
        collectionOfSkills.forEach(AHero.Skill::reload);
    }

    //AHero constructor:
    public AHero(final String name, final Double attack, final Double treatment, final Double hitPoints, final Double supplyHealth
            , final Double currentExperience, final int level, final List<Double> listOfRequiredExperience
            , final List<Double> levelAttackList, final List<Double> levelTreatmentList, final List<Double> listOfSupplyHealth
            , final List<Skill> collectionOfSkills, final Skill swapSkill
                 //Outer:
            , final ImageView face, final List<Media> listOfAttackVoices, final List<Media> listOfTreatmentVoices
            , final Presentation presentation, final List<Bonus> bonusCollection) {

        this.name = name;
        this.attack = attack;
        this.treatment = treatment;
        this.hitPoints = hitPoints;
        this.supplyHealth = supplyHealth;
        this.currentExperience = currentExperience;
        this.level = level;
        this.levelAttackList = levelAttackList;
        this.levelTreatmentList = levelTreatmentList;
        this.listOfRequiredExperience = listOfRequiredExperience;
        this.listOfSupplyHealth = listOfSupplyHealth;
        this.collectionOfSkills = collectionOfSkills;
        this.swapSkill = swapSkill;

        this.face = face;
        this.listOfAttackVoices = listOfAttackVoices;
        this.listOfTreatmentVoices = listOfTreatmentVoices;
        this.presentation = presentation;
        this.bonusCollection = bonusCollection;
    }

    //ActionAccess:
    private boolean healingAccess = true;

    private boolean treatmentAccess = true;

    private boolean attackAccess = true;

    private boolean damageAccess = true;

    //ActionEvents:

    public final boolean getHealing(final double healing) {
        if (hitPoints < supplyHealth) {
            if (hitPoints + healing > supplyHealth) {
                hitPoints = supplyHealth;
            } else {
                hitPoints += healing;
            }
            log.info(hitPoints.toString());
            return true;
        }
        return false;
    }

    private double armor = 0;

    public final boolean getDamage(final double damage) {
        if (damageAccess && damage - armor > 0) {
            hitPoints -= (damage - armor);
            log.info(hitPoints.toString());
            return true;
        }
        return false;
    }

    public final boolean addExperience(final double delta) {
        if (delta > 0) {
            final int oldLevel = level;
            changeExperience(delta);
            if (oldLevel < level) {
                levelUp();
            }
            return true;
        } else {
            return false;
        }
    }

    private void changeExperience(final double delta) {
        double experience = currentExperience + delta;
        this.currentExperience = experience;
        this.level = 1;
        for (int i = 0; i < listOfRequiredExperience.size(); i++) {
            if (experience >= listOfRequiredExperience.get(i)) {
                this.level = i + 2;
            } else {
                break;
            }
        }
    }

    public final boolean removeExperience(final double delta) {
        if (delta > 0) {
            final int oldLevel = level;
            changeExperience(delta * (-1));
            if (currentExperience < 0) {
                currentExperience = 0.0;
            }
            if (oldLevel > level) {
                levelDown();
            }
            return true;
        } else {
            return false;
        }
    }

    private void levelUp() {
        log.info("Level up: " + toString());
        final int levelShift = level - 2;
        attack += levelAttackList.get(levelShift);
        treatment += levelTreatmentList.get(levelShift);
        final double addHealth = listOfSupplyHealth.get(levelShift);
        supplyHealth += addHealth;
        hitPoints += addHealth;
        log.info(toString());
    }

    private void levelDown() {
        log.info("Level down: " + toString());
        final int levelShift = level - 1;
        attack -= levelAttackList.get(levelShift);
        treatment -= levelTreatmentList.get(levelShift);
        final double removeHealth = listOfSupplyHealth.get(levelShift);
        supplyHealth -= removeHealth;
        hitPoints -= removeHealth;
        log.info(toString());
    }

    //Animation:
    public abstract void showAttackAnimation();

    public abstract void showTreatmentAnimation();

    @Override
    public final String toString() {
        return "AHero{" +
                "\nattack=" + attack +
                "\ntreatment=" + treatment +
                "\nhitPoints=" + hitPoints +
                "\nsupplyHealth=" + supplyHealth +
                "\ncurrentExperience=" + currentExperience +
                "\nlevel=" + level +
                '}';
    }

    //Getters:
    public String getName() {
        return name;
    }

    public Presentation getPresentation() {
        return presentation;
    }

    public ImageView getFace() {
        return face;
    }

    public List<Bonus> getCurentCollection() {
        return bonusCollection;
    }

    public Double getAttack() {
        return attack;
    }

    public final void setAttack(final double attack) {
        this.attack = attack;
    }

    public Double getTreatment() {
        return treatment;
    }

    public void setTreatment(final Double treatment) {
        this.treatment = treatment;
    }

    public Double getHitPoints() {
        return hitPoints;
    }

    public final void setHitPoints(Double hitPoints) {
        this.hitPoints = hitPoints;
    }

    public Double getHealthSupply() {
        return supplyHealth;
    }

    public void setHealthSupply(final double supplyHealth) {
        this.supplyHealth = supplyHealth;
    }

    public Double getCurrentExperience() {
        return currentExperience;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(final int level) {
        this.level = level;
    }

    public double getArmor() {
        return armor;
    }

    public void setArmor(double armor) {
        this.armor = armor;
    }

    public List<Double> getListOfRequiredExperience() {
        return listOfRequiredExperience;
    }

    public List<Double> getListOfSupplyHealth() {
        return listOfSupplyHealth;
    }

    public List<Double> getLevelAttackList() {
        return levelAttackList;
    }

    public List<Double> getLevelTreatmentList() {
        return levelTreatmentList;
    }

    public List<Skill> getCollectionOfSkills() {
        return collectionOfSkills;
    }

    public Skill getSwapSkill() {
        return swapSkill;
    }

    public void setSwapSkill(Skill swapSkill) {
        this.swapSkill = swapSkill;
    }

    public final List<Bonus> getBonusCollection() {
        return bonusCollection;
    }

    public void putBonusCollection(List<Bonus> bonusCollection) {
        this.bonusCollection = bonusCollection;
    }

    public List<Media> getListOfAttackVoices() {
        return listOfAttackVoices;
    }

    public List<Media> getListOfTreatmentVoices() {
        return listOfTreatmentVoices;
    }

    //Access:
    public boolean isHealingAccess() {
        return healingAccess;
    }

    public void setHealingAccess(boolean healingAccess) {
        this.healingAccess = healingAccess;
    }

    public boolean isAttackAccess() {
        return attackAccess;
    }

    public void setAttackAccess(boolean attackAccess) {
        this.attackAccess = attackAccess;
    }

    public boolean isDamageAccess() {
        return damageAccess;
    }

    public void setDamageAccess(boolean damageAccess) {
        this.damageAccess = damageAccess;
    }

    public boolean isTreatmentAccess() {
        return treatmentAccess;
    }

    public void setTreatmentAccess(boolean treatmentAccess) {
        this.treatmentAccess = treatmentAccess;
    }
}