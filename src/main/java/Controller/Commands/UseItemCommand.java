package Controller.Commands;

import Controller.Enums.ItemType;
import Model.Ship.Coordinates;

public class UseItemCommand extends Command {
    ItemType itemType;
    Coordinates coordinates;
    int amount;
}
