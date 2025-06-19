package Networking.RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Dispatcher extends Remote {
    RMIMessageQueue dispatch(RMIMessageQueue clientQueue) throws RemoteException;
}
