package cs2030.simulator;

/** 
 * Encapsulates event for LEAVE status. The LeaveEvent class support the following methods:
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

class LeaveEvent extends Event {
    /**
     * Constructor for LEAVE event.
     * Function passed into {@link Event} class to use in {@link Event#execute} is also done here. 
     * The function simply returned a new LeaveEvent.
     * @param customer Customer for the event.
     * @param time Time of the event.
     */
    LeaveEvent(Customer customer, double time) {
        super(customer, time, -1, Status.LEAVE, null,
            x -> Pair.of(x, new LeaveEvent(customer, time))
        );
    }

    @Override
    public String toString() {
        return super.toString() + " leaves";
    }
}
