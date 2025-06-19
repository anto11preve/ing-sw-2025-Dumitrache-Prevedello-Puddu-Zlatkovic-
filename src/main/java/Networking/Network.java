package Networking;

import Networking.Messages.Message;

public interface Network {
    long TIMEOUT = 10000;

    boolean send(Message message);

    Message read();

    void setDone();

    boolean isDone();

    long getTimeout();

    void setTimeout(long timeout);
}
