package Controller;
import java.rmi.*;
import java.util.Map;


public interface ControllerInterface extends Remote {
    public void send(Map<String, Object> command) throws RemoteException;
}