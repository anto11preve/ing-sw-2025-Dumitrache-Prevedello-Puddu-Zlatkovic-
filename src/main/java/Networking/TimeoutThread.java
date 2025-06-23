package Networking;

import Networking.Messages.PingMessage;

public class TimeoutThread extends Thread {
    private final Network network;

    public TimeoutThread(Network network) {
        super("TimeoutThread");
        this.network = network;
    }

    @Override
    public void run() {
        while (!this.network.isDone()) {
            try {
                if (System.currentTimeMillis() < this.network.getTimeout() - (Network.TIMEOUT / 2)) {
                    Thread.sleep(Network.TIMEOUT / 10);
                } else if (System.currentTimeMillis() < this.network.getTimeout()) {
                    if (!this.network.send(new PingMessage()) && !this.network.isDone()) {
                        System.err.println("TimeoutThread: Could not send ping but network isn't done. What?");
                    }
                    Thread.sleep(Network.TIMEOUT / 20);
                } else {
                    System.err.println("TimeoutThread: Killing network due to timeout");
                    this.network.setDone();
                }
            } catch (InterruptedException e) {
                System.err.println("TimeoutThread: Could not sleep. exiting...");
                return;
            }
        }
    }
}