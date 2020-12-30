package cs2030.simulator;

import java.util.List;

/** 
 * Encapsulates event for ARRIVE status. The ArriveEvent class support the following methods:
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

class ArriveEvent extends Event {
    /**
     * Constructor for ARRIVE event.
     * @param customer Customer for the event
     * @param serverList Servers available for the event
     */
    ArriveEvent(Customer customer, List<Server> serverList) {
        super(customer, serverList, customer.getArrivalTime(), -1, Status.ARRIVE);
    }

    /**
     * Proceed from ARRIVE to the next status. Iterates through the serverList to check if
     * there is any server available to serve the customer immediately. If there isn't, it will
     * loop through again to find servers with no queue. If none is found after this two iteration,
     * the customer will LEAVE.
     * 
     * @return Next event after ARRIVE. Either WaitEvent, ServedEvent, LeaveEvent.
     */
    public Event execute() {       
        //check if can serve
        for (int i = 0; i < getServerList().size(); i++) {
            if (getServerList().get(i).checkAvailable()) {
                return new ServeEvent(
                        getCustomer(), 
                        getServerList(), 
                        getTime(), 
                        i);
            }
        }

        //check if can queue
        for (int i = 0; i < getServerList().size(); i++) {
            if (getServerList().get(i).checkCanQueue()) {
                return new WaitEvent(getCustomer(), getServerList(), getTime(), i);
            }
        }

        return new LeaveEvent(getCustomer(), getServerList(), getTime());
        

    }
    
    @Override
    public String toString() {
        return super.toString() + " arrives";
    }
}
