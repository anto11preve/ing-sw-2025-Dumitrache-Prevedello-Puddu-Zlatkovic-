package Client;
import Controller.ControllerInterface;

import java.rmi.*;
import java.rmi.registry.*;
import java.util.*;

public class Client {



        public static void main(String[] args) throws RemoteException, NotBoundException {
            Registry registry = LocateRegistry.getRegistry("localhost");

            ControllerInterface controller = (ControllerInterface) registry.lookup("Game_Controller");

            Map<String, Object> command = new HashMap<>();
            command.put("action", "Testing");
            command.put("playerName", "Antonio");
            command.put("Age", 21);


        }
}
