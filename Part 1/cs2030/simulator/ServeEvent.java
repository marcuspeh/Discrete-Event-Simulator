package cs2030.simulator;

import java.util.List;  

/** 
 * Encapsulates event for SERVE status. The ServeEvent class support the following methods:
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

class ServeEvent extends Event {
    /**
     * Constructor for SERVE event.
     * @param customer Customer for event
     * @param serverList Servers available to the event
     * @param time Time of the event
     * @param serverIndex Index of the server serving the customer based on serverList
     */
    ServeEvent(Customer customer, List<Server> serverList, double time, int serverIndex) {
        super(customer, serverList, time, serverIndex, Status.SERVE);
    }

    /**
     * Proceed from SERVE to next event LEAVE. Updates the server that is currently 
     * serving the customer. Will add it to queue if it is not added ie previous event 
     * is ARRIVE. If it is already added, will update the queue.
     * 
     * @return next event
     */
    public Event execute() {
        Server server = getServerList().get(getServerIndex());
        if (server.checkAvailable()) {
            server = server.update(getTime());
        } else {
            server = server.update();
        }
        getServerList().set(getServerIndex(), server);
        return new DoneEvent(getCustomer(), getServerList(), server.getNextAvailableTime(), 
                getServerIndex());
    }

    @Override
    public String toString() {
        return super.toString() + " served by " + 
                getServerList().get(getServerIndex()).getIdentifier();
    }
}
