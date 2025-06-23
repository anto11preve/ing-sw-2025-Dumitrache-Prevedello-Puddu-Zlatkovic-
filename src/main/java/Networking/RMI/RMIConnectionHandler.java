package Networking.RMI;

import Controller.Server.Server;

import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIConnectionHandler implements Networking.ConnectionHandler {
    private final Dispatcher dispatcher;
    private final Registry registry;

    public RMIConnectionHandler(Server server, String hostname, String port) throws RemoteException {
        int PORT;
        try {
            PORT = Integer.parseInt(port);
        }  catch (NumberFormatException e) {
            PORT = 1099;
        }

        this.dispatcher = new DispatcherImpl(server);

        System.setProperty("java.rmi.server.hostname", hostname);

        this.registry = LocateRegistry.createRegistry(PORT);


        this.registry.rebind("Dispatcher", this.dispatcher);

        System.out.println("RMI connection handler started at port " + PORT);
    }

    @Override
    public void setDone() {
        try {
            this.registry.unbind("Dispatcher");
        } catch (NotBoundException e) {
            System.err.println("RMIConnectionHandler.setDone(): Could not unbind Dispatcher as it's apparently not bound/doesn't exist (Lucky, but how?)");
        } catch (RemoteException e) {
            System.err.println("RMIConnectionHandler.setDone(): Something went wrong in the networking layer. Doesn't matter, I'm out of here!");
        }finally {
            try {
                UnicastRemoteObject.unexportObject(this.dispatcher, true);
            } catch (NoSuchObjectException e) {
                System.err.println("RMIConnectionHandler.setDone(): Could not unexport the dispatcher object");
            }
        }
    }
}
