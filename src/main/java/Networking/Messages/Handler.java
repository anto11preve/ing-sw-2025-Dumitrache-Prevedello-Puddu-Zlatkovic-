package Networking.Messages;

import Networking.Agent;
import Networking.Network;

public class Handler<T extends Agent> extends Thread {
    protected final T reference;
    protected final Network network;

    public Handler(T reference, Network network) {
        super(reference.getClass().getSimpleName() + "Handler");
        this.reference = reference;
        this.network = network;
    }

    @Override
    public final void run() {
        while (!this.network.isDone()) {
            final Message message = this.network.read();

            if (message != null) {
                this.network.setTimeout(System.currentTimeMillis() + Network.TIMEOUT);
                try {
                    message.handle(this.reference, this.network);
                } catch (RuntimeException e) {
                    e.printStackTrace(System.err);
                    return;
                }
            }
        }
    }
}
