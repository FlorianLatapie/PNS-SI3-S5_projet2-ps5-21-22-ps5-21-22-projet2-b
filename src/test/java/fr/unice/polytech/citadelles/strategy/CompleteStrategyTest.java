package fr.unice.polytech.citadelles.strategy;

import fr.unice.polytech.citadelles.strategy.buildstrats.BuildStrat;
import fr.unice.polytech.citadelles.strategy.characterstrats.CharacterStrat;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompleteStrategyTest {
    @Test
    void toStringTest(){
        CompleteStrategy strat = new CompleteStrategy(new CharacterStrat(), new BuildStrat());
        assertEquals("CompleteStrategy{characterStrat=random Character Strategy, buildStrat=random Build Strategy}",strat.toString());
    }
}
