package cs2030.simulator;

/**
 * Encapsulates event for SERVER status. The Serve event class support the following methods:
 * <ol> 
 * <li>
 * doneUpdate method for updating the queue if the last customer is served.
 * </li>
 * <li> 
 * update() method to move the queue such that the person in line is being served.
 * </li>
 * Overloaded update(time) method to add a new person into the queue.
 * </li>
 * </ol>
 *
 * @author Marcus Peh
 * @version 1.0
 * @since 2020-09-22
 */

class Server {
    /** Stores the id of the server. */
    private final int identifier;
    /** Stores if the serve is free to serve customer. */
    private final boolean isAvailable;
    /** Stores if there is someone queueing up to wait for the server. */
    private final boolean hasWaitingCustomer;
    /** Stores the next timing where the server can serve the next customer.*/
    private final double nextAvailableTime;
    /** 
     * Stores the default time taken to serve({@1.0}). 
     */
    private final double servingTime = 1.0;

    /**
     * Constructor for server.
     * @param identifier Server ID
     * @param isAvailable Whether the server is serving anyone
     * @param hasWaitingCustomer Whether the server has someone waiting in queue
     * @param nextAvailableTime The time the server will be able to serve the next Customer
     */
    Server(int identifier, boolean isAvailable, boolean hasWaitingCustomer, 
            double nextAvailableTime) {
        this.identifier = identifier;
        this.isAvailable = isAvailable;
        this.hasWaitingCustomer = hasWaitingCustomer;
        this.nextAvailableTime = nextAvailableTime;
    }
 
    public double getNextAvailableTime() {
        return nextAvailableTime;
    }

    public boolean checkAvailable() {
        return this.isAvailable;
    }

    public boolean checkCanQueue() {
        return !this.hasWaitingCustomer;
    }

    public int getIdentifier() {
        return this.identifier;
    }

    /**
     * Returns a new Server if there is no one else in the queue and that it has 
     * finished serving the customer it is serving.
     * @return A server.
     */
    public Server doneUpdate() {
        if (!hasWaitingCustomer && !isAvailable) {
            return new Server(this.identifier, true, false, 
                    this.nextAvailableTime + servingTime);
        } else {
            return this;
        }
    }

    /**
     * Return a new updated server where the queue moves forward by 1.
     * @return A server
     */
    public Server update() {
        if (hasWaitingCustomer) {
            return new Server(this.identifier, false, false, 
                    this.nextAvailableTime + servingTime);
        } else if (!this.isAvailable) {
            return new Server(this.identifier, true, false, this.nextAvailableTime);
        } else {
            return this;
        }
    }    

    /**
     * Add in a new customer to the queue.
     * @param time Customer's arrival time
     * @return A server
     */
    public Server update(double time) {
        if (this.isAvailable) {
            return new Server(this.identifier, false, false, time + servingTime);
        } else if (!this.hasWaitingCustomer) {
            return new Server(this.identifier, false, true, this.nextAvailableTime);
        } else {
            return this;
        }
    }

    @Override
    public String toString() {
        if (this.isAvailable) {
            return this.identifier + " is available";
        } else if (this.hasWaitingCustomer) {
            return this.identifier + " is busy; waiting customer to be served at " + 
                    String.format("%.3f", this.nextAvailableTime);
        } else {
            return this.identifier + " is busy; available at " +
                    String.format("%.3f", this.nextAvailableTime);
        }
    }
}
