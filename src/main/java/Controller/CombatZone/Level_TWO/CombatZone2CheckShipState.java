package Controller.CombatZone.Level_TWO;

import Controller.Context;
import Controller.State;
import Model.Board.AdventureCards.Components.CombatZoneLine;
import Model.Ship.Coordinates;

public class CombatZone2CheckShipState extends State {
    private Context context;

    public CombatZone2CheckShipState(Context context) {
        this.context = context;
    }

    @Override
    public void deleteComponent(String playerName, Coordinates coordinates){
        //
    }
}
