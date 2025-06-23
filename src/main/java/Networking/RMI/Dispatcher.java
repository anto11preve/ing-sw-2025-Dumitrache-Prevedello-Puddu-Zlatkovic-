package Networking.RMI;

import Networking.Messages.MessageQueue;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Dispatcher extends Remote {
    MessageQueue dispatch(MessageQueue clientQueue) throws RemoteException;
}
