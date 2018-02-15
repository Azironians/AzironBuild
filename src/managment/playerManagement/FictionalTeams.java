package managment.playerManagement;

import heroes.abstractHero.hero.Hero;
import heroes.lv.builder.LVBuilder;
import heroes.orcBash.builder.OrcBashBuilder;
import managment.profileManagement.Profile;
import org.jetbrains.annotations.NotNull;

/**
 * Test factory imitates players in match.
 * Temporary test clazz.
 */

public final class FictionalTeams {

    @NotNull
    public static ATeam createLeft(){

        final Profile joysProfile = new Profile("Joe", 0, null, null, null
                , null, null, 0, null);
        final Profile goresProfile = new Profile("Gore", 0, null, null, null
                , null, null, 0, null);

        final Hero joysDevourer = new OrcBashBuilder().buildHero();
        final Hero goresOrcBash = new OrcBashBuilder().buildHero();

        final Player joysPlayer = new Player(joysProfile, joysDevourer);
        final Player goresPlayer = new Player(goresProfile, goresOrcBash);

        return new ATeam(goresPlayer, joysPlayer);
    }

    @NotNull
    public static ATeam createRight(){
        final Profile mikesProfile = new Profile("Mike", 0, null, null, null
                , null
                , null, null, null);
        final Profile kevinProfile = new Profile("Kevin", 0, null, null, null
                , null
                , null, null, null);

        final Hero mikesLordVampire = new LVBuilder().buildHero();
        final Hero kevinOrcBash = new LVBuilder().buildHero();

        final Player mikesPlayer = new Player(mikesProfile, mikesLordVampire);
        final Player kevinPlayer = new Player(kevinProfile, kevinOrcBash);

        return new ATeam(mikesPlayer, kevinPlayer);
    }
}
