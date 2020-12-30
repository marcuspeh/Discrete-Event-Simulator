package cs2030.simulator;

/** 
 * Encapsulates event for server REST status. The RestEvent class support the following methods:
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

class RestEvent extends Event {
    /**
     * Constructor for Rest event.
     * <br> 
     * Function passed into {@link Event} class to use in {@link Event#execute} is also done here. 
     * Update the server and return a pair of updated server and new server event.
     * @param customer Customer for the event.
     * @param time Time of the event.
     * @param serverNumber Server number of server serving the customer.
     */
    RestEvent(Customer customer, double time, int serverNumber) {
        super(customer, time, serverNumber, Status.REST, ServerType.HUMAN,
            x -> {
                Server server = x.find(y -> y.getIdentifier() == serverNumber).get();
                server = server.doneUpdate(time);
                x = x.replace(server);
                return Pair.of(x, new RestEvent(customer, time, serverNumber));
            });
    }
    
    @Override
    public String toString() {
        return "";
    }
}
