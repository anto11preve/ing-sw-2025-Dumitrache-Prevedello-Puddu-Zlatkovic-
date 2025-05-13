package Model.Ship;

import Model.Enums.Good;

public class GoodCounter {
    private int red;
    private int yellow;
    private int green;
    private int blue;

    public GoodCounter() {
        this.red = 0;
        this.yellow = 0;
        this.green = 0;
        this.blue = 0;
    }

    public int getRed() {
        return red;
    }
    public int getYellow() {
        return yellow;
    }
    public int getGreen() {
        return green;
    }
    public int getBlue() {
        return blue;
    }

    public void addGood(Good good) {
        switch (good) {
            case RED:
                red++;
                break;
            case YELLOW:
                yellow++;
                break;
            case GREEN:
                green++;
                break;
            case BLUE:
                blue++;
                break;
        }
    }

    public boolean removeGood(Good good) {
        switch (good) {
            case RED:
                if (red > 0) {
                    red--;
                    return true;
                }
                break;
            case YELLOW:
                if (yellow > 0) {
                    yellow--;
                    return true;
                }
                break;
            case GREEN:
                if (green > 0) {
                    green--;
                    return true;
                }
                break;
            case BLUE:
                if (blue > 0) {
                    blue--;
                    return true;
                }
                break;
        }
        return false; // Good not found or not enough quantity
    }
}
