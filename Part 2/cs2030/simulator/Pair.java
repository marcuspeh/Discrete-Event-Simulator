package cs2030.simulator;

/** 
 * Encapsulates a Pair class that is useful when returning multiple 
 * values from a function. 
 */
public class Pair<T, U> {
    /** Stores the first item. */
    T t;
    /** Stores the second item. */
    U u;

    /**
     * Constructor for Pair class.
     * @param t First item to be stored.
     * @param u Second item to be stored.
     */
    Pair(T t, U u) {
        this.t = t;
        this.u = u;
    }

    /**
     * Static method that creates a new Pair event.
     * @param <T> Type of first item.
     * @param <U> Type of second item.
     * @param t First item.
     * @param u Second item.
     * @return A new Pair that stores both first and second item.
     */
    public static <T, U> Pair<T, U> of(T t, U u) {
        return new Pair<>(t, u);
    }

    /**
     * Gets the first item.
     * @return first item stored.
     */
    public T first() {
        return t;
    } 

    /**
     * Gets the second item.
     * @return second item stored.
     */
    public U second() {
        return u;
    }
}
