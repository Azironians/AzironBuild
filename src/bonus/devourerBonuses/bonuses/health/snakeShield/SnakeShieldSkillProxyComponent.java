package bonus.devourerBonuses.bonuses.health.snakeShield;

import heroes.abstractHero.skills.Skill;
import javafx.scene.layout.Pane;
import javafx.util.Pair;
import lib.duplexMap.DuplexMap;
import managment.playerManagement.Player;

import java.util.HashMap;
import java.util.Map;

public final class SnakeShieldSkillProxyComponent {

    private Player player;

    private Pane proxySnakeShieldPane;

    //Skill HashCode vs Pair: place in hero skill collection & place in proxy container;
    private final Map<Integer, Pair<Integer, Integer>> hashVsPairMap;

    private final DuplexMap<Skill, Skill> skillVsProxyMap;

    private Skill justInTimeSnakeShieldSkill;

    SnakeShieldSkillProxyComponent(final Player player){
        this.player = player;
        this.hashVsPairMap = new HashMap<>();
        this.skillVsProxyMap = new DuplexMap<>();

    }








}
