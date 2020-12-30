package cs2030.simulator;

import java.util.List;

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
 * The Event class support the following methods:
 * <ol> 
 * <li>
 * Abstract execute method which is implemented in its child class. 
 * </li>
 * </ol>
 * @author MarcusPeh
 * @version 1.0
 * @since 2020-09-22
 */

abstract class Event {
    /** Stores the customer this event belong to. */
    private final Customer customer;
    /** Stores all the servers available. */
    private final List<Server> serverList;
    /** Stores the time of this event. */
    private final double time;
    /** 
     * Store the index of the server serving the customer in this event. 
     * Index is based on serverList. 
     */
    private final int serverIndex;
    /** Store the status of the customer(ARRIVE, WAIT, SERVE, DONE, LEAVE). */
    private final Status status;

    /**
     * Constructor for abstract class Event to be inherited by its child.
     * @param customer Customer for the event
     * @param serverList Servers for the event
     * @param time Time of the event
     * @param serverIndex Index of the server serving the customer based on serverList
     * @param status Status of the event
     */
    Event(Customer customer, List<Server> serverList, double time, 
            int serverIndex, Status status) {
        this.customer = customer;
        this.serverList = serverList;
        this.time = time;
        this.serverIndex = serverIndex;
        this.status = status;
    }
        

    public Customer getCustomer() {
        return this.customer;
    }

    public List<Server> getServerList() {
        return this.serverList;
    }

    public double getTime() {
        return this.time;
    }

    public int getServerIndex() {
        return this.serverIndex;
    }

    public Status getStatus() {
        return this.status;
    }

    /**
     * Refer to the respective event({@link ArriveEvent}, {@link WaitEvent}, 
     * {@link ServeEvent}, {@link DoneEvent}, {@link LeaveEvent}).
     * @return next event
     */
    public abstract Event execute();

    @Override
    public String toString() {
        return String.format("%.3f", this.time) + " " + this.customer.getCustomerId();
    }
}
