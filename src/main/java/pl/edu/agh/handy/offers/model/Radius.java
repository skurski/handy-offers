package pl.edu.agh.handy.offers.model;

/**
 * Distance in kilometers from user location, radius value for offers searching.
 */
public enum Radius {
    ZERO(0, "+ 0 km"),
    ONE(1, "+ 1 km"),
    TWO(2, "+ 2 km"),
    THREE(3, "+ 3 km"),
    FOUR(4, "+ 4 km"),
    FIVE(5, "+ 5 km"),
    SIX(6, "+ 6 km"),
    SEVEN(7, "+ 7 km"),
    EIGHT(8, "+ 8 km"),
    NINE(9, "+ 9 km"),
    TEN(10, "+ 10 km"),
    FIFTEEN(15, "+ 15 km"),
    TWENTY(20, "+ 20 km"),
    FIFTY(50, "+ 50 km"),
    HUNDRED(100, "+ 100 km");

    private int value;
    private String description;

    Radius(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
