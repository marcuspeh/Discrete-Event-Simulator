package cs2030.simulator;

import java.util.function.Supplier;

/**
 * Encapsulates event for SERVER status. The Serve event class support the following methods:
 * <ol> 
 * <li>
 * updateQueue method to update the queue count for the servers.
 * </li>
 * <li>
 * checkRest method to check if server will rest after serving.
 * </li>
 * <li>
 * getRest method to get the update the server to rest.
 * </li>
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
 * @version 2.0
 * @since 2020-11-11
 */
class Server {
    /** Stores the type of server(HUMAN, SELFCHECKOUT). */
    private final ServerType serverType;
    /** Stores the id of the server. */
    private final int identifier;
    /** Stores if the serve is free to serve customer. */
    private final boolean isAvailable;
    /** Stores the number of people waiting. */
    private final int waitingCustomer;
    /** Stores the next timing where the server can serve the next customer.*/
    private final double nextAvailableTime;
    /** Stores a supplier that generates the serving time. */
    private final Supplier<Double> servingTime;
    /** Stores the maximum queue length. */
    private final int qmax;
    /** Stores the probability of rest. */
    private final double prest;
    /** Stores a supplier that generates the resting rate. */
    private final Supplier<Double> restRate;
    /** Stores a supplier that generate the resting time. */
    private final Supplier<Double> restTime;

    /**
     * Constructor for server.
     * @param serverType Type of server.
     * @param identifier Server ID.
     * @param isAvailable Whether the server is serving anyone.
     * @param waitingCustomer Number of waiting customer for the server.
     * @param nextAvailableTime The time the server will be able to serve the next customer.
     * @param qmax The maximum length of the queue.
     * @param servingTime Supplier that generates serving time.
     * @param prest Probability of rest.
     * @param restRate Supplier that generates resting rate.
     * @param restTime Suppier that generates resting time.
     */
    private Server(ServerType serverType, int identifier, boolean isAvailable, int waitingCustomer, 
            double nextAvailableTime, int qmax, Supplier<Double> servingTime, double prest, 
            Supplier<Double> restRate, Supplier<Double> restTime) {
        this.serverType = serverType;
        waitingCustomer = waitingCustomer < 0 ? 0 : waitingCustomer;
        this.identifier = identifier;
        this.isAvailable = isAvailable;
        this.waitingCustomer = waitingCustomer;
        this.nextAvailableTime = nextAvailableTime;
        this.qmax = qmax;
        this.servingTime = servingTime;
        this.prest = prest;
        this.restRate = restRate;
        this.restTime = restTime;
    }

    /**
     * Overloaded constructor for use in by shop.
     * @param serverType Type of server.
     * @param identifier Server ID.
     * @param qmax The maximum length of the queue.
     * @param pset Probability of rest.
     * @param randomGenerator To generate ServingTime, RestTime, RestRate
     */
    Server(ServerType serverType, int identifier, int qmax, double pset, 
            RandomGenerator randomGenerator) {
        this.serverType = serverType;
        this.identifier = identifier;
        this.isAvailable = true;
        this.waitingCustomer = 0;
        this.nextAvailableTime = 0;
        this.servingTime = () -> randomGenerator.genServiceTime();
        this.qmax = qmax;
        if (serverType == ServerType.HUMAN) {
            this.prest = pset;
            this.restRate = () -> randomGenerator.genRandomRest();
            this.restTime = () -> randomGenerator.genRestPeriod();
        } else {
            this.prest = 0.0;
            this.restRate = () -> 0.0;
            this.restTime = () -> 0.0;
        }
    }

    /**
     * Overloaded constructor for Server.
     * @param identifier Server ID.
     * @param isAvailable Whether the server is serving anyone.
     * @param hasWaitingCustomer Whether there is someone in line
     * @param nextAvailableTime The time the server will be able to serve the next customer.
     */
    Server(int identifier, boolean isAvailable, boolean hasWaitingCustomer, 
            double nextAvailableTime) {
        this.serverType = ServerType.HUMAN;
        this.identifier = identifier;
        this.isAvailable = isAvailable;
        this.waitingCustomer = hasWaitingCustomer ?  1 : 0;
        this.nextAvailableTime = nextAvailableTime;
        this.servingTime = () -> 1.0;
        this.qmax = 1;
        this.prest = 0;
        this.restRate = () -> 0.0;
        this.restTime = () -> 0.0;
    }

    /**
     * Overloaded constructor for Server.
     * @param identifier Server ID.
     */
    Server(int identifier) {
        this.serverType = ServerType.HUMAN;
        this.identifier = identifier;
        this.isAvailable = true;
        this.waitingCustomer = 0;
        this.nextAvailableTime = 0;
        this.servingTime = () -> 1.0;
        this.qmax = 1;
        this.prest = 0;
        this.restRate = () -> 0.0;
        this.restTime = () -> 0.0;
    }

    public double getNextAvailableTime() {
        return nextAvailableTime;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public boolean checkCanQueue() {
        return waitingCustomer < qmax;
    }

    public int getQueue() {
        return waitingCustomer;
    }

    public int getIdentifier() {
        return identifier;
    }

    public ServerType getServerType() {
        return serverType;
    }

    /**
     * Updates the length of the queue. Used mainly by self-checkout.
     * @param n Length of queue.
     * @return A server.
     */
    public Server updateQueue(int n) {
        return new Server(serverType, identifier, isAvailable, n, 
                nextAvailableTime, qmax, servingTime, prest, 
                restRate, restTime);
    }

    /**
     * Get the random generated restRate and check if probability of it resting
     * is more that it. 
     * @return whether the server rest or not
     */
    public boolean checkRest() {
        return prest > restRate.get();
    }

    /**
     * Returns a new server with time updated with restRate.
     * @return A server
     */
    public Server getRest() {
        double time = nextAvailableTime + restTime.get();
        return new Server(serverType, identifier, isAvailable, waitingCustomer, 
                time, qmax, servingTime, prest, restRate, restTime);
    }

    /**
     * Returns a new Server if there is no one else in the queue and that it has 
     * finished serving the customer it is serving.
     * @return A server.
     */
    public Server doneUpdate(double time) {
        if (waitingCustomer == 0 && !isAvailable) {
            return new Server(serverType, this.identifier, true, 0, time, qmax, 
                    servingTime, prest, restRate, restTime);
        } else {
            return this;
        }
    }

    public Server update() {
        if (waitingCustomer > 0) {
            return new Server(serverType, this.identifier, false, waitingCustomer - 1, 
                    this.nextAvailableTime + servingTime.get(), qmax, servingTime, prest, 
                            restRate, restTime);
        } else if (!this.isAvailable) {
            return new Server(serverType, this.identifier, true, 0, 
                    this.nextAvailableTime + servingTime.get(), 
                    qmax, servingTime, prest, restRate, restTime);
        } else {
            return this;
        }
    }

    /**
     * Return a new updated server where the queue moves forward by 1.
     * @return A server
     */
    public Server update(double time) {
        if (this.isAvailable) {
            return new Server(serverType, this.identifier, false, 0, time + servingTime.get(), 
                    qmax, servingTime, prest, restRate, restTime);
        } else if (checkCanQueue()) {
            return new Server(serverType, this.identifier, false, waitingCustomer + 1, 
                    this.nextAvailableTime, qmax, servingTime, prest, restRate, restTime);
        } else {
            return this;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o instanceof Server) {
            Server o2 = (Server) o;
            return o2.identifier == this.identifier;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        if (this.isAvailable) {
            return this.identifier + " is available";
        } else if (waitingCustomer > 0) {
            return this.identifier + " is busy; waiting customer to be served at " + 
                    String.format("%.3f", this.nextAvailableTime);
        } else {
            return this.identifier + " is busy; available at " +
                    String.format("%.3f", this.nextAvailableTime);
        }
    }
}
