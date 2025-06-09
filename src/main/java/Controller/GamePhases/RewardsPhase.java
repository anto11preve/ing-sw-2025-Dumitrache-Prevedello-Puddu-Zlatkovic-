package Controller.GamePhases;

import Controller.State;
import Model.Enums.Good;
import Model.Player;
import Model.Ship.GoodCounter;

import java.util.Arrays;
import java.util.List;

public class RewardsPhase extends State {
    public RewardsPhase() {
        super();
    }

    @Override
    public void endGame() {
        for(int i=0; i<getController().getModel().getFlightBoard().getTurnOrder().length; i++){
            Player player = getController().getModel().getFlightBoard().getTurnOrder()[i];
            player.deltaCredits(4-i);
        }

        //TODO: ricompensa nave più bella, cioè il giocatore con meno connettori esposti prende 2 crediti (a parimerito prendono entrambi)

        for(Player p : getController().getModel().getFlightBoard().getTurnOrder()){
            List<Good> allGoods = p.getShipBoard().getCondensedShip().getCargoHolds().stream()
                    .flatMap(cargo -> Arrays.stream(cargo.getGoods()))
                    .toList();
            GoodCounter counter = new GoodCounter();
            for(Good g : allGoods){
                switch(g){
                    case RED -> p.deltaCredits(4);
                    case YELLOW -> p.deltaCredits(3);
                    case GREEN -> p.deltaCredits(2);
                    case BLUE -> p.deltaCredits(1);
                    default -> {
                        // Do nothing for unknown goods
                    }
                }
            }

            p.deltaCredits(-p.getJunk());
        }

    }
}
