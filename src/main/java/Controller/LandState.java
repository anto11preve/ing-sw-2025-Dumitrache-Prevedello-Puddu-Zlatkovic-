
package Controller;

import Model.Board.AdventureCards.AdventureCard;
import Model.Enums.Good;
import Model.Player;

import java.util.List;
import java.util.Map;

public class LandState extends State {

    Controller controller;
    List<Good> goods;
    Player player;
    AdventureCard card;


    public LandState(Controller controller, Enemy card, Player player) {
        this.controller = controller;
        this.card = card;
        this.player = player;
        this.goods = (Enemy) card.getWinReward();
    }

    public LandState(Controller controller, AdventureCard card, Player player, List<Good> goods) {
        this.controller = controller;
        this.card = card;
        this.player = player;
        this.goods = goods;
    }

    @Override
    public void execute(Map<String, Object> command, Controller controller) {
        String action = (String) command.get("action");
        String playerName = (String) command.get("playerName");
        if(player.getName().equals(playerName))
        switch (action) {
            case "GetGood":     //riceve la persona che chiama la execute, quale good prendere e dove metterlo
                int goodIndex = (int) command.get("goodIndex");
                if (goodIndex >= 0 && goodIndex < goods.size()){
                    Good selectedGood = goods.get(goodIndex);
                    //aggiungi il good in quel posto
                    goods.remove(selectedGood);
                    if(goods.isEmpty()){
                        int position = controller.getModel().getFlightboard().getPosition(player);
                        int temp = controller.getModel().getFlightboard().updatePosition(player, card.getWinPenalty().getAmount());
                        controller.getModel().getFlightboard().updatePosition(player, position + temp);
                        controller.setState(new FlightPhase());
                    } else {
                        controller.setState(new LandState(controller, card, player, goods));
                    }
                }
                break;
            case "MoveGood":    //riceve la persona, quale good spostare, dove spostarlo e da dove spostarlo

                break;
            case "DontGetReward":
                controller.setState(new FlightPhase());
            default:
                //do nothing
                break;
        }
    }
}
