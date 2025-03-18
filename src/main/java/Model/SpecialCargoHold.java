package Model;

class SpecialCargoHold extends SpaceshipComponent {
    private final int capacity;
    private Good[] goods;

    public SpecialCargoHold(int capacity) {
        this.capacity = capacity;
        this.goods = new Good[capacity];
    }

    public int getCapacity() {
        return capacity;
    }

    public Good[] getGoods() {
        return goods;
    }

    public boolean addGood(Good good) {
        for (int i = 0; i < goods.length; i++) {
            if (goods[i] == null) {
                goods[i] = good;
                return true;
            }
        }
        return false;
    }

    public void removeGood(int index) {
        if (index >= 0 && index < goods.length) {
            goods[index] = null;
        }
    }
}
