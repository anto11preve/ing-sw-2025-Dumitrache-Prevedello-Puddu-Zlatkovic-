package Model.Board.AdventureCards;

import Controller.*;
import Controller.Enums.DoubleType;
import Controller.Enums.MatchLevel;
import Controller.MeteorsSwarm.ManageMeteorState;
import Controller.MeteorsSwarm.MeteorsState;
import Controller.Smugglers.SecondSmugglersBatteryRemovalState;
import Controller.Smugglers.SmugglersPowerDeclarationState;
import Model.AdventureCardLoader;
import Model.Game;
import Model.Player;
import TestUtils.GameSnapshot;
import TestUtils.TestStateManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MeteorSwarmTestBugDoubleMeteor {

    @ParameterizedTest
    @ValueSource(ints = {15, 16, 24, 25, 26, 42})
    public void bug(int id){
        GameSnapshot snapshot = TestStateManager.flightPhase2Players(MatchLevel.LEVEL2);
        System.out.println(snapshot.description);
        Controller controller= snapshot.getController();
        Game model= controller.getModel();
        List<AdventureCardFilip> adventureCardFilipList= AdventureCardLoader.loadAdventureCards(MatchLevel.LEVEL2, false);
        MeteorSwarm meteorSwarm=null;
        for (AdventureCardFilip filip : adventureCardFilipList) {
            if(filip.getId()==id){
                meteorSwarm=(MeteorSwarm) filip;
            }
        }
        assertNotNull(meteorSwarm);

        List<Player> allPlayers=model.getPlayers();

        for(Player p : allPlayers){
            p.getShipBoard().render(MatchLevel.LEVEL2);
        }

        meteorSwarm.visualize();

        meteorSwarm.accept(new CardResolverVisitor(), controller);

        assertInstanceOf(MeteorsState.class, model.getState());
        assertDoesNotThrow(()->controller.throwDices("Anna"));
        assertInstanceOf(ManageMeteorState.class, model.getState());
        assertDoesNotThrow(()->controller.end("Anna"));
        assertDoesNotThrow(()->controller.end("Bob"));



        assertInstanceOf(SecondSmugglersBatteryRemovalState.class, model.getState());


    }

}