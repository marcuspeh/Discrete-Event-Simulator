package cs2030.simulator;

import java.util.function.Function;

/** 
 * Abstract class for event. Cannot be instantiated.
 * Sequence of event:
 * <blockquote> 
 * ARRIVE - SERVE - DONE
 * </blockquote>
 * <blockquote>
 * ARRIVE - WAIT - SERVE - DONE
 * </blockquote>
 * <blockquote>
 * ARRIVE - LEAVE  
 * </blockquote>
 * There is an additional REST event that manages the abstraction of server resting 
 * after serving the customer. <br>
 * The Event class support the following methods:
 * <ol> 
 * <li>
 * Final execute method that runs the function passed in by the child. 
 * </li>
 * </ol>
 * @author MarcusPeh
 * @version 1.0
 * @since 2020-09-22
 */

abstract class Event {
    /** Stores the customer this event belong to. */
    private final Customer customer;
    /** Stores the time of this event. */
    private final double time;
    /** Stores the number of the server serving the customer. */
    private final int serverNumber;
    /** Stores the status of the customer(ARRIVE, WAIT, SERVE, DONE, LEAVE, 
     *  REST(to manage the server rest state only)). 
     */
    private final Status status;
    /** Stores the type of server(HUMAN, SELFCHECKOUT) serving the customer. */
    private final ServerType serverType;
    /** Stores the function passed in by child class for use in execute. */
    private final Function<Shop, Pair<Shop, Event>> func;

    /**
     * Constructor for abstract class Event to be inherited by its child.
     * @param customer Customer for the event.
     * @param time Time of the event.
     * @param serverNumber Server number of server serving the customer.
     * @param status Status of the event.
     * @param serverType Type of server (self checkout or human).
     * @param func Function to be ran in {@link execute} method 
     */
    Event(Customer customer, double time, int serverNumber, 
            Status status, ServerType serverType, Function<Shop, Pair<Shop, Event>> func) {
        this.customer = customer;
        this.time = time;
        this.serverNumber = serverNumber;
        this.status = status;
        this.serverType = serverType;
        this.func = func;
    }
        

    public Customer getCustomer() {
        return this.customer;
    }

    public double getTime() {
        return this.time;
    }

    public int getServerNumber() {
        return this.serverNumber;
    }

    public Status getStatus() {
        return this.status;
    }

    public ServerType getServerType() {
        return this.serverType;
    }

    public boolean humanServing() {
        return serverType == ServerType.HUMAN;
    }

    /**
     * Declared final to avoid overriding by child.
     * Will run the function passed in by the child and return the next event state.
     * @param shop Shop containing all the servers
     * @return Pair object with next event and updated shop.
     */
    final Pair<Shop, Event> execute(Shop shop) { // declared final to avoid overriding
        return this.func.apply(shop); // func is the Function property
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.time) + " " + this.customer.getCustomerId()
                + (customer.getCustomerType() == CustomerType.GREEDY ? "(greedy)" : "");
    }
}
