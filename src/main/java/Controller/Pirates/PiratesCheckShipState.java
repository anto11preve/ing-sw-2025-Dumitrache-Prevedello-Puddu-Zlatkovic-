package Controller.Pirates;

import Controller.Context;
import Controller.State;
import Model.Ship.Coordinates;

public class PiratesCheckShipState extends State {
    private Context context;

    public PiratesCheckShipState(Context context) {
        this.context = context;
    }

    @Override
    public void deleteComponent(String playerName, Coordinates coordinates){
        //
    }
}
