package Controller;

import Controller.AbandonedShip.AbandonedShipDecidingState;
import Controller.AbandonedStation.AbandonedStationDecidingState;
import Controller.CombatZone.Level_ONE.CombatZone1EngineDeclarationState;
import Controller.CombatZone.Level_TWO.CombatZone2EngineDeclarationState;
import Controller.Exceptions.InvalidParameters;
import Controller.GamePhases.FlightPhase;
import Controller.MeteorsSwarm.MeteorsState;
import Controller.OpenSpace.OpenSpaceEngineDeclarationState;
import Controller.Pirates.PiratesPowerDeclarationState;
import Controller.Planets.ChoosePlanetState;
import Controller.Slavers.SlaversPowerDeclarationState;
import Controller.Smugglers.SmugglersPowerDeclarationState;
import Model.Board.AdventureCards.*;
import Model.Board.AdventureCards.Penalties.RegularPenalty;
import Model.Enums.CardLevel;
import Model.Enums.Crewmates;
import Model.Exceptions.InvalidMethodParameters;
import Model.Player;
import Model.Ship.Components.Cabin;
import Model.Ship.Components.SpaceshipComponent;
import Model.Ship.Coordinates;

import java.util.HashSet;
import java.util.Set;

public class CardResolverVisitor {

    public CardResolverVisitor() {}

    public void visit(AbandonedShip card, Controller controller) {
        /*
        si fa uno alla volta, fino a che qualcuno effettivamente la svolge, in ordine di rotta
        può rinunciare a tot quipaggio per dei crediti e perdere giorni di volo
         */
        Context context = new Context(controller, card);
        controller.getModel().setState(new AbandonedShipDecidingState(context));
    }

    public void visit(AbandonedStation card, Controller controller) {
        /*
        si fa uno alla volta, fino a che qualcuno effettivamente la svolge, in ordine di rotta
        se il giocatore ha abbastanza equipaggio, può caricare le merci dove vuole e ridistribuirle
        il giocatore perde giorni di volo
         */
        Context context = new Context(controller, card);
        controller.getModel().setState(new AbandonedStationDecidingState(context));
    }

    public void visit( Planets card, Controller controller) {
        /*

         */
        Context context = new Context(controller, card);
        controller.getModel().setState(new ChoosePlanetState(context));
    }

    public void visit(CombatZone card, Controller controller) throws InvalidMethodParameters, InvalidParameters {
        /*
        1. si calcola il giocatore con meno potenza di fuoco e si perdono giorni di volo
        2. si calcola il giocatore con meno potenza motrice e si perdono delle merci
           le merci vanno tolte le più preziose (penso che a parità si faccia decidere al giocatore)
        3. si calcola il giocatore con meno equipaggio e si gestiscono le cannonate
           le cannonate piccole si parano solo con gli scudi, quelle grandi si prega
        in caso di parità, il più avanti nella rotta sconta la penalità
         */
        Context context = new Context(controller, card);
        CombatZone Ccard = (CombatZone) card;
        if(Ccard.getLevel() == CardLevel.LEVEL_ONE){
            int numPlayers = controller.getModel().getFlightBoard().getTurnOrder().length;
            Player currentPlayer = controller.getModel().getFlightBoard().getTurnOrder()[0];
            for(int i = 0; i<numPlayers; i++){
                Player nextPlayer = controller.getModel().getFlightBoard().getTurnOrder()[(i+1)];
                if(nextPlayer.getShipBoard().getCondensedShip().getTotalCrew() > currentPlayer.getShipBoard().getCondensedShip().getTotalCrew()){
                    currentPlayer = nextPlayer;
                }
            }
            RegularPenalty penalty = (RegularPenalty) Ccard.iterator().next().getPenalty();
            controller.getModel().getFlightBoard().deltaFlightDays(currentPlayer, penalty.getAmount());
            controller.getModel().setState(new CombatZone2EngineDeclarationState(context));
        } else if( Ccard.getLevel() == CardLevel.LEVEL_TWO) {
            controller.getModel().setState(new CombatZone1EngineDeclarationState(context));
        } else{
            throw new InvalidMethodParameters("Invalid card level for CombatZone: " + Ccard.getLevel());
        }

    }

    public void visit( Epidemic card, Controller controller) {
        /*
        si scorrono le cabine, e se essa è adiacente a un'altra cabina, si rimuove un membro dell'equipaggio (alieno o umano)
         */
        for(Player p : controller.getModel().getFlightBoard().getTurnOrder()){
            Set<Cabin> processed = new HashSet<>();
            for(int i=4; i<10; i++){{
                for(int j=4; j<9; j++){
                    if(p.getShipBoard().getCondensedShip().getCabins().contains(p.getShipBoard().getComponent(new Coordinates(i,j)))){
                    SpaceshipComponent c = p.getShipBoard().getComponent(new Coordinates(i,j));  //come faccio a dire che è una cabina?
                        int [][] directions = {{-1,0}, {1,0}, {0,-1}, {0,1}};

                        for(int [] d : directions){
                            int newRow = i + d[0];
                            int newColumn = j + d[1];

                            if(p.getShipBoard().getComponent(new Coordinates(newRow,newColumn)) != null){
                                SpaceshipComponent adjacent = p.getShipBoard().getComponent(new Coordinates(newRow,newColumn));  //come faccio a dire che è una cabina?
                                if( !processed.contains(adjacent) && p.getShipBoard().getCondensedShip().getCabins().contains(adjacent)){   //da rivedere
                                    Cabin cNew = (Cabin) c;
                                    Crewmates firstCabin = cNew.getOccupants();
                                    Cabin adjacentNew = (Cabin) adjacent;
                                    Crewmates secondCabin = adjacentNew.getOccupants();    //da rivedere
                                    switch(firstCabin){
                                        case SINGLE_HUMAN, BROWN_ALIEN, PURPLE_ALIEN:
                                            cNew.setOccupants(Crewmates.EMPTY);
                                            break;
                                        case DOUBLE_HUMAN:
                                            cNew.setOccupants(Crewmates.SINGLE_HUMAN);
                                            break;
                                        default:
                                            break;
                                    }

                                    switch(secondCabin){
                                        case SINGLE_HUMAN, BROWN_ALIEN, PURPLE_ALIEN:
                                            adjacentNew.setOccupants(Crewmates.EMPTY);
                                            break;
                                        case DOUBLE_HUMAN:
                                            adjacentNew.setOccupants(Crewmates.SINGLE_HUMAN);
                                            break;
                                        default:
                                            break;
                                    }

                                    processed.add(cNew);
                                    processed.add(adjacentNew);

                                }

                            }
                        }
                    }
                }
            }

            }
        }

        controller.getModel().setState(new FlightPhase(controller));
    }

    public void visit(MeteorSwarm card, Controller controller) {
        /*
        il leader tira due dadi e determina riga e colonna che vengono colpite
        si controlla se ogni giocatore viene colpito
        se è un piccolo meteorite, si controlla il side dove colpisce: se è esposto è un problema
        il giocatore può nel caso decidere se usare lo scudo o no (-1 batteria)
        se si rompe il componente, aggiungere al Junk un pezzo
        fare la check integrity della nave
        Per il grosso meteorite si controllano eventuali cannoni che possono sparare ( se è doppio -1 batteria)


         */

        Context context = new Context(controller, card);
        controller.getModel().setState(new MeteorsState(context));
    }

    public void visit( OpenSpace card, Controller controller) {
        /*
        deve ricevere in qualche modo il numero di batterie che ciascun giocatore vuole usare
        poi semplicemente si calcola la potenza motrice di ciascun giocatore e si muove la nave sulla flighboard
        */
        Context context = new Context(controller, card);
        controller.getModel().setState(new OpenSpaceEngineDeclarationState(context));
    }

    public void visit( Pirates card, Controller controller) {
        /*
        come i controabbandieri
        si vincono crediti e se si perde si ricevono cannonate
         */

        Context context = new Context(controller, card);
        controller.getModel().setState(new PiratesPowerDeclarationState(context));
    }

    public void visit( Slavers card, Controller controller) {
        /*
        come i controabbandieri
        si vincono crediti e di perde equipaggio
         */

        Context context = new Context(controller, card);
        controller.getModel().setState(new SlaversPowerDeclarationState(context));
    }

    public void visit( Smugglers card, Controller controller) {
        /*
        in ordine di rotta, si decide se il giocatore vince/perde o si va avanti, in base alla potenza di fuoco
        1. se vince, il nemico è sconfitto, il giocatore può reclamare le merci e spostarle anche, perdendo giorni di volo
        2. se perde, si paga la penalità e si passa al giocatore dopo
         */

        Context context = new Context(controller, card);
        controller.getModel().setState(new SmugglersPowerDeclarationState(context));
    }

    public void visit( Stardust card, Controller controller) {
        /*
        in ordine inverso di rotta si scorre per vedere se un giocatore ha connettori esposti, e
        in base al loro tipo si perdono giorni di rotta
         */
    }

    public interface SpaceshipComponentVisitor {
        void visit(Cabin cabin);
        // You can add more components later as needed
    }


}
