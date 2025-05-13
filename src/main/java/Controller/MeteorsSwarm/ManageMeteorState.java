package Controller.MeteorsSwarm;

import Controller.Context;
import Controller.Enums.ItemType;
import Controller.State;
import Model.Ship.Coordinates;

public class ManageMeteorState extends State {
    private Context context;
    private int row;
    private int column;

    public ManageMeteorState(Context context, int row, int column) {
        this.context = context;
        this.row = row;
        this.column = column;
    }

    @Override
    public void end(String playerName) {
        //
    }

    @Override
    public void useItem(String playerName, ItemType itemType, Coordinates coordinates ){
        //usa batteria per scudo o cannone
    }
}
