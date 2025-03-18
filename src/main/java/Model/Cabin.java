package Model;

class Cabin extends SpaceshipComponent {
    private Crewmates occupants;

    public Cabin(Crewmates occupants) {
        this.occupants = occupants;
    }

    public Crewmates getOccupants() {
        return occupants;
    }
}
