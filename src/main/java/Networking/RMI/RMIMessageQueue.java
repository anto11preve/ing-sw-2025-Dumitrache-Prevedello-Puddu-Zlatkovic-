package Networking.RMI;

import Networking.Messages.Message;
import Networking.Messages.MessageQueue;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.Queue;

public class RMIMessageQueue extends UnicastRemoteObject implements MessageQueue {
    private final Queue<Message> queue = new LinkedList<>();

    public RMIMessageQueue() throws RemoteException {
        super();
    }

    @Override
    public synchronized boolean enqueue(Message message) throws RemoteException {
        this.notifyAll();
        return this.queue.add(message);
    }

    @Override
    public synchronized Message dequeue() throws RemoteException {
        if (this.queue.isEmpty()) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                System.err.println("Could not wait on dequeue? Why?");
                throw new RuntimeException(e);
            }
            return null;
        }
        return queue.poll();
    }
}
