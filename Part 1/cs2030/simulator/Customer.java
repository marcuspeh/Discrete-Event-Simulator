package cs2030.simulator;

/**
 * Encapsulates the Customer class. The Customer class support the following methods:
 * <ol> 
 * <li>
 * getWaitTime method to return the wait time of the customer.
 * </li>
 * </ol>
 *
 * @author Marcus Peh
 * @version 1.0
 * @since 2020-09-22
 */

class Customer {
    /** Stores the Id of the customer(int). */
    private final int customerId;
    /** Stores the arrival time of the customer(double). */
    private final double arrivalTime;

    /**
     * Constructor for Customer class.
     * @param customerId The customer ID
     * @param arrivalTime The customer's arrival time
     */
    Customer(int customerId, double arrivalTime) {
        this.customerId = customerId;
        this.arrivalTime = arrivalTime;
    }
 
    public double getArrivalTime() {
        return arrivalTime;
    }

    public int getCustomerId() {
        return customerId;
    }
    
    /**
     * Calculates the time the customer waited to be served.
     * @param servedTime The time the customer is served
     * @return The difference between its arrival time and serve time (ie. waiting time)
     */
    public double getWaitTime(double servedTime) {
        return servedTime - arrivalTime;
    }

    @Override
    public String toString() {
        return this.customerId + " arrives at " + this.arrivalTime;
    }
}
