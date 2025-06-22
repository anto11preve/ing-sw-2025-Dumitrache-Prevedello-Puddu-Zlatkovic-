package Networking.RMI;

import Controller.Server.Server;
import Networking.Messages.Handler;
import Networking.Messages.MessageQueue;
import Networking.TimeoutThread;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class DispatcherImpl extends UnicastRemoteObject implements Dispatcher {
    private final Server server;

    public DispatcherImpl(Server server) throws RemoteException {
        super();
        this.server = server;
    }

    @Override
    public MessageQueue dispatch(MessageQueue clientQueue) throws RemoteException {
        System.out.println("Got new client request");

        RMIMessageQueue inQueue = new RMIMessageQueue();

        RMINetwork network = new RMINetwork(inQueue, clientQueue);

        new TimeoutThread(network).start();
        new Handler<Server>(this.server, network).start();

        this.server.connect(network);

        // Return server's inQueue to client (which will use it as outQueue)
        return inQueue;
    }
}
