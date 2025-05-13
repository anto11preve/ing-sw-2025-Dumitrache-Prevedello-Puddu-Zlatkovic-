package Controller.MeteorsSwarm;

import Controller.Context;
import Controller.State;
import Model.Ship.Coordinates;

public class MeteorsCheckShipState extends State {
    private Context context;

    public MeteorsCheckShipState(Context context) {
        this.context = context;
    }

    @Override
    public void deleteComponent(String playerName, Coordinates coordinates){
        //giuro l'ho fatto
    }
}
