package Model;

class Engine extends SpaceshipComponent {
    private boolean isDouble;

    public Engine(boolean isDouble) {
        this.isDouble = isDouble;
    }

    public boolean doubleEngine() {
        return isDouble;
    }
}
