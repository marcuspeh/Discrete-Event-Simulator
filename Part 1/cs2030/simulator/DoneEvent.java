package cs2030.simulator;

import java.util.List;  

/** 
 * Encapsulates event for DONE status. The DoneEvent class support the following methods:
 * <ol> 
 * <li>
 * Execute method to proceed to the next status and update the server list. See {@link event} 
 * for more details regarding sequence.
 * </li>
 * </ol>
 *
 * @author Marcus Peh
 * @version 1.0
 * @since 2020-09-22
 */

class DoneEvent extends Event {
    /**
     * Constructor for DONE event.
     * @param customer Customer for the event
     * @param serverList Servers available for the event
     * @param time Time of the event
     * @param serverIndex Index of the server serving the customer based on serverList
     */
    DoneEvent(Customer customer, List<Server> serverList, double time, int serverIndex) {
        super(customer, serverList, time, serverIndex, Status.DONE);
    }

    /**
     * Updates the queue such that this customer left the queue.
     * @return itself
     */
    public Event execute() {
        Server server = getServerList().get(getServerIndex());
        server = server.doneUpdate();
        getServerList().set(getServerIndex(), server);
        return this;
    }

    @Override
    public String toString() {
        return super.toString() + " done serving by " + 
                getServerList().get(getServerIndex()).getIdentifier();
    }
}
