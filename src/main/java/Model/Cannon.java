package Model;

class Cannon extends SpaceshipComponent {
    private boolean isDouble;

    public Cannon(boolean isDouble) {
        this.isDouble = isDouble;
    }

    public void rotate() {
    }

    public boolean doubleCannon() {
        return isDouble;
    }
}
