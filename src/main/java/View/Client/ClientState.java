package View.Client;

import Networking.Messages.Message;

import java.util.List;

public interface ClientState {
    default List<String> getAvailableCommands() {
        return List.of("stop");
    }

    default ClientState connect(String hostname, Integer port, boolean useRMI){
        throw new UnsupportedOperationException("Cannot invoke connect on " + this.getClass().getName());
    }

    default ClientState login(String username) {
        throw new UnsupportedOperationException("Cannot invoke login on " + this.getClass().getName());
    }

    default ClientState loginSuccess(String username){
        throw new UnsupportedOperationException("Cannot invoke loginSuccess on " + this.getClass().getName());
    }

    default ClientState send(Message message){
        throw new UnsupportedOperationException("Cannot invoke send on " + this.getClass().getName());
    }

    default ClientState stop() {
        return new ClientState() {
            @Override
            public boolean isDone() {
                return true;
            }
        };
    }

    default boolean isDone(){
        return false;
    }
}
