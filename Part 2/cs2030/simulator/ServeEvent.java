package cs2030.simulator;

/** 
 * Encapsulates event for SERVE status. The ServerEvent class support the following methods:
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

class ServeEvent extends Event {
    /**
     * Constructor for SERVE event.
     * <br>
     * Function passed into {@link Event} class to use in {@link Event#execute} is also done here. 
     * Proceed from SERVE to next event LEAVE. Updates the server that is currently 
     * serving the customer. Will add it to queue if it is not added ie previous event 
     * is ARRIVE. If it is already added, will update the queue.
     * @param customer Customer for the event.
     * @param time Time of the event.
     * @param serverNumber Server number of server serving the customer.
     * @param serverType Type of server (self checkout or human).
     */
    ServeEvent(Customer customer, double time, int serverNumber, ServerType serverType) {
        super(customer, time, serverNumber, Status.SERVE, serverType,
            x -> {
                Server server = x.find(y -> y.getIdentifier() == serverNumber).get();
                if (time >= server.getNextAvailableTime()) {
                    if (server.isAvailable()) {
                        server = server.update(time);
                    } else { 
                        server = server.update();
                    }
                    x = x.replace(server);
                    return Pair.of(x, new DoneEvent(customer, server.getNextAvailableTime(), 
                            serverNumber, serverType));
                } else {
                    return Pair.of(null, new ServeEvent(customer, server.getNextAvailableTime(), 
                            serverNumber, serverType));
                }
            }
        );
    }

    @Override
    public String toString() {
        return super.toString() + " served by " + (humanServing() ? "server " : "self-check ") +
                getServerNumber();
    }
}
