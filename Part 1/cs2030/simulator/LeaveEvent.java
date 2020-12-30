package cs2030.simulator;

import java.util.List;  

/** 
 * Encapsulates event for LEAVE status.  The LeaveEvent class support the following methods:
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

class LeaveEvent extends Event {
    /**
     * Constructor for LEAVE event.
     * @param customer Customer for event
     * @param serverList Servers available for the event
     * @param time Time of the event
     */
    LeaveEvent(Customer customer, List<Server> serverList, double time) {
        super(customer, serverList, time, -1, Status.LEAVE);
    }

    /**
     * Since there is no next event after LEAVE, it will return itself.
     * @return itself
     */
    public Event execute() {
        return this;
    }

    @Override
    public String toString() {
        return super.toString() + " leaves";
    }
}
