package Organization;

/**
 * Класс координат Organization.
 */
public class Coordinates implements Comparable<Coordinates> {
    private Long x; //Поле не может быть null
    private double y;

    public Coordinates(Long x, double y){
        this.x = x;
        this.y =y;
    }

    public Coordinates(Long x){
        this.x = x;
    }

    @Override
    public String toString() {
        return ( "(" + x + ", " + y + ")" );
    }

    /**
     * @return Координату X.
     */
    public Long getX() {return x;}
    /**
     * @return Координату Y.
     */
    public double getY() {return y;}

    @Override
    public int compareTo(Coordinates coordinates) {
        int result = Long.compare(x, coordinates.getX());
        if (result==0) result = Double.compare(y, coordinates.getY());
        return result;
    }
}