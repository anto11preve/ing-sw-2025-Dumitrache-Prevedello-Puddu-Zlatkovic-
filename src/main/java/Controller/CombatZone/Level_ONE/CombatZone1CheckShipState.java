package Controller.CombatZone.Level_ONE;

import Controller.Context;
import Controller.State;
import Model.Board.AdventureCards.Components.CombatZoneLine;
import Model.Ship.Coordinates;

public class CombatZone1CheckShipState extends State {
    private Context context;

    public CombatZone1CheckShipState(Context context) {
        this.context = context;
    }

    @Override
    public void deleteComponent(String playerName, Coordinates coordinates){
        //
    }
}
