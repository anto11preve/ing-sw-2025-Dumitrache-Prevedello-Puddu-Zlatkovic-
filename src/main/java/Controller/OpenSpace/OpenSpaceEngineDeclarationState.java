package Controller.OpenSpace;

import Controller.Context;
import Controller.Controller;
import Controller.Enums.DoubleType;
import Controller.State;
import Model.Player;

public class OpenSpaceEngineDeclarationState extends State {
    private Context context;

    public OpenSpaceEngineDeclarationState(Context context) {
        this.context = context;
    }

    @Override
    public void declaresDouble(String playerName, DoubleType doubleType, int amount){
        if(doubleType != DoubleType.ENGINES){
            return;
        }
        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if(player == controller.getModel().getFlightBoard().getTurnOrder()[0])
            return; // Handle the case where it's not the player's turn

        //logica per controllare che abbia veramente quella potenza e le batterie necessarie

        controller.setState(new OpenSpaceBatteryRemovalState(context, amount));
    }
}
