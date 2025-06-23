package View.Client;

import Controller.Enums.MatchLevel;
import Model.Game;
import Networking.Messages.Message;

import java.util.List;

public interface ClientState {
    default List<String> getAvailableCommands() {
        return List.of("stop");
    }

    default String getUsername() {
        throw new UnsupportedOperationException("Cannot invoke getUsername on " + this.getClass().getName());
    }

    default ClientState chooseProtocol(String choice){
        throw new UnsupportedOperationException("Cannot invoke chooseProtocol on " + this.getClass().getName());
    }

    default ClientState connect(String hostname, Integer port){
        throw new UnsupportedOperationException("Cannot invoke connect on " + this.getClass().getName());
    }

    default ClientState create(MatchLevel matchLevel) {
        throw new UnsupportedOperationException("Cannot invoke create on " + this.getClass().getName());
    }

    default ClientState join(int gameId){
        throw new UnsupportedOperationException("Cannot invoke join on " + this.getClass().getName());
    }

    default ClientState joinFailed() {
        throw new UnsupportedOperationException("Cannot invoke joinFailed on " + this.getClass().getName());
    }

    default ClientState joinSuccess(Game game){
        throw new UnsupportedOperationException("Cannot invoke joinSuccess on " + this.getClass().getName());
    }

    default ClientState list(){
        throw new UnsupportedOperationException("Cannot invoke list on " + this.getClass().getName());
    }

    default ClientState login(String username) {
        throw new UnsupportedOperationException("Cannot invoke login on " + this.getClass().getName());
    }

    default ClientState loginFailed(String username){
        throw new UnsupportedOperationException("Cannot invoke loginFailed on " + this.getClass().getName());
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

    default ClientState updateGame(Game game){
        throw new UnsupportedOperationException("Cannot invoke updateGame on " + this.getClass().getName());
    }

    default ClientState updateList(Integer[] newGamesList) {
        throw new UnsupportedOperationException("Cannot invoke updateList on " + this.getClass().getName());
    }

    default boolean isDone(){
        return false;
    }
}
