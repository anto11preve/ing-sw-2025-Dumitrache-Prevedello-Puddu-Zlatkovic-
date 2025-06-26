package View.Client;

import Controller.Enums.MatchLevel;
import Model.Game;
import Model.Ship.Coordinates;
import Networking.Messages.Message;

import java.util.List;

public interface ClientState {
    default List<String> getAvailableCommands() {
        return List.of("Stop");
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

    default ClientState login(String username) {
        throw new UnsupportedOperationException("Cannot invoke login on " + this.getClass().getName());
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

    /*Received from network*/
    default ClientState net_Fly() {
        throw new UnsupportedOperationException("Cannot invoke fly on " + this.getClass().getName());
    }

    default ClientState net_JoinFailed() {
        throw new UnsupportedOperationException("Cannot invoke joinFailed on " + this.getClass().getName());
    }

    default ClientState net_JoinSuccess(Game game){
        throw new UnsupportedOperationException("Cannot invoke joinSuccess on " + this.getClass().getName());
    }

    default ClientState net_Leave(String username) {
        throw new UnsupportedOperationException("Cannot invoke leave on " + this.getClass().getName());
    }

    default ClientState net_LoginFailed(String username){
        throw new UnsupportedOperationException("Cannot invoke loginFailed on " + this.getClass().getName());
    }

    default ClientState net_LoginSuccess(String username){
        throw new UnsupportedOperationException("Cannot invoke loginSuccess on " + this.getClass().getName());
    }

    default ClientState net_Reward() {
        throw new UnsupportedOperationException("Cannot invoke reward on " + this.getClass().getName());
    }
    default ClientState net_Start() {
        throw new UnsupportedOperationException("Cannot invoke start on " + this.getClass().getName());
    }

    default ClientState updateGame(Game game){
        throw new UnsupportedOperationException("Cannot invoke updateGame on " + this.getClass().getName());
    }

    default ClientState updateList(Integer[] newGamesList) {
        throw new UnsupportedOperationException("Cannot invoke updateList on " + this.getClass().getName());
    }

    /*Visualizations*/
    default void list(){
        throw new UnsupportedOperationException("Cannot invoke list on " + this.getClass().getName());
    }

    default void viewCard() {
        throw new UnsupportedOperationException("Cannot invoke viewCard on " + this.getClass().getName());
    }

    default void viewComponent(String username, Coordinates coordinates){
        throw new UnsupportedOperationException("Cannot invoke viewComponent on " + this.getClass().getName());
    }

    default void viewFlightBoard(){
        throw new UnsupportedOperationException("Cannot invoke viewFlightBoard on " + this.getClass().getName());
    }

    default void viewPlayers(){
        throw new UnsupportedOperationException("Cannot invoke viewPlayers on " + this.getClass().getName());
    }

    default void viewRewards(){
        throw new UnsupportedOperationException("Cannot invoke viewRewards on " + this.getClass().getName());
    }

    default void viewShipBoard(String username){
        throw new UnsupportedOperationException("Cannot invoke viewShipBoard on " + this.getClass().getName());
    }

    default void viewTiles(){
        throw new UnsupportedOperationException("Cannot invoke viewShipBoard on " + this.getClass().getName());
    }
}
