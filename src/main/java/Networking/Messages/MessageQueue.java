package Networking.Messages;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MessageQueue extends Remote {
    boolean enqueue(Message message) throws RemoteException;

    Message dequeue() throws RemoteException;
}
