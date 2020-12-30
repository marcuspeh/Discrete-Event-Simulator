package cs2030.simulator;

/** 
 * Encapsulates event for WAIT status. The WaitEvent class support the following methods:
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

class WaitEvent extends Event {
    /**
     * Constructor for WAIT event.
     * <br>
     * Function passed into {@link Event} class to use in {@link Event#execute} is also done here. 
     * Proceed from WAIT to SERVE. Updates the server that is currently serving the customer.
     * @param customer Customer for the event
     * @param time Time of the event
     * @param serverNumber Server number of server serving the customer.
     * @param serverType Type of server (self checkout or human).
     */
    WaitEvent(Customer customer, double time, int serverNumber, ServerType serverType) {
        super(customer, time, serverNumber, Status.WAIT, serverType,
            x -> {
                Server server = x.find(y -> y.getIdentifier() == serverNumber).get();
                server = server.update(time);
                x = x.replace(server);
                return Pair.of(x, new ServeEvent(customer, server.getNextAvailableTime(), 
                            serverNumber, serverType));
            }
        );
    }

    @Override
    public String toString() {
        return super.toString() + " waits to be served by " +
            (humanServing() ? "server " : "self-check ") + getServerNumber();
    }
}
