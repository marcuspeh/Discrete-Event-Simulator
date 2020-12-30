package cs2030.simulator;

import java.util.List;

/** 
 * Encapsulates event for WAIT status. The WaitEvent class support the following methods:
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

class WaitEvent extends Event {
    /**
     * Constructor for WAIT event.
     * @param customer Customer for event
     * @param serverList Serves available for the event
     * @param time Time of the event
     * @param serverIndex Index of the server serving the customer based on serverList
     */
    WaitEvent(Customer customer, List<Server> serverList, double time, int serverIndex) {
        super(customer, serverList, time, serverIndex, Status.WAIT);
    }

    /**
     * Proceed from WAIT to SERVE. Updates the server that is currently serving the customer.
     * @return ServeEvent
     */
    public Event execute() {
        Server server = getServerList().get(getServerIndex());
        server = server.update(getTime());
        getServerList().set(getServerIndex(), server);
        return new ServeEvent(getCustomer(), getServerList(), server.getNextAvailableTime(), 
                getServerIndex());
    }
    
    @Override
    public String toString() {
        return super.toString() + " waits to be served by " + 
                getServerList().get(getServerIndex()).getIdentifier();
    }
}
