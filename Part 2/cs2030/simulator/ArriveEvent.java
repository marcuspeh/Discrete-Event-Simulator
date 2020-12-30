package cs2030.simulator;

import java.util.Optional;

/** 
 * Encapsulates event for ARRIVE status. The ArriveEvent class support the following methods:
 * <ol> 
 * <li>
 * Execute method to proceed to the next status and return a Pair of next event and new shop 
 * with updated server. See {@link Event} for more details regarding sequence.
 * </li>
 * </ol>
 *
 * @author Marcus Peh
 * @version 2.0
 * @since 2020-11-11
 */

class ArriveEvent extends Event {
    /**
     * Constructor for ARRIVE event. 
     * <br>
     * Function passed into {@link Event} class to use in {@link Event#execute} is also done here. 
     * Proceed from ARRIVE to the next status. Iterates through the serverList to check if
     * there is any server available to serve the customer immediately. If there isn't, 
     * it will loop through again to find servers with no queue. If none is found after 
     * this two iteration, the customer will LEAVE.Returns Next event after ARRIVE. Either 
     * WaitEvent, ServedEvent, LeaveEvent.
     * 
     * @param customer Customer for the event.
     */
    ArriveEvent(Customer customer) {
        super(customer, customer.getArrivalTime(), -1, Status.ARRIVE, null,
            x -> {
                Optional<Server> serve = x.find(y -> y.isAvailable() &&
                        y.getNextAvailableTime() <= customer.getArrivalTime());
                //check can serve
                if (serve.isPresent()) {
                    return Pair.of(x, new ServeEvent(customer, customer.getArrivalTime(),
                            serve.get().getIdentifier(), serve.get().getServerType()));
                } else {
                    Optional<Server> queue;
                    if (customer.getCustomerType() == CustomerType.NORMAL) {
                        //check can queue
                        queue = x.find(y -> y.checkCanQueue());
                    } else {
                        queue = x.findBest(y -> y.checkCanQueue());
                    }
                    if (queue.isPresent()) {
                        return Pair.of(x, new WaitEvent(customer, customer.getArrivalTime(),
                                queue.get().getIdentifier(), queue.get().getServerType()));
                    } else {
                        //leave
                        return Pair.of(x, new LeaveEvent(customer, customer.getArrivalTime()));
                    }
                }
            }
        );
    }
    
    @Override
    public String toString() {
        return super.toString() + " arrives";
    }
}
