package Networking.RMI;

import Networking.Messages.Message;
import Networking.Messages.QuitMessage;
import Networking.Network;
import Networking.TimeoutThread;

import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMINetwork implements Network {
    private final RMIMessageQueue inQueue = new RMIMessageQueue();
    private final RMIMessageQueue outQueue;
    private boolean done = false;

    private long timeout = System.currentTimeMillis() + TIMEOUT;;

    public RMINetwork(String hostname, Integer port) throws RemoteException, NotBoundException {
        final Registry registry = LocateRegistry.getRegistry(hostname, port != null ? port : 1099);

        final Dispatcher dispatcher = (Dispatcher) registry.lookup("Dispatcher");

        this.outQueue = dispatcher.dispatch(this.inQueue);

        new TimeoutThread(this).start();
    }

    public RMINetwork(RMIMessageQueue outQueue) throws RemoteException {
        this.outQueue = outQueue;

        new TimeoutThread(this).start();
    }

    public RMIMessageQueue getInQueue() {
        return inQueue;
    }

    @Override
    public Message read(){
        try {
            return this.inQueue.dequeue();
        } catch (RemoteException e) {
            this.setDone();
            return null;
        }
    }

    @Override
    public boolean send(Message message) {
        try {
            return this.outQueue.enqueue(message);
        } catch (RemoteException e) {
            return false;
        }
    }

    @Override
    public synchronized void setDone() {
        if(this.done) return;
        send(new QuitMessage());
        this.done = true;
        try {
            synchronized (this.inQueue) {
                UnicastRemoteObject.unexportObject(this.inQueue, true);
                this.inQueue.notifyAll();
            }

        } catch (NoSuchObjectException e) {
            System.err.println("Tried to unexport non-exported queue");
        }
    }

    @Override
    public synchronized boolean isDone() {
        return this.done;
    }

    @Override
    public long getTimeout() {
        return this.timeout;
    }

    @Override
    public void setTimeout(long timeout) {
        this.timeout = timeout;

    }
}
