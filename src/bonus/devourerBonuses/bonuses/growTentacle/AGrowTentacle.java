package bonus.devourerBonuses.bonuses.growTentacle;

import bonus.bonuses.Bonus;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Pair;
import managment.actionManagement.actions.ActionEvent;
import managment.actionManagement.actions.ActionType;
import managment.actionManagement.service.components.HandleComponent;
import managment.actionManagement.service.engine.DynamicHandleService;
import managment.playerManagement.ATeam;
import managment.playerManagement.Player;
import managment.processors.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public final class AGrowTentacle extends Bonus implements DynamicHandleService{

    private static final Logger log = LoggerFactory.getLogger(AGrowTentacle.class);

    private static final int ATTACK_BOOST = 4;

    private static int CUT_TENTACLE_ID;

    public AGrowTentacle(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final Hero currentHero = playerManager.getCurrentTeam().getCurrentPlayer().getHero();
        currentHero.setAttack(currentHero.getAttack() + ATTACK_BOOST);
        log.info("+4 ATTACK");
        actionManager.getEventEngine().addHandler(getHandlerInstance());
    }

    private final class CustomProcessor implements Processor{

        private Hero opponentHero;

        private CustomProcessor(final Hero hero){
            this.opponentHero = hero;
        }

        @Override
        public final void process() {
            final List<Bonus> bonusList = opponentHero.getBonusCollection();
            final Random random = new Random();
            final int bound = 16; //16 cards;

            final int firstBonus = battleManager.getBonusGetterList().get(0).getValue();
            int secondBonus = battleManager.getBonusGetterList().get(1).getValue();
            int thirdBonus = battleManager.getBonusGetterList().get(2).getValue();
            while (secondBonus == firstBonus){
                secondBonus = random.nextInt(bound);
            }
            while (thirdBonus == firstBonus || thirdBonus == secondBonus){
                thirdBonus = random.nextInt(bound);
            }

            final List<Integer> bonusIDList = new ArrayList<>();
            bonusIDList.add(firstBonus);
            bonusIDList.add(secondBonus);
            bonusIDList.add(thirdBonus);
            bonusIDList.set(getEarlierModifiedBonusIndex(), CUT_TENTACLE_ID);

            actionManager.getGraphicEngine().showBonuses(bonusList
                    , bonusIDList.get(0), bonusIDList.get(1), bonusIDList.get(2));
            for (final int i: bonusIDList){
                log.info("BONUS ID: " + i);
            }
        }

        private int getEarlierModifiedBonusIndex(){
            final List<Pair> standardRandomBonuses = battleManager.getStandardRandomBonuses();
            int index = 0;
            standardRandomBonuses.sort((o1, o2) -> {
                final Integer priority_1 = (Integer) o1.getKey();
                final Integer priority_2 = (Integer) o2.getKey();
                return priority_2 - priority_1;
            });
            for (int i = 0; i < standardRandomBonuses.size(); i++){
                if ((Boolean) standardRandomBonuses.get(i).getKey()){
                    standardRandomBonuses.set(i, new Pair(false, standardRandomBonuses.get(i).getValue()));
                    index = i;
                    break;
                }
            }
            final List<Pair> newStandardRandomBonuses = new ArrayList<>(){{
                for (final Pair standardRandomBonuses : standardRandomBonuses) {
                    Pair<Boolean, Integer> pair = standardRandomBonuses;
                    pair = new Pair<>(pair.getKey(), (pair.getValue() + 2) % 4);
                    add(pair);
                }
            }};
            battleManager.setStandardRandomBonuses(newStandardRandomBonuses);
            return index;
        }
    }

    @Override
    public final HandleComponent getHandlerInstance() {
        return new HandleComponent() {

            private Player player;

            private ATeam opponentTeam;

            private boolean isWorking;

            @Override
            public void setup() {
                this.player = playerManager.getCurrentTeam().getCurrentPlayer();
                this.opponentTeam = playerManager.getOpponentATeam();
                this.isWorking = true;
            }

            @Override
            public void handle(final ActionEvent actionEvent) {
                if (actionEvent.getActionType() == ActionType.START_TURN
                        && (actionEvent.getPlayer() == opponentTeam.getCurrentPlayer()
                        || actionEvent.getPlayer() == opponentTeam.getAlternativePlayer())){
                    battleManager.setStandardRandomBonusEngine(false);
                    battleManager.setProcessor(new CustomProcessor(opponentTeam.getCurrentPlayer().getHero()));
                }
                if ((actionEvent.getActionType() == ActionType.END_TURN
                        || actionEvent.getActionType() == ActionType.SKIP_TURN
                        || actionEvent.getActionType() == ActionType.USED_BONUS)
                        && (actionEvent.getPlayer() == opponentTeam.getCurrentPlayer()
                        || actionEvent.getPlayer() == opponentTeam.getAlternativePlayer())){
                    battleManager.setStandardRandomBonusEngine(true);
                    battleManager.setDefaultProcessor();
                    this.isWorking = false;
                }
            }

            @Override
            public final String getName() {
                return "GrowTentacle";
            }

            @Override
            public final Player getCurrentPlayer() {
                return player;
            }

            @Override
            public final boolean isWorking() {
                return isWorking;
            }

            @Override
            public final void setAble(final boolean able) {
                throw new UnsupportedOperationException();
            }
        };
    }
}