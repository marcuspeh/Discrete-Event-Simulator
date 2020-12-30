package cs2030.simulator;
  
import java.util.Comparator;

/**
 * TimeComparator to sort the events in ascending order based on time, and followed by 
 * weight of its Status. The TimeComparator class support the following methods:
 * <ol> 
 * <li>
 * Compare method to compare events.
 * </li>
 * </ol>
 *
 * @author Marcus Peh
 * @version 2.0
 * @since 2020-11-11
 */

class TimeComparator implements Comparator<Event> {
    /**
     * Sorts the event according to its time in ascending order. In case of a tie breaker, 
     * it will be based on its Status weight. Status weight in ascending order
     * <blockquote> 
     * DONE / LEAVE, SERVE, WAIT, ARRIVE
     * </blockquote>
     * 
     * @param e1 first event to compare
     * @param e2 second event to compare
     */
    @Override
    public int compare(Event e1, Event e2) {
        if (e1.getTime() < e2.getTime()) {
            return -1;
        } else if (e1.getTime() > e2.getTime()) {
            return 1;
        } else {
            if (e1.getStatus().getWeight() - e2.getStatus().getWeight() != 0) {
                return e1.getStatus().getWeight() - e2.getStatus().getWeight();
            } else {
                return e1.getCustomer().getCustomerId() - e2.getCustomer().getCustomerId();
            }
        }
    }
}
