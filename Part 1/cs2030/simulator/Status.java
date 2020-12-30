package cs2030.simulator;

/** 
 * Status of each event. It also stores the weight of each event.
 * 
 * @author Marcus Peh
 * @version 1.0
 * @since 2020-09-22
 */

enum Status {
    ARRIVE(4), WAIT(3), SERVE(2), LEAVE(1), DONE(1);  

    /** 
     * Stores the weight of each status to use in TimeComparator{@link TimeComparator} 
     * in case of tie breaker.
     */
    private final int weight;

    /**
     * Constructor for the enum.
     * @param weight The weight of the status
     */
    Status(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return this.weight;
    }
}
