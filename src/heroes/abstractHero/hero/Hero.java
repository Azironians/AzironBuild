package heroes.abstractHero.hero;

import bonus.bonuses.Bonus;
import gui.service.locations.ALocation;
import heroes.abstractHero.bonusManagement.BonusManager;
import heroes.abstractHero.skills.Skill;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Hero {

    private static final Logger log = LoggerFactory.getLogger(Hero.class);

    private static final double START_EXPERIENCE = 0.0;

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

    //Other abilities:
    private final Map<String, Object> additionalAbilityMap;

    //Outer:
    private final ImageView face; //Картинка героя
    private final List<Media> listOfAttackVoices;
    private final List<Media> listOfTreatmentVoices;

    public final void reloadSkills() {
        collectionOfSkills.forEach(Skill::reload);
    }

    //Hero constructor:
    public Hero(final String name, final Double attack, final Double treatment, final Double hitPoints, final Double supplyHealth
            , final Double currentExperience, final int level, final List<Double> listOfRequiredExperience
            , final List<Double> levelAttackList, final List<Double> levelTreatmentList, final List<Double> listOfSupplyHealth
            , final List<Skill> collectionOfSkills, final Skill swapSkill
                //Outer:
            , final ImageView face, final List<Media> listOfAttackVoices, final List<Media> listOfTreatmentVoices) {
        //Main characteristics:
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
        //Other ability map:
        this.additionalAbilityMap = new HashMap<String, Object>();
        //Outer:
        this.face = face;
        this.listOfAttackVoices = listOfAttackVoices;
        this.listOfTreatmentVoices = listOfTreatmentVoices;
    }

    //ActionAccess:
    private boolean healingAccess = true;

    private boolean treatmentAccess = true;

    private boolean attackAccess = true;

    private boolean damageAccess = true;

    //ActionEvents:

    private double damageCoefficient = 1;

    private double armor = 0;

    public final boolean getDamage(final double damage) {
        if (damageAccess && damage - armor > 0) {
            hitPoints -= (damage - armor);
            log.info("WAS DAMAGE: " + damage);
            return true;
        }
        return false;
    }

    private double healingCoefficient = 1;

    public final boolean getHealing(final double healing) {
        if (hitPoints < supplyHealth) {
            if (hitPoints + healing > supplyHealth) {
                hitPoints = supplyHealth;
            } else {
                hitPoints += healing * healingCoefficient;
            }
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
        return "Hero{" +
                "\n" + name +
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

    public final void putBonusCollection(final List<Bonus> bonusCollection) {
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

    //Coefficients:
    public final double getDamageCoefficient() {
        return damageCoefficient;
    }

    public final void setDamageCoefficient(double damageCoefficient) {
        this.damageCoefficient = damageCoefficient;
    }

    public final double getHealingCoefficient() {
        return healingCoefficient;
    }

    public final void setHealingCoefficient(double healingCoefficient) {
        this.healingCoefficient = healingCoefficient;
    }

    //Bonus management:
    private final BonusManager bonusManager = new BonusManager();

    public final BonusManager getBonusManager() {
        return bonusManager;
    }

    public final Map<String, Object> getAdditionalAbilityMap() {
        return additionalAbilityMap;
    }

    //Location:
    private ALocation location;

    public final ALocation getLocation(){
        return this.location;
    }

    public final void setLocation(final ALocation location){
        this.location = location;
    }
}