package gui.service.graphicEngine;

import bonus.bonuses.Bonus;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import controllers.main.matchmaking.ControllerMatchMaking;
import gui.service.locations.ALocation;
import gui.windows.WindowType;
import heroes.abstractHero.hero.Hero;
import heroes.abstractHero.skills.Skill;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import main.AGame;
import managment.actionManagement.ActionManager;
import managment.actionManagement.actions.ActionEvent;
import managment.actionManagement.actions.ActionEventFactory;
import managment.actionManagement.service.engine.EventEngine;
import managment.battleManagement.BattleManager;
import managment.playerManagement.GameMode;
import managment.playerManagement.Player;
import managment.playerManagement.ATeam;
import managment.playerManagement.PlayerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Singleton
public final class GraphicEngine {

    private static final Logger log = LoggerFactory.getLogger(GraphicEngine.class);

    @Inject
    private AGame aGame;

    @Inject
    private BattleManager battleManager;

    @Inject
    private PlayerManager playerManager;

    @Inject
    private EventEngine eventEngine;

    private ATeam leftTeam;

    private ATeam rightTeam;

    private ALocation leftLocation;

    private ALocation rightLocation;

    private boolean bindEnable = true;

    public final void install(){
        final ControllerMatchMaking matchMaking = (ControllerMatchMaking) aGame.getWindowMap()
                .get(WindowType.MATCHMAKING).getController();

        this.leftLocation = matchMaking.getLeftLocation();
        this.rightLocation = matchMaking.getRightLocation();
        this.leftTeam = playerManager.getLeftATeam();
        this.rightTeam = playerManager.getRightATeam();
        //Time:
        installTeamTimer(leftTeam, leftLocation, BattleManager.getStartTime());
        installTeamTimer(rightTeam, rightLocation, BattleManager.getStartTime());
        //Skills:
        wireActionManagerToSkills(matchMaking.getActionManager(), leftTeam, rightTeam);
        showLocation();
    }

    private void installTeamTimer(final ATeam team, final ALocation location, final int time){
        team.setTime(time);
        final Timeline timeline = team.getTimeline();
        timeline.setOnFinished(event -> {
            log.info("GG");
            battleManager.endGame();
        });
        final ObservableList<KeyFrame> keyFrames = team.getTimeline().getKeyFrames();
        keyFrames.add(new KeyFrame(Duration.seconds(1), event -> showLocation(location, team)));
        keyFrames.add(new KeyFrame(Duration.seconds(1), event -> {
            final ActionEvent frameEvent = ActionEventFactory.getFrame(team.getCurrentPlayer());
//            eventEngine.handle(frameEvent);
        }));
    }

    private void wireActionManagerToSkills(final ActionManager actionManager, final ATeam leftATeam, final ATeam rightATeam){
        final List<Hero> allHeroes = new ArrayList<>(){{
            add(leftATeam.getCurrentPlayer().getCurrentHero());
            add(rightATeam.getCurrentPlayer().getCurrentHero());
            if (playerManager.getGameMode() == GameMode._2x2){
                add(leftATeam.getAlternativePlayer().getCurrentHero());
                add(rightATeam.getAlternativePlayer().getCurrentHero());
            }
        }};
        for (final Hero hero: allHeroes){
            final List<Skill> skills = hero.getCollectionOfSkills();
            for (final Skill skill : skills){
                skill.setActionManager(actionManager);
                log.debug("Successfully wired action manager" + skill.getName());
            }
        }
    }

    public final void showLocation(){
        if (bindEnable){
            showLocation(leftLocation, leftTeam);
            showLocation(rightLocation, rightTeam);
        }
    }

    private void showLocation(final ALocation location, final ATeam team){
        final Player currentPlayer = team.getCurrentPlayer();
        final Hero hero = currentPlayer.getCurrentHero();
        hero.setLocation(location);
        location.setFace(currentPlayer.getCurrentHero().getFace());
        //Profile:
        location.setName(currentPlayer.getProfile().getName());
        //Attack:
        location.setAttack((hero.getAttack().intValue()));
        //Health:
        location.setHitPoints(hero.getHitPoints().intValue());
        location.setSupplyHealth(hero.getHealthSupply().intValue());
        location.setTreatment(hero.getTreatment().intValue());
        //Experience:
        location.setLevel(hero.getLevel());
        location.setExperience(hero.getCurrentExperience().intValue());
        final int level = hero.getLevel();
        if (level - 1 < 9){
           location.setRequiredExperience(hero.getListOfRequiredExperience().get(level - 1).intValue());
        } else {
            location.setRequiredExperience("");
        }
        //Skills:
        location.setupSuperSkills(hero);
        for (final Skill skill : hero.getCollectionOfSkills()){
            final boolean levelReached = hero.getLevel() >= skill.getRequiredLevel();
            if (skill.isReady() && levelReached){
                skill.getSprite().setVisible(true);
            }
        }
        final Player alternativePlayer = team.getAlternativePlayer();
        if (alternativePlayer != null){
            location.setupSwapSkill(alternativePlayer.getCurrentHero());
//        log.debug("SWAP_TEMP:" + team.getAlternativePlayer().getCurrentHero().getSwapSkill().getTemp());
//        log.debug("ALIVE: " + team.getAlternativePlayer().isAlive());
//        log.debug("READY_SWAP: " + team.getAlternativePlayer().getCurrentHero().getSwapSkill().isReady());
            if (alternativePlayer.isAlive() && alternativePlayer.getCurrentHero().getSwapSkill().isReady()){
                log.debug("SWAP_SKILL_IS_VISIBLE");
                location.getSwapSkillPane().setVisible(true);
            } else {
                log.debug("SWAP_SKILL_IS_INVISIBLE");
                location.getSwapSkillPane().setVisible(false);
            }
        }
        //Time:
        location.setTime(team.getTime());
    }

    public void show3Bonuses(final List<Bonus> bonusList
            , final int firstBonus, final int secondBonus, final int thirdBonus){
        final ControllerMatchMaking controllerMatchMaking = (ControllerMatchMaking) aGame.getWindowMap()
                .get(WindowType.MATCHMAKING).getController();
        final AnchorPane bonusLocationPane = controllerMatchMaking.getBonusLocationPane();
        bonusLocationPane.toFront();
        final Pane bonusPane = (Pane) bonusLocationPane.getChildren().get(0);
        final ImageView firstSprite = bonusList.get(firstBonus).getSprite();
        firstSprite.setLayoutX(0.0);
        bonusPane.getChildren().add(firstSprite);
        final ImageView secondSprite = bonusList.get(secondBonus).getSprite();
        secondSprite.setLayoutX(335.0);
        bonusPane.getChildren().add(secondSprite);
        final ImageView thirdSprite = bonusList.get(thirdBonus).getSprite();
        thirdSprite.setLayoutX(660.0);
        bonusPane.getChildren().add(thirdSprite);

        log.info("CHOOSE BONUS: " + bonusList.get(firstBonus).getName());
        log.info("CHOOSE BONUS: " + bonusList.get(secondBonus).getName());
        log.info("CHOOSE BONUS: " + bonusList.get(thirdBonus).getName());
        log.info("Bonuses load successfully");
    }

    public final void hideBonuses(){
        final ControllerMatchMaking controllerMatchMaking = (ControllerMatchMaking) aGame.getWindowMap()
                .get(WindowType.MATCHMAKING).getController();
        final AnchorPane bonusLocationPane = controllerMatchMaking.getBonusLocationPane();
        bonusLocationPane.toBack();
        final Pane bonusPane = (Pane) bonusLocationPane.getChildren().get(0);
        bonusPane.getChildren().clear();
    }

    public ALocation getLeftLocation() {
        return leftLocation;
    }

    public ALocation getRightLocation() {
        return rightLocation;
    }

    public boolean isBindEnable() {
        return bindEnable;
    }

    public void setBindEnable(boolean bindEnable) {
        this.bindEnable = bindEnable;
    }
}
