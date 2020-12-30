package cs2030.simulator;

/** 
 * Encapsulates event for DONE status. The DoneEvent class support the following methods:
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

class DoneEvent extends Event {
    /**
     * Constructor for DONE event.
     * <br>
     * Function passed into {@link Event} class to use in {@link Event#execute} is also 
     * done here. When a server finishes serving a customer, there is a probability Pr 
     * that the server takes a rest for a random amount of time Tr. To decide if the 
     * server should rest, a random number uniformly drawn from [0, 1] is generated 
     * using the RandomGenerator method genRandomRest() saved lazily in server. If the 
     * value returned is less than Pr, the server rests and a REST event is returned. 
     * Otherwise, the server does not rest but DONE event will be returned.
     * 
     * @param customer Customer for the event.
     * @param time Time of the event.
     * @param serverNumber Server number of server serving the customer.
     * @param serverType Type of server (self checkout or human).
     */
    DoneEvent(Customer customer, double time, int serverNumber, ServerType serverType) {
        super(customer, time, serverNumber, Status.DONE, serverType,
            x -> {
                Server server = x.find(y -> y.getIdentifier() == serverNumber).get();
                if (server.checkRest()) {
                    server = server.getRest();
                    x = x.replace(server);
                    return Pair.of(x, 
                            new RestEvent(customer, server.getNextAvailableTime(), serverNumber));
                } else {
                    server = server.doneUpdate(time);
                    x = x.replace(server);
                    return Pair.of(x, new DoneEvent(customer, time, serverNumber, serverType));
                }
            }
        );
    }

    @Override
    public String toString() {
        return super.toString() + " done serving by " +
                (humanServing() ? "server " : "self-check ") + getServerNumber();
    }
}
